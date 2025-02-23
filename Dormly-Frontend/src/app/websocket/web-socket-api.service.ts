import { Injectable } from '@angular/core';
import { TokenService } from '../auth/token/token.service';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { Message } from '../models/Message';
import { MessagesComponent } from '../messages/messages.component';
import { BehaviorSubject } from 'rxjs';




@Injectable({
  providedIn: 'root'
})
export class WebSocketApiService {
  //create a subject to communicate the responses back to the messages component
  private subject = new BehaviorSubject("")
  messageSubscription$ = this.subject.asObservable()


  destination:string = "/user/queue/chat"
  brokerURL:string = "http://localhost:8099/ws"
  stompClient:any
  token!:string
 

  constructor(private tokenService:TokenService) {
    this.token = this.tokenService.token as string
   }



  connect(){
    console.log("connecting to websocket...")
    let ws = new SockJS(this.brokerURL)
      this.stompClient = Stomp.over(ws)
    
    this.stompClient.connect({"Authorization" : "Bearer " + this.token}, () =>{

        ///the stompclient takes 3 parameters which includes the headers and 2 callback functions,
        ///the frames defined the handshake agreement of protocol switches, and once the event occurs we can now subscribe to the destination

        this.stompClient.subscribe(this.destination, (message:any)=>{
          //the subscribe also triggers a callback which means when a user subscribes to a destination, an event of a message could be returned
          this.onMessageRecieved(message)

        })

    },
    ///error callback
    (error:Error | any)=>{
    this.errorCallBack(error)
    }
  )



  }
  errorCallBack(error:Error):void{
    console.log(error.message)
    
  }

  disconnect(){
    if(this.stompClient!==null){
      this.stompClient.disconnect()
      console.log("disconnected")
    }
    
  }


  onMessageRecieved(message:any) {
   ///manual deserialization with websockets. using Json.parse() to convert the json into a javascript objecy
    console.log("message recieved ", message)
    this.subject.next(JSON.parse(message))
    console.log("added message to the subject")

  }


  send(message:Message):void{
    ///when the user sends a message, our in memory message broker in spring will automatically forward it to the destination the user is subscribed to
    ///the user will recieve the message immediatley assuming the connection is still live - if not we persist the chat to the db
    ///all messages are sent with the /app prefix and users are able to send messages
    ///the message object includes the recieptent and the message itself.
    ///websocket does not serialize the object into a json like HTTP does, hence we do it manually
    this.stompClient.send("/app/chat/send", {"Authorization" : "Bearer " + this.token}, JSON.stringify(message))
  }


}
