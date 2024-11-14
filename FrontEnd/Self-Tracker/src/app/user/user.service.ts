import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  constructor(@Inject(PLATFORM_ID) private platformId: Object, private http : HttpClient) { }

  private url= environment.baseUrl+'/user';

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

  getLeaderBoardOnFootprint():Observable<any> {
    return this.http.get(this.url + "/leaderBoard/footprint", {headers:this.headers});
  }

  getLeaderBoardOnRewardPoints():Observable<any> {
    return this.http.get(this.url + "/leaderBoard/rewardPoints", {headers:this.headers});
  }
  
  getLeaderBoardOnSixMonthRewardPoints():Observable<any> {
    return this.http.get(this.url + "/leaderBoard/rewardPoints/sixMonths", {headers:this.headers});
  }
}
