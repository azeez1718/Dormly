import { Component, OnInit } from '@angular/core';
import { MatToolbar } from '@angular/material/toolbar';
import { RouterLink } from '@angular/router';
import { MatButton } from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import { ProfileService } from '../service/profile/profile.service';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-dashboard-navbar',
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard-navbar.component.html',
  styleUrl: './dashboard-navbar.component.css'
})
export class DashboardComponent implements OnInit {


  hasLoaded:boolean = false
  image!:URL


  constructor(private profileService:ProfileService){}


  ngOnInit(): void {
    this.getProfilePicture()
  }


  getProfilePicture():void{
    this.profileService.getProfilePicture().subscribe({
      next:(data:URL)=>{
        this.hasLoaded = true
        console.log('we retrieved the url for the image')
        this.image = data

      },

      error:(error:Error)=>{
        console.log(error.message)
      }
    })

  }


}
