import { Component, OnInit } from '@angular/core';
import { DashboardComponent } from '../dashboard-navbar/dashboard-navbar.component';
import { AuthService } from '../auth/auth.service';
import { CommonModule } from '@angular/common';
import { ListingcardComponent } from '../listingcard/listingcard.component';
import { listingCard } from '../models/listingCard';
import { ListingService } from '../service/listing/listing.service';


@Component({
  selector: 'app-home',
  imports: [DashboardComponent, CommonModule, ListingcardComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
 cards!:Array<listingCard>
 hasLoaded:boolean = false



  constructor(private listingService:ListingService){}
  
  
  ngOnInit(): void {
    console.log("going to make call in ts class")
    this.fetchAllListings()
   
  }


  fetchAllListings():void{
  this.listingService.fetchListings().subscribe({
    next:(listingDTO:Array<listingCard>)=>{
      console.log('returned data from subscribing')
      this.cards = listingDTO
      this.hasLoaded = true
    },

    error:(error:Error)=>{
      console.log('unable to retrieve listings' , error.message)
    }
  })

  }

  
 
 

  





}
