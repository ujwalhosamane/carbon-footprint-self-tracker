import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthenticationService
  ) {}

  registrationForm: FormGroup = new FormGroup({});
  isSubmitting: boolean = false;

  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [
        Validators.required, 
        Validators.minLength(6), 
        Validators.pattern('^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{6,}$')
      ]],
      city: ['', Validators.required],
    });
  }

  onSubmit(): any {
    if (this.registrationForm.valid) {
      this.isSubmitting = true;
      let data = this.registrationForm.value;
      this.authService.register(data).subscribe({
        next: () => {
          console.log("Success");
          this.isSubmitting = false;
        },
        error: (error) => {
          console.log(error);
          this.isSubmitting = false;
        }
      });
    }
  }
}
