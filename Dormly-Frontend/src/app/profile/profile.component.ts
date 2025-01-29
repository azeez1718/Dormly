import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../service/profile/profile.service';
import { Profile } from '../models/Profile';
import { catchError, of } from 'rxjs';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-profile',
  imports: [CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit{
  //abstracts away from typescripts strict property initialization
  userProfile:Array<Profile> = []
  hasError:boolean = false
  hasloaded:boolean = false


  constructor(private profileService:ProfileService){}
  
  /**
   * The component gets rendered when a user clicks on view profile button in the dormly-home
   * as soon as the component constructor gets initialized
   * we run the ngoninit to immediatley fetch the user associated with the profile
   */
  ngOnInit(): void {
    this.fetchProfile()
    //the returning json includes the image and the user info
   
  }

  fetchProfile(){
    this.profileService.fetchUserProfile()
    .pipe(
      catchError((error)=>{
        console.log(error.message)
        this.hasError = true;
        //return an empty profile object if any errors occur
        //this empty object replaces the value in the datastream if there is any error
        return of({} as Profile)
    })
    )
    .subscribe(data=>{
      this.userProfile.push(data)
      this.hasloaded = true
      
      //as soon as this 
    });


      }
    
  }





