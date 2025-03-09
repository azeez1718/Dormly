import { Component, OnDestroy, OnInit } from '@angular/core';
import { WebSocketApiService } from '../../websocket/web-socket-api.service';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from '../../dashboard-navbar/dashboard-navbar.component';
import { SidebarmessageComponent } from '../sidebarmessage/sidebarmessage.component';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from '../../service/message/message.service';
import { ThreadsDto } from '../../models/ThreadsDto';
import { TokenService } from '../../auth/token/token.service';
import { Message } from '../../models/Message';
import { FormsModule } from '@angular/forms';
import { MessageDto } from '../../models/MessageDto';


@Component({
  selector: 'app-messages',
  imports: [CommonModule, DashboardComponent, SidebarmessageComponent, FormsModule],
  templateUrl: './messages.component.html',
  styleUrl: './messages.component.css'
})
export class MessagesComponent implements OnInit{

  inputMessage!:string
  messages!:Array<any>
  recievedMessage:boolean = false
  returnedInbox = false
  thread !:ThreadsDto
  newConversation:Boolean = false

  InboxProfiles!:Array<ThreadsDto> 
  
  

  constructor(private webSocket:WebSocketApiService, private route:ActivatedRoute, private messageService:MessageService,
    private jwtService:TokenService , private router:Router
  ){}

  
  ngOnInit():void{
    ///we want to immediatley fetch the user inbox when the component is instantiated
    this.getUserInbox()
    ///we also want to make the api call that renders the messages for the first inbox 
    



    //subscribe to the param map to always listen to changes in the url
    this.route.paramMap.subscribe(param=>{
      const threadId = param.get("id")
      if(threadId){
        this.ConversationThread(threadId)
      }
    })
    
   
      
    console.log("calling handshake")
  
  this.connect()
  this.onMessageRecieved()

  }

   onMessageRecieved(){
  this.webSocket.messageSubscription$.subscribe({
    next:(message:MessageDto | null)=>{
      if(message!==null){
        console.log("i got the message")
        console.log(message)
        this.thread.messages?.push(message)
        this.recievedMessage = true
        console.log("after appending", this.thread.messages)
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
  console.log(`i clicked on profile with id ${threadId} so this is what im calling`)
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


disconnect(){
  this.webSocket.disconnect()
}

appendSentMessageToUi(user:string, input:string){
  ///append the message to the messages in the thread, so the user who sent the message has seen it in his own ui
  ///first we must convert the input message into a DTO
   const updateUi:MessageDto = {
    "sender": user,
    "message": input,
    "timestamp": new Date()
  }
  //on the senders side, he sees it as the send message, and the reciever we update their ui to display their recieved message from subscription
  this.thread.messages?.push(updateUi)

}

sendMessage(){
  ///create a message object that will be deserialized and used in the backend
  ///because the threads object returns the conversation between 2 users. from this we know who our principal is sending the message to
  ///if we know the current logged in user, when he presses send we just set the email of other user in the conversation as the recipient
  const user = this.findUser()
  this.appendSentMessageToUi(user, this.inputMessage)
  if(this.thread){
    const recipient =  this.thread.buyer.email === user ? this.thread.seller.email : this.thread.buyer.email 
    console.log(recipient)
    const message = new Message(recipient, this.inputMessage)
    this.webSocket.send(message)

    //persist the message, no need to subscribe as it returns void
    this.messageService.persistMessages(message, this.thread.id)

  }

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
      this.InboxProfiles = inbox

      if(inbox.length ===0){
        console.log("user has no conversations")
      }
      ///fetch the message for the first profile returned. 
      else{
        if(this.route.snapshot.paramMap.get('id')===null){
        ///this means the user navigated to the messages component without an id in the url
        this.router.navigate(['/messages', inbox[0].id])
      }
    }

    }, 

    error:(err:Error)=>{
      console.log(err.message)
    }
  })
}

  




}
