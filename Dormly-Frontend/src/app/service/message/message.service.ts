import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, ObservedValueOf } from 'rxjs';
import { ThreadsDto } from '../../models/ThreadsDto';
import { Message } from '../../models/Message';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  
  chatUrl:string = "http://localhost:8099/api/v1/Dormly/chats"
  private threadSubject:BehaviorSubject<ThreadsDto|null> = new BehaviorSubject<ThreadsDto | null>(null)
  thread$ = this.threadSubject.asObservable()


  constructor(private http:HttpClient) { }
  

 
  //this is used to return previous chats between a user and a seller based on its thread , whenever a user clicks on 'message seller'
  // the thread id is fetched from the url, which we navigated to after fetching the thread id
  //the id we pass in here is the thread id
  conversationThread(id:string){
    const threadConversations = `${this.chatUrl}/history/${id}`
    console.log("about to call conversation thread")
    return this.http.get<ThreadsDto>(threadConversations)
    .subscribe({
      next:(dto:ThreadsDto)=>{
        if(dto){
          this.threadSubject.next(dto)
          console.log("added thread to subject")
        }
      },
      error:(err:Error)=>{
        throw Error(err.message)
      }
    }
    
  )
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

  /**
   * this acts as our source of truth, when a user recieves a message from the stomp subscription, the user 
   * can update the state of the thread, appending the recieved message to the message object
   * Decouples the handling of the susbcription away from the websocket and now the thread handles the updating of the ui
   * 
   * Sent messages are also updated to this thread, if the authenticated user is the sender then the message gets displayed on the sent side
   * @param Message - updates the message object of our thread state
   */
  addNewMessage(Message:MessageDto){

  }



}
