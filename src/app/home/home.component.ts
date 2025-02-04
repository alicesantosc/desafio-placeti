import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  template: `
   
    
    <router-outlet></router-outlet>
    
  `,
  imports: [CommonModule, RouterModule],
})
export class HomeComponent {}
