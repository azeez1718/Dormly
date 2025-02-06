import { Component, OnInit } from '@angular/core';
import { FileuploadService } from '../service/fileuploads/fileupload.service';
import { ListingService } from '../service/listing/listing.service';
import { Listing } from '../models/listing';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-listing',
  imports: [FormsModule],
  templateUrl: './listing.component.html',
  styleUrl: './listing.component.css'
})
export class ListingComponent implements OnInit{
  
  
    listingDetails: Listing = {
      title: '',
      price: undefined,  //overriden by the binding
      description: '',
      brand: '',
      condition: '',
      location: '',
      category: '',
      availability: ''

    }
  validForm:boolean = false
  formdata :FormData = new FormData()

  constructor(private fileService:FileuploadService, private listingService:ListingService){}
  
  
  ngOnInit(): void {
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
    //wrapping the json in a blob allows to explicity define the content-type
    const listingJson = new Blob([JSON.stringify(this.listingDetails)], {type:'application/json'})
    this.formdata.append('listing', listingJson)

    console.log("appended the form data")

    this.listingService.uploadlistingItems(this.formdata).subscribe({
      next:(data:any)=>{
        console.log("items uploaded successfully")
       
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

}
