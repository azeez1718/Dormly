import { MessageDto } from "./MessageDto";
import { profileDto } from "./Profile";

export interface ThreadsDto{
    id:number
    seller:profileDto
    buyer:profileDto
    title:string
    messages?:Array<MessageDto>  // it could be undefined, as our backend doesnt serialize null values when creating a new therad
    listingId:number
}