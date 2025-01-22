import { CommonModule } from '@angular/common';
import { Component, NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HomeComponent } from "../home/home.component";
import {MatInputModule} from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-login',
  imports: [FormsModule, 
          CommonModule, 
          HomeComponent, 
          MatInputModule,
          MatButtonModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {



  onSubmit(){
    console.log("submitted")

  }

}
