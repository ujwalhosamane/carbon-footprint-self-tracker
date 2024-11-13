import { Component } from '@angular/core';
import { Router } from '@angular/Router';
import { RegisterService,RegisterData } from '../services/register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(
    private router: Router,
    private registerService: RegisterService  // Inject the RegisterService
  ) {}
  onRegister(registerForm: any) {
    if (registerForm.valid) {
      const registerData: RegisterData = {
        name: registerForm.value.name,
        email: registerForm.value.email,
        password: registerForm.value.password,
        city: registerForm.value.city
      };
  
      // Call the register service to submit the form
      this.registerService.registerUser(registerData).subscribe(
        (response: any) => {
          // Handle success (user registered)
          const jsonResponse = {message : response}
          console.log(jsonResponse);
          this.router.navigate(['/login']);
        },
        (error) => {
          // Handle error (display error message or log it)
          console.error('Registration failed:', error);
        }
      );
    } else {
      // Log validation errors for debugging
      console.log('Form is invalid', registerForm.errors);
      for (const control in registerForm.controls) {
        if (registerForm.controls[control].invalid) {
          console.log(`${control} is invalid`);
        }
      }
    }
}
}
