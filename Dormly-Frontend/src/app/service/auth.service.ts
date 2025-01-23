import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { AuthResponse } from '../models/AuthResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

    url:string = "http://localhost:8099/api/v1/Dormly.com"

  constructor(private Http:HttpClient) { }

  public login(userForm:FormGroup):Observable<AuthResponse>{
    const loginUrl = '{this.url}/login'
    return this.Http.post<AuthResponse>(loginUrl, userForm)
    
  }
}
