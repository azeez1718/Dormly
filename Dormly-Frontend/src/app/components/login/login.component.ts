import { CommonModule } from '@angular/common';
import { Component, NgModule } from '@angular/core';
import { HomeComponent } from "../home/home.component";
import {MatInputModule} from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormArray, ReactiveFormsModule, FormBuilder, FormGroup, Validator, Validators } from '@angular/forms';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [ 
          CommonModule, 
          HomeComponent, 
          MatInputModule,
          MatButtonModule,
          ReactiveFormsModule,
          FormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  userForm : FormGroup


  constructor(private formBuilder : FormBuilder){
    this.userForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      passowrd: ['', Validators.maxLength(15), Validators.required]
    })
  }


  onSubmit(){
    console.log("submitted")

  }

}
