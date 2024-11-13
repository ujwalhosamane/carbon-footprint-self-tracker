import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-main-layout',
  templateUrl: './admin-main-layout.component.html',
  styleUrls: ['./admin-main-layout.component.css']
})
export class AdminMainLayoutComponent {
  isSidebarOpen: boolean = true;
  userName: string = 'User Name'; // This should be populated from auth service

  constructor(private router: Router) {}

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  signOut() {
    // Implement sign out logic here
    // For example:
    // this.authService.signOut();
    this.router.navigate(['/login']);
  }
}
