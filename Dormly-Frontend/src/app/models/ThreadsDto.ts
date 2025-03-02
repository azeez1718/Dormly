import { profileDto } from "./Profile";

export interface MessageDto{
    seller:profileDto
    buyer:profileDto
    content:string
    title:string //this will be displayed at the top of the chat, the title of the lisitng in question
    description:string
    createdAt:Date
}