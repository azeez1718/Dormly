import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ThreadsDto } from '../../models/ThreadsDto';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private http:HttpClient) { }
  chatUrl:string = "http://localhost:8099/api/v1/Dormly/chats"

 
  //this is used to return previous chats between a user and a seller based on a listing , whenever a user clicks on 'message seller'
  // the Listing id, acts as our source of truth as we can return chat history between currently authenticated user(buyer) & seller(owner of listing)

  conversationThreadForListing(listingId:string):Observable<ThreadsDto>{
    const chatsBylisting = `${this.chatUrl}/history/${listingId}`
    return this.http.get<ThreadsDto>(chatsBylisting)
  }

  




}
