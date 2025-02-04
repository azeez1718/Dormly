import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-listing',
  imports: [],
  templateUrl: './listing.component.html',
  styleUrl: './listing.component.css'
})
export class ListingComponent implements OnInit{
  
  
  
  
  
  constructor(){}
  
  
  
  
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  uploadListing(event:Event):void{
    console.log("file selected")
  }

}
