import { CommonModule } from '@angular/common';
import { Component, OnInit, Query, ɵresetCompiledComponents } from '@angular/core';
import { ActivatedRoute, Route, Router, RouterLink } from '@angular/router';
import { ListingService } from '../service/listing/listing.service';
import { CategoryDto } from '../models/CategoryDto';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink, CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit{

  categories:Array<string> = []
  categoriesReturned:boolean = false
  
  constructor(private listingService:ListingService, private router:Router, private route:ActivatedRoute){}
  
  
  ngOnInit() :void{
    this.findAllCategories()
  }

  ///we dont want categories statically placed in side navbar 
  findAllCategories(){
    this.listingService.findAllCategories()
    .subscribe({
      next:(res:CategoryDto[])=>{
        console.log(res)
        res.forEach(category=>this.categories.push(category.categoryName))
        this.categoriesReturned = true
        console.log("categories returned")
        
      },
      error:(err:Error)=>{
        console.log("error retrieving categories", err.message)
      }
    })
  }

  /**
   * we want to make the url the single soruce of truth, hence the home component will always look to the url when deciding what to render
   * @param category - the category to binded as a query param to the URL
   */
  fetchByCategory(Category:string):void{
    console.log("we clicked the category :", Category)
    this.router.navigate([] //dont navigate , stay on the component that instanitates this component e.g home
     ,
      {
        relativeTo:this.route, //stay on the current route
        queryParams:{Category}, //the query param will be default to ?category:books
        queryParamsHandling: 'merge' //merge any existing queries, useful for when we add sorts and pagination

      })
  }

}
