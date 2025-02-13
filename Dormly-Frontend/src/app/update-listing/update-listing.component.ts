import { Component, OnInit } from '@angular/core';
import { ListingService } from '../service/listing/listing.service';
import { ActivatedRoute } from '@angular/router';
import { listingCard } from '../models/listingCard';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-listing',
  imports: [CommonModule],
  templateUrl: './update-listing.component.html',
  styleUrl: './update-listing.component.css'
})
export class UpdateListingComponent implements OnInit{



  listing!:listingCard
  hasLoaded:boolean = false
  editForm!:FormGroup

  


   
  constructor(private listingService:ListingService,
     private route:ActivatedRoute, private formBuilder:FormBuilder){}

 

  


  ngOnInit(): void {
    this.updateListing()
    this.UpdateForm()
    
  }

  private UpdateForm():void{
    //prepopulate the form to show the user listing information

    if(!this.hasLoaded){
      throw Error("An error occured fetching the listing information")

    }
    this.editForm = this.formBuilder.group({
      'title' : [this.listing.title, [Validators.required,  Validators.maxLength(20)]],
      'price' : [this.listing.price, [Validators.required, Validators.min(5.01)]],
      'description': [this.listing.description, [Validators.required, Validators.maxLength(100)]],
      'brand': [this.listing.brand, [Validators.required, Validators.maxLength(100)]],
      'condition': [this.listing.condition, [Validators.required, Validators.maxLength(100)]],
      'location': [this.listing.location, [Validators.required, Validators.maxLength(20)]], // we will make an API call to fetch locations
      'category': [this.listing.category, [Validators.required,]],
      'availability': [this.listing.availability, [Validators.required, Validators.maxLength(100)]]
      

    })
  }

  

  /**
   * whenever the user clicks on edit listing, we bind the id associated to that listing as a path variable
   * we make an api call to fetch that listing by id and return its information
   */
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
