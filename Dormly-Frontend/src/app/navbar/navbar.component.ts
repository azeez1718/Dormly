import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar'
import { RouterLink } from '@angular/router';
import {MatIconModule} from '@angular/material/icon';


@Component({
  selector: 'app-navbar',
  imports: [MatToolbarModule, RouterLink, MatButtonModule, MatIconModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

}
