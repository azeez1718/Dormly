import { CommonModule } from '@angular/common';
import { Component, ɵresetCompiledComponents } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {


  //sidenav:boolean = false


  constructor(){}



}
