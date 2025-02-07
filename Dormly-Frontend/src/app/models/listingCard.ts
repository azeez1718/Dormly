export interface listingCard{
    listingId:number
    title:string
    price:number
    description:string
    brand:string
    condition:string
    isSold:boolean
    location:string
    category:string
    availability:string
    listingUrl:URL
    firstname:string  //the name of the seller
    lastname:string
    profileUrl:URL
   createdDate?:Date  //may be empty

}

export type listingConfirmation = Pick<listingCard, 'listingId' | 'title' | 'listingUrl' |'description' | 'price' | 'location' | 'createdDate'>