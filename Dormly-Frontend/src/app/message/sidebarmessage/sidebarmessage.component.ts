import { Component, Input, OnInit } from '@angular/core';
import { ThreadsDto } from '../../models/ThreadsDto';
import { TokenService } from '../../auth/token/token.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sidebarmessage',
  imports: [CommonModule],
  templateUrl: './sidebarmessage.component.html',
  styleUrl: './sidebarmessage.component.css'
})
export class SidebarmessageComponent implements OnInit{

name!:string
@Input()
Inbox!:Array<ThreadsDto>



constructor(private token:TokenService){}


ngOnInit(): void {
}




/**
 * our threads for inboxes return the profiles of everyones profile expectt he authenticated user for each thread object
 * each thread is a conversation, and hence we didnt want to repetitively fetch the users profile for each conversation
 * the user profile is trivial here as we only need to render the profiles of the current users inbox 
 * @returns  URL - the image profile
 */
imageOfOtherUser(inbox:ThreadsDto):URL | null{
  if(inbox.buyer.image === undefined){
    return inbox.seller.image
  }
  else{
    return inbox.buyer.image
  }

}

/**
  * we need to check for each conversation if the user is either the buyer or the seller, if hes the buyer return the sellers name
  * lets assume jack and james are two users. is jack wants to see his Inbox, we want to check for each conversation what jack is
  * this way if we know Jacks role in the conversation, we then can always return the other users information
  * each conversation is associated to a listing- and that is how the buyer and seller are figured out
 */
nameOfEachUserInInbox(inbox:ThreadsDto){
  const user = this.token.getTokenSubject()
  if(user){
    
    if(inbox.buyer.email === user){
      ///return the sellers name
      return inbox.seller.firstname + " " + inbox.seller.lastname
    }
    else{
    ///and if our user is the seller, we return the buyers name in this case
      return inbox.buyer.firstname + " " + inbox.buyer.lastname
    }
    
  }
  throw Error("user can not be empty")
}


}
