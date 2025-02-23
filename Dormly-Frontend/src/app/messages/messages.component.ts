import { Component, OnInit } from '@angular/core';
import { WebSocketApiService } from '../websocket/web-socket-api.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-messages',
  imports: [CommonModule],
  templateUrl: './messages.component.html',
  styleUrl: './messages.component.css'
})
export class MessagesComponent implements OnInit{

  messages : any = []
  recievedMessage:boolean = false
  constructor(private webSocket:WebSocketApiService){}

  ngOnInit():void{
  this.connect()
  this.onMessageRecieved()

  }
   onMessageRecieved(){
  this.webSocket.messageSubscription$.subscribe({
    next:(message)=>{
      if(message!==null){
        this.recievedMessage = true
        this.messages.push(message)
      }
    },
    error:(error:Error)=>{
      console.log(error)
    }
  })
}


connect(){
  console.log("calling service class to connect to websocket")
  this.webSocket.connect()
}


disconnect(){
  this.webSocket.disconnect()
}

sendmessage(){
  ///create a sample message object
  const message = {
    "content"   : "hi james its abas, hope you are well!",
    "recipient" : "james@qmul.ac.uk",
  }
  this.webSocket.send(message)

}
  
  




}
