import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private loginService: LoginService, private router: Router) {}

  onLogin() {
    const userCredentials = { username: this.email, password: this.password };

    this.loginService.loginUser(userCredentials).subscribe(
      (response: any) => {
        // const token = ;
        // Handle successful login response'
        // console.log(userCredentials);
        alert('Login successful!');
        console.log(response);

        // Redirect user to the main/home page
        this.router.navigate(['/tracker']);
      },
      (error: any) => {
        // Handle login failure
        console.error('Login failed:', error);
        
        alert('Login failed. Please check your email and password.');
      }
    );
  }
}
