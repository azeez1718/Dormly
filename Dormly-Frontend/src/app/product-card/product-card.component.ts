import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ListingService } from '../service/listing/listing.service';
import { listingCard } from '../models/listingCard';
import { DashboardComponent } from '../dashboard-navbar/dashboard-navbar.component';
import { MessageService } from '../service/message/message.service';
import { ThreadsDto } from '../models/ThreadsDto';

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
      console.log(data.listingUrl)
      },
    
    error:(error:Error)=>{
      console.log('error retrieveing listing information', error)
    }


  
  })
}

  messageSeller(listingId:number){
    /**
     * this acts as our source of truth and a precheck to see if two users have a conversation based on a listing
     * and only when the the user & the seller have had a previous conversation do we then navigate to the specific thread
     * from here we can naviagate to the message component by passing the thread id
     */
    this.messageService.checkThreadExists(listingId).subscribe({
    next:(threadId:number)=>{
      console.log("we got back the id which is", threadId)
        ///we navigate to the messages component to render the thread based on the id
        this.router.navigate(["/messages:id", threadId])
      },
      error:(err:Error)=>{
        console.log(err.message)
      }
      })
  
}


}
