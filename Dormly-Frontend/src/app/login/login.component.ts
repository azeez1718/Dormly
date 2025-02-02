import { CommonModule } from '@angular/common';
import { Component, NgModule, OnInit } from '@angular/core';
import { HomeComponent } from "../user-home/home.component";
import {MatInputModule} from '@angular/material/input';
import { MatError } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormArray, ReactiveFormsModule, FormBuilder, FormGroup, Validator, Validators } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth/auth.service';
import { RouterLink } from '@angular/router';
import { LoginRequest } from '../models/LoginRequest';
import { AuthResponse } from '../models/AuthResponse';
import { Router } from '@angular/router';
import { TokenService } from '../auth/token/token.service';
import { MatIcon } from '@angular/material/icon';


@Component({
  selector: 'app-login',
  imports: [ 
          CommonModule,  
          MatInputModule,
          MatButtonModule,
          ReactiveFormsModule,
          FormsModule,
          MatError,
          RouterLink,
          MatIcon
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {


  userForm!: FormGroup
  formSubmitted: boolean = false
  loginRequest : LoginRequest = {
    'email' : '',
    'password': ''
  }



  /**
   * The parameters for the form controls are default value ->synchronous data -> asynchronous data
   *  
   */
  constructor(private formBuilder : FormBuilder, private auth:AuthService, private route:Router,
    private tokenservice:TokenService){}
  ngOnInit(): void {
   this.formSetup()
  }


  formSetup():void{
    this.userForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email, Validators.pattern(/@qmul\.ac\.uk$/)]],
      password: ['', [Validators.maxLength(15), Validators.required]]
    })
  }

  login():void{
    /**
     * when we login, we can send the payload to the backend to save user info
     * for now as we know the button is only displayed when the form is valid(all fields are entered)
     * we can return the values of the form in the console
     */
    this.formSubmitted = true
  

    if(this.userForm.valid){
      /**
       * if all validations are correct including pattern and required 
       * we can then call the service class method and emit the data from the observable
       * console.log the token just for demonstration purposes to see if we are returning the correct thing
       */

      this.loginRequest = this.userForm.value
      this.auth.login(this.loginRequest).subscribe({
        next:(response: AuthResponse)=>{
          /**
           * setter method to set the token in the browser of the client
           * similar to tokenservice.setToken(response)
           * the token will be stored in the browser with (key,value)
           */
          this.tokenservice.token = response.token as string
          console.log("login page-token returned", response)

          this.route.navigate(['/my-home'])
        },
        error:(error:Error)=>{
          console.log(error.message)
        }
      })
    }
    
    

  }

 
}
