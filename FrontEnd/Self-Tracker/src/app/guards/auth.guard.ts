import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable, map, catchError, of, switchMap } from 'rxjs';
import { AuthenticationService } from '../auth/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private authService: AuthenticationService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
    // Check for admin auth first
    const adminAuth = localStorage.getItem('auth');
    if (adminAuth) {
      return this.authService.validateToken(adminAuth).pipe(
        map(response => {
          if (response.role === 'ADMIN') {
            return this.router.createUrlTree(['/admin/home']);
          }
          return true;
        }),
        catchError((error) => {
          if (error.status === 406) {
            localStorage.removeItem('auth');
          }
          return of(true);
        })
      );
    }

    // Check for user auth
    const userAuth = localStorage.getItem('__auth');
    if (userAuth) {
      return this.authService.validateToken(userAuth).pipe(
        map(response => {
          if (response.role === 'USER') {
            return this.router.createUrlTree(['/user/home']);
          }
          return true;
        }),
        catchError((error) => {
          if (error.status === 406) {
            localStorage.removeItem('__auth');
          }
          return of(true);
        })
      );
    }

    // If no auth found, allow access to auth routes
    return true;
  }
  
}
