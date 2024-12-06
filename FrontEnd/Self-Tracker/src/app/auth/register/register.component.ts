import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  showErrorToast: boolean = false;
  showSuccessToast: boolean = false;
  loading: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthenticationService,
    private router: Router
  ) {
    this.registerForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      city: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, {
      validator: this.passwordMatchValidator
    });
  }

  ngOnInit(): void {}

  passwordMatchValidator(formGroup: FormGroup) {
    const password = formGroup.get('password')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;

    if (password !== confirmPassword) {
      formGroup.get('confirmPassword')?.setErrors({ passwordMismatch: true });
    } else {
      formGroup.get('confirmPassword')?.setErrors(null);
    }
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.loading = true;
      const userData = {
        name: this.registerForm.value.name,
        email: this.registerForm.value.email,
        city: this.registerForm.value.city,
        password: this.registerForm.value.password
      };

      this.authService.register(userData).subscribe({
        next: (response: any) => {
          console.log('Response:', response);
          this.loading = false;
          this.showSuccessToast = true;
          setTimeout(() => {
            this.showSuccessToast = false;
            this.router.navigate(['/auth/login']);
          }, 2000);
        },
        error: (error) => {
            this.loading = false;
            this.showErrorToast = true;
            setTimeout(() => {
              this.showErrorToast = false;
            }, 2000);
          
          console.error('Registration failed:', error);
        }
      });
    }
  }

}
