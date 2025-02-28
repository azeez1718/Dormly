import { profileListings } from "./listingCard"

export interface Profile{
    image :URL | null  
    bio:string
    location:string
    email:string
    firstname:string
    lastname:string
    profileListings:Array<profileListings>


    //the json sent by the backend must match the property names that angular maps to it, hence profileListings was changed in the dto response 
    





}
/**
 * this type is used to map to the DTO that returns the profile buyer and seller, when we are setting up the inbox messaging
 * we set profile to be either null, as we only fetch the profile picture from s3 once.. as its the same 2 users conversing
 */
export type  profileDto = Pick<Profile, 'image'  | "email" | "firstname" | "lastname">



/**
 * this is used when the user goes to click on their view profile, in which we show their profile and all their listings
 */