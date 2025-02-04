import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ListingService {
  
  url:string = "http://localhost:8099/api/v1/Dormly.com/listing"

  constructor(private Http:HttpClient) { }



  uploadlistingItems(formdata:FormData):Observable<void>{
    const create = `${this.url}/create`
    console.log("about to make request")
    return this.Http.post<void>(create,formdata)

    
  }
}
