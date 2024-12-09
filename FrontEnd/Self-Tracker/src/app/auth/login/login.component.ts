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

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.email]], 
      password: ['', [Validators.required, Validators.minLength(6)]] 
    });
  }

  private showToast(isError: boolean, message: string) {
    if (isError) {
      this.showErrorToast = true;
      this.errorMessage = message;
    } else {
      this.showSuccessToast = true;
      this.successMessage = message;
    }

    setTimeout(() => {
      this.showErrorToast = false;
      this.showSuccessToast = false;
    }, 1500);
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.isSubmitting = true;
      
      let loginData = {
        username: this.loginForm.value.username,  
        password: this.loginForm.value.password
      };

      this.authService.login(loginData).subscribe({
        next: (data) => {
          if (data.role === 'USER') {
            localStorage.setItem('__auth', data.token);
            this.showToast(false, "Successfully logged in!");
            setTimeout(() => {
              this.isSubmitting = false;
              this.router.navigate(['/user/home']);
            }, 1500);
          } else if(data.role === 'ADMIN') {
            localStorage.setItem('auth', data.token);
            this.showToast(false, "Successfully logged in!");
            setTimeout(() => {
              this.isSubmitting = false;
              this.router.navigate(['/admin/home']);
            }, 1500);
          }
        },
        error: (error) => {
          console.log(error);
          this.isSubmitting = false;
          this.showToast(true, "Login failed. Please try again.");
        }
      });
    }
  }
}
