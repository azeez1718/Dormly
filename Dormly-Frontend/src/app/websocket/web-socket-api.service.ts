import { Injectable } from '@angular/core';
import { TokenService } from '../auth/token/token.service';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { Message } from '../models/Message';
import { MessagesComponent } from '../message/chat/messages.component';
import { BehaviorSubject } from 'rxjs';
import { MessageDto } from '../models/MessageDto';
import { MessageService } from '../service/message/message.service';




@Injectable({
  providedIn: 'root'
})
export class WebSocketApiService {
  //create a subject to communicate the responses back to the messages component
  private subject = new BehaviorSubject<MessageDto | null>(null)
  messageSubscription$ = this.subject.asObservable()



  brokerURL:string = "http://localhost:8099/ws"
  stompClient:any
  token!:string
  username = "james@qmul.ac.uk"

  constructor(private tokenService:TokenService) {
    this.token = this.tokenService.token as string
  }



  connect(){
    console.log("connecting to websocket...")
    let ws = new SockJS(this.brokerURL)
    this.stompClient = Stomp.over(ws)//wraps the ws over a stomp protocol to allow usage of stomp protocols over the ws connection
    console.log("connected to websocket")
    

    ///user will only subscribe to his destination
    let subject = this.tokenService.getTokenSubject()
    
    this.stompClient.connect({"Authorization" :"Bearer " + this.token}, () =>{
      console.log("connected with STOMP");

        ///the stompclient takes 3 parameters which includes the headers and 2 callback functions,
        ///the frames define the stomp protocol connection over the established WS connection, and once the event occurs we can now subscribe to the destination
        ///the user who is sending the request should be subscribed to his own queue
        ///we need the subject of the users token
        this.stompClient.subscribe(`/user/${subject}/queue/chat`, (message:any)=>{
          console.log("i got back a message")
          //the subscribe also triggers a callback which means when a user subscribes to a destination, an event of a message could be returned
          this.onMessageRecieved(message.body)
          

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
    setTimeout(()=>{
      this.connect()
    },
    5000) //reconnect after 5 seconds
    
  }


  onMessageRecieved(message:any) {
    if(message){
    
    console.log("message recieved before deserializing", message)
    ///manual deserialization with websockets. using Json.parse() to convert the json into a javascript object
    //message dto includes the content and the sender(the person who sent this message to our subscribed user)
    const payload:MessageDto = JSON.parse(message)
    this.subject.next(payload)
    }
 

  }


  send(message:Message):void{
    ///when the user sends a message, our in memory message broker in spring will automatically forward it to the destination the user is subscribed to
    ///the user will recieve the message immediatley assuming the connection is still live - if not we persist the chat to the db
    ///all messages are sent with the /app prefix and users are able to send messages
    ///the message object includes the recieptent and the message itself.
    ///websocket does not serialize the object into a json like HTTP does, hence we do it manually
    console.log(message)
    this.stompClient.send("/app/chat/send", {}, JSON.stringify(message))
   
   
  }


}
