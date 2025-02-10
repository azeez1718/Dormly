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
    profileUrl:URL //the profile of the seller
   createdDate?:Date  //may be empty

}

export type listingConfirmation = Pick<listingCard, 'listingId' | 'title' | 'firstname'|'listingUrl' |'description' | 'price' | 'location' | 'createdDate'>

export type profilePageListings = Pick<listingCard, 'listingId' | 'title' | 'price' | 'description' | 'brand' | 'condition'
                                                 | 'location' | 'category' | 'availability' | 'isSold' | 'listingUrl'>