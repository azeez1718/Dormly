import { Component, OnInit } from '@angular/core';
import { ListingService } from '../service/listing/listing.service';
import { ListingstateService } from '../shared/listingstate.service';
import { listingConfirmation } from '../models/listingCard';

@Component({
  selector: 'app-listing-confirmation',
  imports: [],
  templateUrl: './listing-confirmation.component.html',
  styleUrl: './listing-confirmation.component.css'
})
export class ListingConfirmationComponent implements OnInit{


  constructor(private listingStateService:ListingstateService){}
  

  //retrieve the response of the users listing upon creation, this way we can automatically render a listing confirmed page
  //the behaviour subject was converted into an observable so that observers could only subscribe and not alter the state with next()
  ngOnInit(): void {
    this.latestListing()

  }
   
   
   
    latestListing():void{
    this.listingStateService.listingConfirmation$.subscribe({
    next:(data:listingConfirmation| null)=>{
      console.log(data)

    },
    error:(error:Error)=>{
      console.log('error whilst receiving new state', error.message)
    }
   })
    }




}
