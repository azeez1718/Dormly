import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './user-home/home.component';
import { combineLatest } from 'rxjs';
import { SignupComponent } from './signup/signup.component';
;

export const routes: Routes = [

    {
        path : "my-home",
        component : HomeComponent

    },

    {
        path : "login",
        component : LoginComponent
    },
    {
        path: "sign-up",
        component : SignupComponent
    }
];
