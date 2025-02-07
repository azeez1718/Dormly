import { Component, OnInit } from '@angular/core';
import { ListingService } from '../service/listing/listing.service';
import { ListingstateService } from '../shared/listingstate.service';
import { listingConfirmation } from '../models/listingCard';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-listing-confirmation',
  imports: [CommonModule],
  templateUrl: './listing-confirmation.component.html',
  styleUrl: './listing-confirmation.component.css'
})
export class ListingConfirmationComponent implements OnInit{

  confirmation!:listingConfirmation | null
  isCreated:boolean = false

  constructor(private listingStateService:ListingstateService){}
  

  //retrieve the response of the users listing upon creation, this way we can automatically render a listing confirmed page
  //the behaviour subject was converted into an observable so that observers could only subscribe and not alter the state with next()
  ngOnInit(): void {
    this.latestListing()

  }
   
   
   
    latestListing():void{
    this.listingStateService.listingConfirmation$.subscribe({
    next:(data:listingConfirmation| null)=>{
      console.log('the data we returned is', data)
      this.confirmation = data  

      if(!this.confirmation){ 
        //if falsy handle error
        throw new Error('the data returned is falsy')
      
      }
      this.isCreated = true //meaning the user successfully created their listing and we were able to return their listing confirmation
    },
    error:(error:Error)=>{
      console.log('error whilst receiving new state', error.message)
    }
   })
    }




}
