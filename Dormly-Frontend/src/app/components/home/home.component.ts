import { Component, OnInit } from '@angular/core';
import { DashboardComponent } from '../../shared/dashboard/dashboard.component';
import { AuthService } from '../../service/auth/auth.service';
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


  constructor(private authService:AuthService){}
  
  
  ngOnInit(): void {
    this.protectedEndpoint();
  }


  protectedEndpoint(){
    this.authService.protectedapi().subscribe({
      next:(res : any)=>{
        this.returned = true
        this.message = res
        
      },

      error:(error:Error)=>{
        console.log(error.message)
      }
      
    })
  }





}
