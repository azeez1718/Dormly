import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthResponse } from '../models/AuthResponse';
import { LoginRequest } from '../models/LoginRequest';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

    url:string = "http://localhost:8099/api/v1/Dormly.com"

  constructor(private http:HttpClient) { }

  public login(login:LoginRequest):Observable<AuthResponse>{
    const loginUrl = `${this.url}/login`
    return this.http.post<AuthResponse>(loginUrl, login)
    
  }
}
