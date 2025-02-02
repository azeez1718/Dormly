import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { HomeComponent } from "./user-home/home.component";
import { NavbarComponent } from './navbar/navbar.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  displayNavbar:Boolean = false
 


  constructor(private router:Router){}


  
  ngOnInit() {
    //this.showNavbar();
     


}

// showNavbar():void{
//   // TODO bug remains in here
//   //the router.url only shows the path after the port number, like '/dashboard' or /home
//   console.log(this.router.url)
//   if (this.router.url === '/login' || this.router.url==='/sign-up' || this.router.url === '/'){
//     this.displayNavbar = true
//   }else{
//     this.displayNavbar=false
//   }

  //}
}


