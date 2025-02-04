import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './user-home/home.component';
import { combineLatest } from 'rxjs';
import { SignupComponent } from './signup/signup.component';
import { computed } from '@angular/core';
import { ProfileComponent } from './profile/profile.component';
import { MainComponent } from './main/main.component';
import { ListingComponent } from './listing/listing.component';
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
    },
    {
    path:"profile",
    component:ProfileComponent
    },

    {
        path: '',  // Root route
        component: MainComponent

    },
    {
        path : 'listing',
        component: ListingComponent
    }
];
