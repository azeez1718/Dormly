import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../service/profile/profile.service';
import { Profile } from '../models/Profile';
import { catchError, elementAt, of } from 'rxjs';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { FileuploadService } from '../service/fileuploads/fileupload.service';
import { DashboardComponent } from '../dashboard-navbar/dashboard-navbar.component';
import { profileListings } from '../models/listingCard';

@Component({
  selector: 'app-profile',
  imports: [CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit{

  //abstracts away from typescripts strict property initialization
  profile!:Profile
  hasError:boolean = false
  hasLoaded:boolean = false
  activeListings!:number 


  constructor(private profileService:ProfileService, private fileService : FileuploadService){}
  
 
  ngOnInit(): void {
    this.fetchProfile()
    //the returning json includes the image and the user info
   
  }

  uploadProfile(event: Event) {
    //the event parameter represents the  input field that triggered the event
    //we return a formdata which represents the key and value, the key binds to the backend request param
    console.log("calling the upload file function")
    const file = this.fileService.uploadFile(event)
    console.log("we returned a form data object")
    this.profileService.uploadProfilePicture(file)

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
  
      this.hasLoaded = true
      //wrap it an array as we only return a single object, and ngFor requires an iterable
      this.profile= data
      console.log(this.profile)
      //we can just know the number of listing objects returned.
      this.activeListings= this.findActiveListings(this.profile.profileListings)
      
    });

    
      }


    
      findActiveListings(listings:Array<profileListings>):number{
      /**
       * call this function to return the number of listings that have isSold to False - hence active Listings
       *this creates a new list as we use the filter function to iterate and create a new list where it pushes all not sold items
       * we can then later fetch all items that have been sold, which would be the opposite of this statement, and render them
       */
        const activeListings:Array<profileListings> = listings.filter(listing => listing.isSold === false)
        return activeListings.length
        
      }
    
  }






