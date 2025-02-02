import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthResponse } from '../models/AuthResponse';
import { LoginRequest } from '../models/LoginRequest';
import { SignUp } from '../models/SignUp';


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

  protectedapi():Observable<any> {
    const privateUrl = `${this.url}/dashboard`
    return this.http.get(privateUrl)
  }


  register(registerDto:SignUp):Observable<AuthResponse>{
    const signUpurl = `${this.url}/Sign-up`
    return this.http.post<AuthResponse>(signUpurl, registerDto)


  }
}
