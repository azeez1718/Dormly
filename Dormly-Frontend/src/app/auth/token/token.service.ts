import { Injectable } from '@angular/core';

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

}
