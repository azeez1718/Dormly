import { Component, OnInit } from '@angular/core';
import { DashboardComponent } from '../dashboard-navbar/dashboard-navbar.component';
import { AuthService } from '../auth/auth.service';
import { CommonModule } from '@angular/common';
import { ListingcardComponent } from '../listingcard/listingcard.component';
import { listingCard } from '../models/listingCard';
import { ListingService } from '../service/listing/listing.service';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { NavbarComponent } from '../navbar/navbar.component';
import { ActivatedRoute } from '@angular/router';
import { CategoryService } from '../service/category/category.service';


@Component({
  selector: 'app-home',
  imports: [ CommonModule, ListingcardComponent, SidebarComponent, DashboardComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
 cards!:Array<listingCard>
 hasLoaded:boolean = false
 category:string = ''



  constructor(private listingService:ListingService, private activaedRoute:ActivatedRoute, private categoryService:CategoryService){}
  
  
  ngOnInit(): void {
    this.activaedRoute.queryParamMap.subscribe(data=>{
      this.category = data.get('Category') as string
      console.log("im null initially, because no query was made", this.category)
      if(this.category == null){
        this.fetchAllListings()
        }
      else{  
      console.log("going to fetch all listings")
      this.fetchListingsByCategoryName()
      }
    })
   
   
  }

  fetchAllListings():void{
  this.listingService.fetchListings().subscribe({
    next:(listingDTO:Array<listingCard>)=>{
      console.log(listingDTO)
      this.cards = listingDTO
      this.hasLoaded = true
    },

    error:(error:Error)=>{
      console.log('unable to retrieve listings' , error.message)
    }
  })

  }


  fetchListingsByCategoryName():void{
    console.log("this.category", this.category)
    this.categoryService.findByCategoryName(this.category).subscribe({
      next:(listingDTO:listingCard[])=>{
      this.cards = listingDTO
      this.hasLoaded = true
      console.log(`listings for category: ${this.category} is ${this.cards}`)
      }
    })

  }

  
 
 

  





}
