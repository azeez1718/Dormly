import { profileListings } from "./listingCard"

export interface Profile{
    image :URL
    bio:string
    location:string
    email:string
    firstname:string
    lastname:string
    profileListings:Array<profileListings>

    //the json sent by the backend must match the property names that angular maps to it, hence profileListings was changed in the dto response 
    




}
/**
 * this is used when the user goes to click on their view profile, in which we show their profile and all their listings
 */