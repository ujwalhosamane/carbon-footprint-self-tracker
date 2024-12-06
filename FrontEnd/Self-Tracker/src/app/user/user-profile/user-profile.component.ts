import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
  animations: [
    trigger('toastAnimation', [
      state('void', style({
        transform: 'translateY(-100%)',
        opacity: 0
      })),
      state('visible', style({
        transform: 'translateY(0)',
        opacity: 1
      })),
      state('hidden', style({
        transform: 'translateY(100vh)',
        opacity: 0
      })),
      transition('void => visible', [
        animate('0.5s ease-in-out')
      ]),
      transition('visible => hidden', [
        animate('0.5s ease-in-out')
      ])
    ])
  ]
})
export class UserProfileComponent implements OnInit {
  user: any = {};
  passwordForm: FormGroup;
  errorMessage: string = '';
  successMessage: string = '';
  toastState: 'visible' | 'hidden' = 'hidden';
  loading: boolean = false;

  constructor(
    private userService: UserService,
    private fb: FormBuilder
  ) {
    this.passwordForm = this.fb.group({
      currentPassword: ['', [Validators.required]],
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    }, { validator: this.passwordMatchValidator });
  }

  ngOnInit() {
    this.loadUserProfile();
  }

  loadUserProfile() {
    this.loading = true;
    this.userService.getUserDetailsAfterLogin().subscribe({
      next: (response) => {
        this.user = response;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading user profile:', error);
        this.showErrorMessage('Failed to load user profile');
        this.loading = false;
      }
    });
  }

  passwordMatchValidator(g: FormGroup) {
    return g.get('newPassword')?.value === g.get('confirmPassword')?.value
      ? null : { 'mismatch': true };
  }

  showSuccessMessage(message: string) {
    this.successMessage = message;
    this.toastState = 'visible';
    setTimeout(() => {
      this.toastState = 'hidden';
      setTimeout(() => {
        this.successMessage = '';
      }, 500);
    }, 2000);
  }

  showErrorMessage(message: string) {
    this.errorMessage = message;
    this.toastState = 'visible';
    setTimeout(() => {
      this.toastState = 'hidden';
      setTimeout(() => {
        this.errorMessage = '';
      }, 500);
    }, 2000);
  }

  onSubmit() {
    if (this.passwordForm.valid) {
      if (this.passwordForm.hasError('mismatch')) {
        this.showErrorMessage('New password and confirm password do not match');
        return;
      }

      this.loading = true;
      const passwordData = {
        currentPassword: this.passwordForm.get('currentPassword')?.value,
        newPassword: this.passwordForm.get('newPassword')?.value
      };

      this.userService.updatePassword(passwordData).subscribe({
        next: (response) => {
          if (response && response.passwordUpdated) {
            this.showSuccessMessage('Password updated successfully');
            this.passwordForm.reset();
          }
          this.loading = false;
        },
        error: (error: HttpErrorResponse) => {
          if (error.status === 400) {
            this.showErrorMessage('User not found');
          } else if (error.status === 406) {
            this.showErrorMessage('Invalid or expired session. Please login again.');
          } else {
            this.showErrorMessage('An error occurred while updating password');
          }
          console.error('Error updating password:', error);
          this.loading = false;
        }
      });
    } else {
      if (this.passwordForm.get('newPassword')?.errors?.['minlength']) {
        this.showErrorMessage('New password must be at least 6 characters long');
      } else {
        this.showErrorMessage('Please fill in all required fields');
      }
    }
  }
}
