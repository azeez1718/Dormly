import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { AuthResponse } from '../models/AuthResponse';
import { loginRequest } from '../models/LoginRequest';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

    url:string = "http://localhost:8099/api/v1/Dormly.com"

  constructor(private Http:HttpClient) { }

  public login(login:loginRequest):Observable<AuthResponse>{
    const loginUrl = `${this.url}/login`
    return this.Http.post<AuthResponse>(loginUrl, login)
    
  }
}
