import { Injectable } from '@angular/core';
import { jwtDecode } from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  
  constructor() { }


  set token(token:string){
    localStorage.setItem('token', token)
    console.log("token being set in the localstorage", token)
  }

  get token(){
    return localStorage.getItem('token') as string
  }


  getTokenSubject():string{
  let jwt = this.token as string
  const decoded = jwtDecode(jwt)
  
  if(decoded){
    console.log(decoded)
    return decoded.sub as string
  }
  return "Error decoding token"

    

  }

}
