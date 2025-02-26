import { Component, OnDestroy, OnInit } from '@angular/core';
import { WebSocketApiService } from '../../websocket/web-socket-api.service';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from '../../dashboard-navbar/dashboard-navbar.component';
import { SidebarmessageComponent } from '../sidebarmessage/sidebarmessage.component';


@Component({
  selector: 'app-messages',
  imports: [CommonModule, SidebarmessageComponent, DashboardComponent],
  templateUrl: './messages.component.html',
  styleUrl: './messages.component.css'
})
export class MessagesComponent implements OnInit{

  messages : any = []
  recievedMessage:boolean = false
  constructor(private webSocket:WebSocketApiService){}

  ngOnInit():void{
    console.log("calling handshake")
  
  this.connect()
  this.onMessageRecieved()

  }
   onMessageRecieved(){
  this.webSocket.messageSubscription$.subscribe({
    next:(message)=>{
      if(message!==null){
        this.recievedMessage = true
        console.log("i got the message")
        this.messages.push(message)
      }
    },
    error:(error:Error)=>{
      console.log(error)
    }
  })
}


connect(){
  console.log("----------------- hello")
  console.log("calling service class to connect to websocket")
  this.webSocket.connect()
}


disconnect(){
  this.webSocket.disconnect()
}

sendMessage(){
  ///create a sample message object
  const message = {
    "content"   : "hi james its abas, hope you are well!",
    "recipient" : "james@qmul.ac.uk",
  }
  this.webSocket.send(message)

}
  

  




}
