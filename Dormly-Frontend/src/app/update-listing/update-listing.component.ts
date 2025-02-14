import { Component, OnInit } from '@angular/core';
import { ListingService } from '../service/listing/listing.service';
import { ActivatedRoute, Router } from '@angular/router';
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
     private route:ActivatedRoute,
      private formBuilder:FormBuilder, 
      private fileService:FileuploadService,
      private router:Router
    
    ){}

  ngOnInit(): void {
    this.UpdateForm()
    this.fetchListingToUpdate()
  
  
    
  }

  private UpdateForm():void{
    //prepopulate the form to show the user listing information
    this.editForm = this.formBuilder.group({
      'title' : ['' ,[Validators.required,  Validators.maxLength(100)]],
      'brand': ['', [Validators.required, Validators.maxLength(50)]],
      'condition': ['', [Validators.required, Validators.maxLength(10)]],
      'location': ['', [Validators.required, Validators.maxLength(100)]],
      'availability': ['', [Validators.required, Validators.maxLength(100)]],
      'category': ['', [Validators.required,]],
      'price' : ['', [Validators.required, Validators.min(5.01)]],
      'description': ['', [Validators.required, Validators.maxLength(100)]]

      

    })
  }


  /**
   * whenever the user clicks on edit listing, we bind the id associated to that listing as a path variable
   * we make an api call to fetch that listing by id and return its information
   */
  fetchListingToUpdate():void{
    let id = this.route.snapshot.paramMap.get('id')
    if(id){
    this.listingService.fetchListingById(id).subscribe({
      next:(res:listingCard)=>{
        this.listing = res
        console.log("edit listing", this.listing)
        this.editForm.controls['title'].setValue(this.listing.title)
        this.editForm.controls['brand'].setValue(this.listing.brand)
        this.editForm.controls['condition'].setValue(this.listing.condition)
        this.editForm.controls['location'].setValue(this.listing.location)
        this.editForm.controls['availability'].setValue(this.listing.availability)
        this.editForm.controls['category'].setValue(this.listing.category)
        this.editForm.controls['price'].setValue(this.listing.price)
        this.editForm.controls['description'].setValue(this.listing.description)
        
        this.hasLoaded = true
        
      


      },
      error:(error:Error)=>{
        console.log("error with retrieving editing information", error.message)
      }
    })
  }

}


onFileSelected($event:Event){
  //retrieve the form data that includes the key value pair of the 'file' and file, no error checks as this gets handled in fileService
  this.multipartForm = this.fileService.upload($event);
  
  
  
}

onSubmit(){
  Object.keys(this.editForm.value).forEach(controlName=>{
    const error = this.editForm.get("controlName")?.errors
    if(error){
      console.log(controlName, error)
    }

  })
  if(this.editForm.invalid){
    throw new Error("unable to submit form")
  }
  //if the user hasnt selected anything, multipart form wouldnt be initialized, so we reintialize it here
  if (!this.multipartForm) {
    this.multipartForm = new FormData();
  }

  //as the form data may be undefined due to no selection of file, 
  //convert the form data into json to allow it to be processed, also use blob to set explicit content type without browser inferring
  let listingJson = new Blob([JSON.stringify(this.editForm.value)],{type:'application/json'})

  this.multipartForm.append('listing', listingJson)


  //before sending we need to set a path variable identify exactly which listing the user is willing to update
  this.listingService.updateListing(this.multipartForm, this.listing.listingId.toString())
  .subscribe({
    next:()=>{
      console.log('successfully updated your listing')
      this.router.navigate(['/profile'])

    },
    error:(err:Error) =>{
    console.log('error occured updating listing', err.message)
    
    }
  })


  

}

}
