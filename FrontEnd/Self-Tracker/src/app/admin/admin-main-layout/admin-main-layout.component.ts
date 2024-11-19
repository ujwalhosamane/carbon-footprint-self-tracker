import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-admin-main-layout',
  templateUrl: './admin-main-layout.component.html',
  styleUrls: ['./admin-main-layout.component.css']
})
export class AdminMainLayoutComponent {
  isDropdownOpen = false;
  isModalOpen = false;

  constructor(private router: Router, private adminService: AdminService) {}

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  closeDropdown() {
    this.isDropdownOpen = false;
  }

  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }

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
    this.adminService.logOut().subscribe({
      next: () => {
        localStorage.removeItem('auth');
        this.closeDropdown();
        this.router.navigate(['/auth/home']);
      },
      error: (error) => {
        console.error('Logout failed:', error);
      }
    });
  }
}
