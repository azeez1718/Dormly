import { Component, OnInit } from '@angular/core';
import { FileuploadService } from '../service/fileuploads/fileupload.service';
import { ListingService } from '../service/listing/listing.service';
import { Listing } from '../models/listing';
import { FormsModule } from '@angular/forms';
import { listingCard, listingConfirmation } from '../models/listingCard';
import { ListingstateService } from '../shared/listingstate.service';
import { Router } from '@angular/router';
import { CategoryDto } from '../models/CategoryDto';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-listing',
  imports: [FormsModule, CommonModule],
  templateUrl: './listing.component.html',
  styleUrl: './listing.component.css'
})
export class ListingComponent implements OnInit{
  
  
    listingDetails:Listing = 
   {
      title: '',
    price: undefined,  //overriden by the binding
    description: '',
    brand: '',
    condition: '',
    location: '',
    category: '',
    availability: ''

    }

      // Predefined categories for the dropdown.

    



  validForm:boolean = false
  formdata :FormData = new FormData()
  listingConfirmation!:listingConfirmation
   // This property controls the dropdown's visibility.
   isDropdownHidden: boolean = true;
   categories!:Array<string>
   selectedCategory:string=''

  constructor(private fileService:FileuploadService, private listingService:ListingService, 
    private listingStateService:ListingstateService,
    private router:Router
  
  ){}
  
  
  ngOnInit(): void {
    this.fetchAllCategories()
  }

  uploadListing(event:Event):void{
    //this will return the formdata that includes the file uploaded 
    this.formdata = this.fileService.uploadFile(event)
    console.log("file successfully added")
  }


  onSubmit():void{

    //the backend expects a json and a file 
    //multipart formdata allows us to send objects of different contents, the backend binds each key value to the method parameter
    //we will sent a json which is the listing information and the image of the item itself
    if(!this.listingDetails || !this.formdata.has('file')){
      throw new Error("error creating listing")

    }
    this.validForm = true
    this.listingDetails.category = this.selectedCategory.valueOf()
    console.log(this.selectedCategory)
    //wrapping the json in a blob allows to explicity define the content-type
    const listingJson = new Blob([JSON.stringify(this.listingDetails)], {type:'application/json'})
    this.formdata.append('listing', listingJson)

    console.log("appended the form data")

    this.listingService.uploadlistingItems(this.formdata).subscribe({
      next:(data:listingConfirmation)=>{
        console.log("items uploaded successfully")
        this.listingConfirmation = data //this will be required by the listingconfirmation component
        console.log(this.listingConfirmation)

       
        this.listingStateService.updateListingConfirmationState(this.listingConfirmation)
        this.router.navigate(['/listing-confirmation']) //subscribes to the observable to retrieve the latest listing creation

      
       
      },
      error:(error:Error)=>{
        console.log(error.message)
      }
    })
    //reset the form
      this.listingDetails={
        title: '',
      price: undefined,  //overriden by the binding
      description: '',
      brand: '',
      condition: '',
      location: '',
      category: '',
      availability: ''

      }
      
  }

  //show dropdown
  toggleDropdown(): void {
    this.isDropdownHidden = !this.isDropdownHidden;
    console.log("show dropdown")
  }


  fetchAllCategories(){
    this.listingService.findAllCategories()
    .subscribe({
      next:(category:CategoryDto[])=>{
        this.categories = category.map(category=>category.categoryName) 
        },
      error:(error:Error)=>{
        console.log("error", error.message)
      }
    })


  }
   
  

}
