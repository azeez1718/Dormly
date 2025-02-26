import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './user-home/home.component';
import { combineLatest } from 'rxjs';
import { SignupComponent } from './signup/signup.component';
import { computed } from '@angular/core';
import { ProfileComponent } from './profile/profile.component';
import { MainComponent } from './main/main.component';
import { ListingComponent } from './listing/listing.component';
import { ListingConfirmationComponent } from './listing-confirmation/listing-confirmation.component';
import { ProductCardComponent } from './product-card/product-card.component';
import { UpdateListingComponent } from './update-listing/update-listing.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { MessagesComponent } from './message/chat/messages.component';
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
    },
    {
        path : 'listing-confirmation',
        component:ListingConfirmationComponent
    },

    {
        path: 'product/:id',
        component:ProductCardComponent
    },
    {
        path : "update/listing/:id",
        component: UpdateListingComponent
    },
    {
        path : "sidebar",
        component: SidebarComponent
    },

    {
        path : "messages",
        component: MessagesComponent
    }
];
