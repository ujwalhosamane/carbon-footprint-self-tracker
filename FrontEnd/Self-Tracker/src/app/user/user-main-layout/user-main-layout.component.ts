import { Component, HostListener } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-main-layout',
  templateUrl: './user-main-layout.component.html',
  styleUrls: ['./user-main-layout.component.css'],
})
export class UserMainLayoutComponent {
  isDropdownOpen = false;
  isModalOpen = false;

  constructor(private router: Router) {}

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (!target.closest('.relative')) {
      this.isDropdownOpen = false;
    }
  }

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  logout() {
  
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    
   
    this.router.navigate(['/auth/login']);
  }
}
