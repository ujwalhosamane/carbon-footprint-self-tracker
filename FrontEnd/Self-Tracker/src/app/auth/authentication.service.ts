import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { environment } from 'src/environments/environment';
import { isPlatformBrowser } from '@angular/common';
import { Observable,of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private apiUrl: string = environment.baseUrl;

  private isAuthenticated:boolean=false;

  private getToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem("__auth");
    }
    return null;
  }
  private token=this.getToken()

  public headers=new HttpHeaders({
    'Authorization':`Bearer ${this.token}`
  })
  constructor( @Inject(PLATFORM_ID) private platformId: Object,private http: HttpClient) { }

  login(data: any): Observable<any> {
    console.log(data)
    return this.http.post<any>(this.apiUrl + '/auth/login', data,{headers:this.headers});
  }

  register(data: any): Observable<any> {
    return this.http.post<any>(this.apiUrl + '/auth/register', data);
  }

  
}
