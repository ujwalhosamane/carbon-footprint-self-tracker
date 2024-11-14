import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-main-layout',
  templateUrl: './admin-main-layout.component.html',
  styleUrls: ['./admin-main-layout.component.css']
})
export class AdminMainLayoutComponent {
  isDropdownOpen = false;
  isModalOpen = false;

  constructor(private router: Router) {}

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  closeDropdown() {
    this.isDropdownOpen = false;
  }

  // Handle modal state
  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }

  // Navigation methods
  navigateToHome() {
    this.router.navigate(['/admin/home']);
    this.closeDropdown();
  }

  navigateToDashboard() {
    this.router.navigate(['/admin/dashboard']); 
    this.closeDropdown();
  }

  navigateToProfile() {
    this.router.navigate(['/admin/profile']);
    this.closeDropdown();
  }

  navigateToSettings() {
    this.router.navigate(['/admin/settings']);
    this.closeDropdown();
  }

  logout() {
    // Clear any stored auth tokens/session data
    localStorage.removeItem('adminToken');
    
    // Close dropdown before navigating
    this.closeDropdown();

    // Navigate to login page
    this.router.navigate(['/login']);
  }
}
