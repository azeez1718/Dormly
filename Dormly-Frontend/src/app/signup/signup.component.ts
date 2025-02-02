import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../auth/auth.service';
import { SignUp } from '../models/SignUp';
import { AuthResponse } from '../models/AuthResponse';
import { Router, RouterLink } from '@angular/router';
import { TokenService } from '../auth/token/token.service';

@Component({
  selector: 'app-signup',
  imports: [FormsModule, ReactiveFormsModule, CommonModule, RouterLink],//RouterLink],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent implements OnInit{

  registerForm!:SignUp
  formgroup!:FormGroup

 

  constructor(private formBuilder:FormBuilder, private authService:AuthService, private router:Router, private tokenService:TokenService){}
  ngOnInit(): void {

    this.formgroup = this.formBuilder.group({
      'firstname' : ['',[Validators.required, Validators.maxLength(20), Validators.minLength(1)]],
      "lastname" : ['', [Validators.required, Validators.maxLength(20), Validators.minLength(1)]],
      "email": ['',[Validators.required, Validators.email, Validators.pattern(/@qmul\.ac\.uk$/)]],
      "password":['',[Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/)]],
      "confirmPassword" :['',[Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/)]]
    })
   
  }


  Register():void{
    if(this.formgroup.valid && this.formgroup.controls['confirmPassword'].value === this.formgroup.controls['password'].value){
   
      //because confirm password is used for frontend validations only, we need to extract confirm password from the formgroup before sending to backend..using destructions
    const {confirmPassword , ...form} = this.formgroup.value;
    this.registerForm = form

    this.authService.register(this.registerForm).subscribe({
      next:(response:AuthResponse)=>{
        //set the token returned to the local storage and redirect the user once signed up
        //because the token can be undefined it doesnt let you pass an undefined to a method that expects a string, so we only pass the string 
        if(response.token){
        this.tokenService.token = response.token as string
        console.log("user successfully signed up")
        //for now we'll redirect user to the homepage but later they will need to set up their profile
        this.router.navigate(["/my-home"])
        }
      },

      error:(error:Error)=>{
        console.log(error.message)
      }
    })

    }else{
      console.log('passwords do not match')
      //set a password dont match flag
    }
    

    
  }



  


}
