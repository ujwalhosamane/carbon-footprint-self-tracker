import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable, map, catchError, of, finalize } from 'rxjs';
import { AuthenticationService } from '../auth/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuardGuard implements CanActivate {
  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
    document.body.style.cursor = 'wait';
    
    const auth = localStorage.getItem('auth');
    if (!auth) {
      document.body.style.cursor = 'default';
      return this.router.createUrlTree(['/auth/home']);
    }

    return this.authService.validateToken(auth).pipe(
      map(response => {
        if (response.role === 'ADMIN') {
          return true;
        }
        localStorage.removeItem('auth');
        return this.router.createUrlTree(['/auth/home']);
      }),
      catchError((error) => {
        if (error.status === 406) {
          localStorage.removeItem('auth');
        }
        return of(this.router.createUrlTree(['/auth/home']));
      }),
      finalize(() => {
        document.body.style.cursor = 'default';
      })
    );
  }
  
}
