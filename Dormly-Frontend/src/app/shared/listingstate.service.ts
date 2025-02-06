import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { listingConfirmation } from '../models/listingCard';

@Injectable({
  providedIn: 'root'
})
export class ListingstateService {

  /**
   * This class allows us to manage state and pass data between different components
   * behaviour subject holds the last emitted value, meaning whenever we make a listing and return a dto, we update the behaviour subject 
   * behaviour subject is done using next(), we later convert the behaviour subj to an observable
   * observbales allow us to subscribe to the data, behaviour subject is of type observable
   * converting it to an observable means subscribes cant alter or add values to the subject, they can only subscribe(process data/read)
   * 
   * in the component class (listingconfirmation) it requires the dto returned from 'ListingComponent'
   * we can subscribe to the observable there and process the data to render
   * 
   * it can hold an initial value of null
   */

  private subject = new BehaviorSubject<listingConfirmation |null>(null)
  listingConfirmation$ = this.subject.asObservable()

  constructor() { }


  updateListingConfirmationState(dto:listingConfirmation){
    this.subject.next(dto)
   
  }
}
