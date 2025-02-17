import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { listingCard } from '../../models/listingCard';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
 
 url:string = "http://localhost:8099/api/v1/Dormly/Categories"

 constructor(private http:HttpClient) { }
 
  
 
 
  findByCategoryName(category: string) :Observable<listingCard[]>{
    const params = new HttpParams().set('Category', category)
    console.log("params", params)
    return this.http.get<listingCard[]>(`${this.url}/filter`, {params})

  }

 
}
