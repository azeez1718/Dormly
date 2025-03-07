import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, ObservedValueOf } from 'rxjs';
import { ThreadsDto } from '../../models/ThreadsDto';
import { Message } from '../../models/Message';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  
  

  constructor(private http:HttpClient) { }
  chatUrl:string = "http://localhost:8099/api/v1/Dormly/chats"

 
  //this is used to return previous chats between a user and a seller based on its thread , whenever a user clicks on 'message seller'
  // the thread id is fetched from the url, which we navigated to after fetching the thread id
  //the id we pass in here is the thread id
  conversationThread(id:string):Observable<ThreadsDto>{

    const threadConversations = `${this.chatUrl}/history/${id}`
    console.log("about to call conversation thread")
    return this.http.get<ThreadsDto>(threadConversations)
  }


  getInbox():Observable<Array<ThreadsDto>>{
    console.log("calling the getInbox")
    const inboxApi = `${this.chatUrl}/inbox/preview`
    return this.http.get<Array<ThreadsDto>>(inboxApi)
    
  }

  //we use this to see if a thread exists between two users, if so the return id is returned
  // if not we return the newly created thread id. 
  checkThreadExists(id: number):Observable<number> {
    const getThreadIdApi = `${this.chatUrl}/thread/listing/${id}`
    return this.http.get<number>(getThreadIdApi)
  }

  /**
   * 
   * @param message - the message to persisted, returned to users if any messages were sent whilst they were disconnected
   * @param id  - the thread id associated with each message
   */
  persistMessages(message: Message, id:number):Observable<void> {
    const persistMessagesApi = `${this.chatUrl}/persist/message/${id}`
    return this.http.post<void>(persistMessagesApi, message)
    
  }







}
