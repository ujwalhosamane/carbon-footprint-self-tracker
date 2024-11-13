import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup = new FormGroup({});
  isSubmitting: boolean = false;
  showErrorToast: boolean = false;
  showSuccessToast: boolean = false;
  successMessage: string = "";
  errorMessage: string = "";

  // Variables to hold the input values for email (as username) and password
  email: string = '';
  password: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Initialize the login form with email and password validation
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]], // Email is required and must be in correct format
      password: ['', [Validators.required, Validators.minLength(6)]] // Password is required and must be at least 6 characters
    });
  }

  // onSubmit method to handle the form submission
  onSubmit(): void {
    if (this.loginForm.valid) {
      this.isSubmitting = true;
      let loginData = {
        username: this.email,  // Sending email as username
        password: this.password
      };

      this.authService.login(loginData).subscribe({
        next: (data) => {
          console.log(data);
          localStorage.setItem('__auth', data.token); // Store the authentication token

          // Check user role and navigate accordingly
          if (data.role === 'USER') {
            this.showErrorToast = true;
            this.errorMessage = "You are not authorized to access these resources.";
            setTimeout(() => {
              this.router.navigate(['/user/home']); // Redirect to user profile if role is USER
            }, 1500);
          } else {
            this.router.navigate(['/admin/dashboard']); // Redirect to admin dashboard
          }
        },
        error: (error) => {
          console.log(error);
          this.showErrorToast = true;
          this.errorMessage = error.message; // Show error message from backend
        },
        complete: () => {
          // Hide success/error toast after 1.5 seconds
          setTimeout(() => {
            this.showErrorToast = false;
            this.showSuccessToast = false;
          }, 1500);
        }
      });
    }
  }
}
