import { Component, OnInit } from '@angular/core';
import { ListingService } from '../service/listing/listing.service';
import { ActivatedRoute } from '@angular/router';
import { listingCard } from '../models/listingCard';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { FileuploadService } from '../service/fileuploads/fileupload.service';

@Component({
  selector: 'app-update-listing',
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './update-listing.component.html',
  styleUrl: './update-listing.component.css'
})
export class UpdateListingComponent implements OnInit{



  listing!:listingCard
  hasLoaded:boolean = false
  editForm!:FormGroup
  multipartForm!:FormData
  hasUploadedNewPhoto:boolean = false
  uploadedPhoto!:string | undefined


  


   
  constructor(private listingService:ListingService,
     private route:ActivatedRoute, private formBuilder:FormBuilder, private fileService:FileuploadService){}

 

  


  ngOnInit(): void {
    this.UpdateForm()
    this.updateListing()
  
  
    
  }

  private UpdateForm():void{
    //prepopulate the form to show the user listing information
    this.editForm = this.formBuilder.group({
      'title' : ['' ,[Validators.required,  Validators.maxLength(20)]],
      'price' : ['', [Validators.required, Validators.min(5.01)]],
      'description': ['', [Validators.required, Validators.maxLength(100)]],
      'brand': ['', [Validators.required, Validators.maxLength(100)]],
      'condition': ['', [Validators.required, Validators.maxLength(100)]],
      'location': ['', [Validators.required, Validators.maxLength(100)]],
      'category': ['', [Validators.required,]],
      'availability': ['', [Validators.required, Validators.maxLength(100)]]

      

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
        this.editForm.controls['title'].setValue(this.listing.title)
        this.editForm.controls['price'].setValue(this.listing.price)
        this.editForm.controls['description'].setValue(this.listing.description)
        this.editForm.controls['brand'].setValue(this.listing.brand)
        this.editForm.controls['condition'].setValue(this.listing.condition)
        this.editForm.controls['category'].setValue(this.listing.category)
        this.editForm.controls['location'].setValue(this.listing.location)
        this.editForm.controls['availability'].setValue(this.listing.availability)
        this.hasLoaded = true
      


      },
      error:(error:Error)=>{
        console.log("error with retrieving editing information", error.message)
      }
    })
  }

}


onFileSelected($event:Event){
  this.multipartForm = this.fileService.uploadFile($event);
  // we want to display the image on the left side of the screen, so the user can see his new listing
  //if the new listing uploaded photo doesnt equal to null and is valid, then show the listing photo on the left
    //otherwise if nothing changes meaning 
  if(!this.multipartForm.get('file') === null){
    //only when this is true we can append the json and the file together
    this.hasUploadedNewPhoto=true
  }
  else{
    throw Error("please select a valid image")
  }
  
   

}

onSubmit(){

  

}

}
