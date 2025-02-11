import { Component, OnInit } from '@angular/core';
import { ListingService } from '../service/listing/listing.service';
import { ActivatedRoute } from '@angular/router';
import { listingCard } from '../models/listingCard';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-update-listing',
  imports: [CommonModule],
  templateUrl: './update-listing.component.html',
  styleUrl: './update-listing.component.css'
})
export class UpdateListingComponent implements OnInit{

  listing!:listingCard
  hasLoaded:boolean = false

  
  constructor(private listingService:ListingService, private route:ActivatedRoute){}



  ngOnInit(): void {
    this.updateListing()
    
  }


  updateListing():void{
    let id = this.route.snapshot.paramMap.get('id')
    if(id){
    this.listingService.fetchListingById(id).subscribe({
      next:(res:listingCard)=>{
        this.listing = res
        console.log("edit listing", this.listing)
        this.hasLoaded = true

      },
      error:(error:Error)=>{
        console.log("error with retrieving editing information", error.message)
      }
    })
  }





}


}
