import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SuggestionDTO } from '../models/suggestion-dto';

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

  //GetAllGoal
  getAllGoal():Observable<any> {
    return this.http.get(this.url + "/goal/get", {headers:this.headers});
  }

  //getLastN-Months Crabon Foot print details
  getLastNMonthsCarbonFootprint(n: number): Observable<any> {
    return this.http.get(this.url + `/carbonFootprint/getAllSums/${n}`, {headers:this.headers});
  }

  //get Carbon Footprint for month and year
  getCarbonFootprintForMonthAndYear(month: String, year: number): Observable<any> {
    return this.http.get(this.url + `/carbonFootprint/get/${month}/${year}`, {headers:this.headers});
  }

  //get latest Carbon Footprint details
  getLatestCarbonFootprint():Observable<any>{
    return this.http.get(this.url + "/carbonFootprint/get/latest", {headers:this.headers});
  }

  //get n insights
  getNInsights(n:number):Observable<any>{
    return this.http.get(this.url + `/getNInsights/${n}`, {headers:this.headers});
  }

  //add carbon footprint
  addCarbonFootprint(carbonFootprint:any):Observable<any>{
    return this.http.post(this.url + "/carbonFootprint/add", carbonFootprint, {headers:this.headers});
  }

  //update carbon footprint
  updateCarbonFootprint(carbonFootprint:any):Observable<any>{
    return this.http.put(this.url + "/carbonFootprint/update", carbonFootprint, {headers:this.headers});
  }

  //delete carbon footprint by month and year
  deleteCarbonFootprintByMonthAndYear(month:String, year:number):Observable<any>{
    return this.http.delete(this.url + `/carbonFootprint/delete/${month}/${year}`, {headers:this.headers});
  }

  //get user details after login
  getUserDetailsAfterLogin():Observable<any>{
    return this.http.get(this.url + "/afterLogin/get", {headers:this.headers});
  }

  //update password
  updatePassword(passwordData:any):Observable<any>{
    return this.http.put(environment.baseUrl + "/auth/updatePassword", passwordData, {headers:this.headers});
  }

  //get emission factor
  getEmissionFactor():Observable<any>{
    return this.http.get(this.url + "/carbonFootprint/get/emissionFactor", {headers:this.headers});
  }

  //gte user's goal count
  getUserGoalCount():Observable<any>{
    return this.http.get(this.url + "/goal/total-count", {headers:this.headers});
  }

  //get user's latest activity
  getUserLatestActivity():Observable<any>{
    return this.http.get(this.url + "/carbonFootprint/latest-activity", {headers:this.headers});
  }

  // log out
  logOut():Observable<any>{
    return this.http.post(environment.baseUrl + "/auth/logout", {}, {headers:this.headers});
  }
  
  //get N insights for user
  getNInsightsForUser(n:number):Observable<any>{
    return this.http.get(this.url + `/getNInsights/${n}`, {headers:this.headers});
  }

  //get latest suggestion
  getLatestSuggestion():Observable<any> {
    return this.http.get(this.url + `/suggestion/latest`, {headers:this.headers});
  }

  //get regenrated suggestion
  getRegeneratedSuggestion(suggestionId: number):Observable<any> {
    return this.http.get(this.url + `/suggestion/regenerate`, {
      params: { suggestionId },
      headers: this.headers
    });
  }

  //update regenerated suggestion
  updateRegeneratedSuggestion(suggestion: SuggestionDTO): Observable<any> {
    return this.http.put(
      this.url + `/suggestion/update`,
      suggestion,
      {
        headers: this.headers
      }
    );
  }

  //delete user account with email
  deleteUserAccount(email:string):Observable<any>{
    return this.http.delete(environment.baseUrl + `/auth/delete`, {
      params: { email },
      headers:this.headers
    });
  }
}
