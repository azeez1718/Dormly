import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Profile } from '../../models/Profile';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  
   url:string = "http://localhost:8099/api/v1/Dormly.com/profile"

  constructor(private Http:HttpClient) { }



  public fetchUserProfile():Observable<Profile>{
    const profileEndpoint = `${this.url}/my-account`
    return this.Http.get<Profile>(profileEndpoint);
  }

  uploadProfilePicture(file: FormData):Observable<void>{
    const uploadPicture = `${this.url}/upload-photo`
    console.log("about to make the api request")
    console.log(Array.from(file.entries()));

    return this.Http.post<void>(uploadPicture, file)

  }

  getProfilePicture():Observable<URL>{
    const getPicture = `${this.url}/home/profile-picture`
    return this.Http.get<URL>(getPicture)
  }
  

}
