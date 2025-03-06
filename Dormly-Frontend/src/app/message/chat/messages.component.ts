import { Component, OnDestroy, OnInit } from '@angular/core';
import { WebSocketApiService } from '../../websocket/web-socket-api.service';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from '../../dashboard-navbar/dashboard-navbar.component';
import { SidebarmessageComponent } from '../sidebarmessage/sidebarmessage.component';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from '../../service/message/message.service';
import { ThreadsDto } from '../../models/ThreadsDto';
import { TokenService } from '../../auth/token/token.service';


@Component({
  selector: 'app-messages',
  imports: [CommonModule, DashboardComponent, SidebarmessageComponent],
  templateUrl: './messages.component.html',
  styleUrl: './messages.component.css'
})
export class MessagesComponent implements OnInit{

  messages!:Array<any>
  recievedMessage:boolean = false
  returnedInbox = false
  thread !:ThreadsDto
  newConversation:Boolean = false

  InboxProfiles!:Array<ThreadsDto> 
  
  

  constructor(private webSocket:WebSocketApiService, private route:ActivatedRoute, private messageService:MessageService,
    private jwtService:TokenService
  ){}

  
  ngOnInit():void{
    ///we want to immediatley fetch the user inbox when the component is instantiated
    this.getUserInbox()



    //on every refresh the source of truth for the threads are represented by the active route
    if(this.getPathVariable()){
      this.ConversationThread(this.getPathVariable() as string)
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

ConversationThread(threadId:string){
  ///fetches the thread associated between two users, this id is fetched from the url
  ///if the length of the messages is 0, we know there is no existing chat and we pass the threadsDto to the startNew function
  this.messageService.conversationThread(threadId).subscribe({
  next:(threads:ThreadsDto)=>{
  
    this.returnedInbox = true
    this.thread = threads
    console.log("rendering the threads", threads)

    if(!threads.messages|| threads.messages?.length===0){
      this.startNewConversation(threads)
    }
    
  },
  error:(err:Error)=>{
    console.log(err.message)
  }
  })

}

startNewConversation(threads:ThreadsDto){
  ///this will omit the messages as there is nothing there
  console.log("new conversation", threads)
  this.newConversation = true
}



getPathVariable():string | null{
  const listingId = this.route.snapshot.paramMap.get('id')
  if(listingId!==null){
    return listingId
  }
  return null
  
}


disconnect(){
  this.webSocket.disconnect()
}



sendMessage(){
  ///create a sample message object
  const message = {
    "content"   : "hi james its abas, hope you are well!",
    "recipient" : ""
  }

  this.webSocket.send(message)

}

senderImage(sender:string):URL|null{
  if(this.thread.buyer.email===sender){
    return this.thread.buyer.image
  }
  else{
  return this.thread.seller.image
  }
 
}
findUser():string{
  ///find the current authenitcated user to allow us to differentiate which side of the ui the messages get displayed on
  
  const user = this.jwtService.getTokenSubject()
  if(user){
  return user 
  }
  throw Error("user can not be Falsy")

}


getUserInbox(){
  this.messageService.getInbox().subscribe({
    next:(inbox:Array<ThreadsDto>)=>{
      console.log("--------------------------", inbox)
      this.InboxProfiles = inbox
    }, 

    error:(err:Error)=>{
      console.log(err.message)
    }
  })
}

  




}
