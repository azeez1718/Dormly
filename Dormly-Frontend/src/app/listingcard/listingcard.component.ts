import { Component, Input } from '@angular/core';
import { listingCard } from '../models/listingCard';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-listingcard',
  imports: [],
  templateUrl: './listingcard.component.html',
  styleUrl: './listingcard.component.css'
})
export class ListingcardComponent {
  
  @Input()
  card!:listingCard



  constructor(private router:Router){}


  renderProductDetails(listingId:number){
    //when the user clicks on the card, we navigate to the product details information
    //and when we navigate to the component we set the listing id as a path variable, allowing us to make an api call 
    //the api call retrieves the product in futher details

    this.router.navigate(['/product', listingId]) //path variable
  }



}
