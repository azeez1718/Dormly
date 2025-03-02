import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ListingService } from '../service/listing/listing.service';
import { listingCard } from '../models/listingCard';
import { DashboardComponent } from '../dashboard-navbar/dashboard-navbar.component';
import { MessageService } from '../service/message/message.service';

@Component({
  selector: 'app-product-card',
  imports: [DashboardComponent],
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.css'
})
export class ProductCardComponent implements OnInit {
  
  /**
   * As we already binded the listing id to url of this component, we just need to fetch it by getting the current snapshot of active route
   * 
   */

  //params are string or null by default
  listingId!:string | null
  listingProduct!:listingCard
  
  constructor(private route:ActivatedRoute, private listingService:ListingService, private router:Router, private messageService:MessageService){}

  
  ngOnInit(): void {
    this.fetchListingDetail()
    
  }


  fetchListingDetail(){
    this.listingId = this.route.snapshot.paramMap.get('id')
    if(!this.listingId){
      throw new Error('there is no id present')
    }
    this.listingService.fetchListingById(this.listingId).subscribe({
      next:(data:listingCard)=>{
      this.listingProduct = data
      },
    
    error:(error:Error)=>{
      console.log('error retrieveing listing information', error)
    }


  
  })
}

  messageSeller(id:string){
    /**
     * this acts as our source of truth and we fetch the user associated with this specific listing
     * and only when the the user & the seller have had a previous conversation do we then navigate to the specific thread
     */
    this.messageService.InboxHistoryForListing(id)
    

  }


}
