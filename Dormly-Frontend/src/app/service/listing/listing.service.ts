import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { listingCard, listingConfirmation } from '../../models/listingCard';
import { CategoryDto } from '../../models/CategoryDto';

@Injectable({
  providedIn: 'root'
})
export class ListingService {
  
  url:string = "http://localhost:8099/api/v1/Dormly.com/listing"

  constructor(private Http:HttpClient) { }



  uploadlistingItems(formdata:FormData):Observable<listingConfirmation>{
    const create = `${this.url}/create`
    console.log("about to make request")
    return this.Http.post<listingConfirmation>(create,formdata)

    
  }

  fetchListings():Observable<Array<listingCard>>{
    console.log('retrieving listings...')
    return this.Http.get<Array<listingCard>>(this.url);

  }

  fetchListingById(id:string):Observable<listingCard>{
    const url = `${this.url}/product/${id}`
    return this.Http.get<listingCard>(url)
  }

  findAllCategories():Observable<CategoryDto[]>{
    const url = `${this.url}/categories`
    return this.Http.get<CategoryDto[]>(url)
  }
}
