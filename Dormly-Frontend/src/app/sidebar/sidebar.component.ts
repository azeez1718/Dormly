import { CommonModule } from '@angular/common';
import { Component, OnInit, ɵresetCompiledComponents } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ListingService } from '../service/listing/listing.service';
import { CategoryDto } from '../models/CategoryDto';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink, CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit{

  categories:Array<String> = []
  categoriesReturned:boolean = false
  
  constructor(private listingService:ListingService){}
  
  
  ngOnInit() :void{
    this.findAllCategories()
  }

  ///we dont want categories statically placed in side navbar 
  findAllCategories(){
    console.log("entered")
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

  //whenever the user clicks a category we bind category as a query parameter to the url



}
