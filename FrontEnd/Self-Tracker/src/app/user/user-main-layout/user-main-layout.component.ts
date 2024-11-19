import { Component, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-user-main-layout',
  templateUrl: './user-main-layout.component.html',
  styleUrls: ['./user-main-layout.component.css'],
})
export class UserMainLayoutComponent {
  isDropdownOpen = false;
  isModalOpen = false;

  constructor(private router: Router, private userService: UserService) {}

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
    this.userService.logOut().subscribe({
      next: () => {
        localStorage.removeItem('__auth');
        this.router.navigate(['/auth/home']);
      },
      error: (error) => {
        console.error('Logout failed:', error);
      }
    });
  }
}
