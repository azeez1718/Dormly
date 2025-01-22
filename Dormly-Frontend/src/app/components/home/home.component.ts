import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';

@Component({
  selector: 'app-home',
  imports: [RouterLink, MatSlideToggleModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
