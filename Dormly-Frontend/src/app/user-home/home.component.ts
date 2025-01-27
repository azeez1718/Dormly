import { Component, OnInit } from '@angular/core';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { AuthService } from '../service/auth/auth.service';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-home',
  imports: [DashboardComponent, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  returned:Boolean = false
  message : string = ''
  time : any
  protectBoolean:boolean = false


  constructor(private authService:AuthService){}
  
  
  ngOnInit(): void {
   
  }

  /**Test function to see if token is set in the headers before api requests 
  protectedEndpoint(){
    this.authService.protectedapi().subscribe({
      next:(res : any)=>{
        console.log('the response returned is',res)
        this.returned = true
        this.message = res
        console.log("nice you sent a jwt with the request")
        
      },

      error:(error:Error)=>{
        console.log("there is no jwt sent with this request", error.message)
       
      }
      
    })
  }
  **/
 

  





}
