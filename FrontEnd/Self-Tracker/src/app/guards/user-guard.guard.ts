import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable, map, catchError, of } from 'rxjs';
import { AuthenticationService } from '../auth/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class UserGuardGuard implements CanActivate {
  constructor(private router: Router, private authService: AuthenticationService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
    const auth = localStorage.getItem('__auth');
    if (!auth) {
      return this.router.createUrlTree(['/auth/home']);
    }

    return new Promise((resolve) => {
      this.authService.validateToken(auth).pipe(
        map(response => {
          if (response.role === 'USER') {
            resolve(true);
            return;
          }
          localStorage.removeItem('__auth');
          resolve(this.router.createUrlTree(['/auth/home']));
        }),
        catchError((error) => {
          if (error.status === 406) {
            localStorage.removeItem('__auth');
          }
          resolve(this.router.createUrlTree(['/auth/home']));
          return of(null);
        })
      ).subscribe();
    });
  }
  
}
