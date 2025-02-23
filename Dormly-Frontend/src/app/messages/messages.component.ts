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
  constructor(private webSocket:WebSocketApiService){}

  ngOnInit():void{
  this.onMessageRecieved()

  }
   onMessageRecieved(){
  this.webSocket.messageSubscription$.subscribe({
    next:(message)=>{
      if(message){
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
  
  




}
