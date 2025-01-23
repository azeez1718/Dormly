import { CommonModule } from '@angular/common';
import { Component, NgModule } from '@angular/core';
import { HomeComponent } from "../home/home.component";
import {MatInputModule} from '@angular/material/input';
import { MatError } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormArray, ReactiveFormsModule, FormBuilder, FormGroup, Validator, Validators } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../service/auth.service';
import { RouterLink } from '@angular/router';
import { LoginRequest, loginRequest } from '../../models/LoginRequest';
import { AuthResponse } from '../../models/AuthResponse';

@Component({
  selector: 'app-login',
  imports: [ 
          CommonModule,  
          MatInputModule,
          MatButtonModule,
          ReactiveFormsModule,
          FormsModule,
          MatError,
          RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  userForm : FormGroup
  formSubmitted: boolean = false
  loginRequest : LoginRequest = {
    'email' : '',
    'password': ''
  }



  /**
   * The parametrs for the form controls are default value ->synchronous data -> asynchronous data
   *  
   */
  constructor(private formBuilder : FormBuilder, private auth:AuthService){
    this.userForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email, Validators.pattern(/@qmul\.ac\.uk$/)]],
      password: ['', [Validators.maxLength(15), Validators.required]]
    })
  }


  onSubmit():void{
    /**
     * when we submit the button, we can send the payload to the backend to save user info
     * for now as we know the button is only displayed when the form is valid(all fields are entered)
     * we can return the values of the form in the console
     */
    this.formSubmitted = true
    console.log(this.userForm.value)

    if(this.userForm.valid){
      /**
       * if all validations are correct including pattern and required 
       * we can then call the service class method and emit the data from the observable
       * console.log the token just for demonstration purposes to see if we are returning the correct thing
       */

      this.loginRequest = this.userForm.value
      this.auth.login(loginRequest).subscribe({
        next:(token: AuthResponse)=>{
          console.log(token)
        },
        error:(error:Error)=>{
          console.log(error.message)
        }
      })
    }
    
    

  }

}
