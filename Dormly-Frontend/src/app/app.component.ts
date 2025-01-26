import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { HomeComponent } from "./user-home/home.component";
import { NavbarComponent } from './navbar/navbar.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavbarComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  displayNavbar:Boolean = false


  constructor(private router:Router){}


  /**
   * ngoninit gets ran when the main component gets instantiated
   * it remains active due to the subscribable listening to events being emitted
   * the events function returns an observable of an event type whenever a route change occurs
   * if the event is of type navigation end this means the new route has been activated
   * we can then check for every new route we go to, we run the showNavbar to see if its the '' route
   */
  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.showNavbar();
      }
    });


}

showNavbar(){
  // TODO bug remains in here
  //the router.url only shows the path after the port number, like '/dashboard' or /home
  console.log("we need to remove navbar")
  if(this.router.url=="/"){
    this.displayNavbar = true
  }
}

}
