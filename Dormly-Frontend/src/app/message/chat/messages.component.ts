import { Component, OnDestroy, OnInit } from '@angular/core';
import { WebSocketApiService } from '../../websocket/web-socket-api.service';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from '../../dashboard-navbar/dashboard-navbar.component';
import { SidebarmessageComponent } from '../sidebarmessage/sidebarmessage.component';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from '../../service/message/message.service';
import { MessageDto } from '../../models/MessageDto';


@Component({
  selector: 'app-messages',
  imports: [CommonModule, DashboardComponent, SidebarmessageComponent],
  templateUrl: './messages.component.html',
  styleUrl: './messages.component.css'
})
export class MessagesComponent implements OnInit{

  messages : any = []
  recievedMessage:boolean = false
  returnedInbox = false
  listingId !: number 
  constructor(private webSocket:WebSocketApiService, private route:ActivatedRoute, private messageService:MessageService){}

  
  ngOnInit():void{
    if(this.setupInboxWithSeller!==null){
      ///we can fetch the chat history between a user and a seller for that specific listing
      this.fetchUserBasedOnListingId(this.setupInboxWithSeller()as string)
    }
      
      
    
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

setupInboxWithSeller(){
  const listingId = this.route.snapshot.paramMap.get("id")
  ///if listing id is truthy return it, otherwise return null
  return listingId ? listingId : null

}

fetchUserBasedOnListingId(id:string){
  this.messageService.InboxHistoryForListing(id)
  .subscribe({
    next:(message:MessageDto)=>{
    this.messages.push(message)
    
    if(this.messages){
      this.returnedInbox = true
      console.log(this.messages)
    }

  },
  error:(err:Error)=>{
    console.log(`error returning inbox with id : ${id}`, err.message)
  }
})

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
