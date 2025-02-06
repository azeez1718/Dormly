import { Component, Input } from '@angular/core';
import { listingCard } from '../models/listingCard';

@Component({
  selector: 'app-listingcard',
  imports: [],
  templateUrl: './listingcard.component.html',
  styleUrl: './listingcard.component.css'
})
export class ListingcardComponent {
  
  @Input()
  card!:listingCard

}
