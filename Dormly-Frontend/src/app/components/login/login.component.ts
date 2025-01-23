import { CommonModule } from '@angular/common';
import { Component, NgModule } from '@angular/core';
import { HomeComponent } from "../home/home.component";
import {MatInputModule} from '@angular/material/input';
import { MatError } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormArray, ReactiveFormsModule, FormBuilder, FormGroup, Validator, Validators } from '@angular/forms';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [ 
          CommonModule,  
          MatInputModule,
          MatButtonModule,
          ReactiveFormsModule,
          FormsModule,
          MatError
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  userForm : FormGroup


  constructor(private formBuilder : FormBuilder){
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
    console.log(this.userForm.value, "submitted")

  }

}
