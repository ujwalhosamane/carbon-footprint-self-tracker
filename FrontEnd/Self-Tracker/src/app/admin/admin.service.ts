import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable, OnInit, PLATFORM_ID } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PredefinedGoalDTO } from '../models/predefined-goal-dto.model';
import { GlobalInsight } from '../models/global-insight.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService implements OnInit {

  constructor(@Inject(PLATFORM_ID) private platformId: Object, private http : HttpClient) { }
  ngOnInit(): void {
    //console.log("this.url",this.url);
  }

  private url= environment.baseUrl+'/admin';

  private getToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem("auth");
    }
    return null;
  }
  private token=this.getToken()

  public headers=new HttpHeaders({
    'Authorization':`Bearer ${this.token}`
  })

  //PredefinedGoal
  getAllPredefinedGoal(): Observable<any> {
    return this.http.get(this.url + "/predefinedGoal/getAll", {headers:this.headers});
  }

  //PredefinedGoal
  addPredefinedGoal(data: PredefinedGoalDTO): Observable<any> {
    //return type PredefinedGoal
    return this.http.post(this.url + "/predefinedGoal/add", data, {headers:this.headers});
  }

  //PredefinedGoal
  updatePredefinedGoal(data: PredefinedGoalDTO, id: number): Observable<any> {
    //return type PredefinedGoal
    return this.http.put(this.url + `/predefinedGoal/update/${id}`, data, {headers:this.headers});
  }

  //PredefinedGoal
  deletePredefinedGoal(id: number): Observable<any> {
    return this.http.delete(this.url + `/predefinedGoal/delete/${id}`, {headers:this.headers});
  }


  //Indights
  getAllInsights(): Observable<any> {
    return this.http.get(this.url + "/globalInsight/allInsights", {headers:this.headers});
  }

  //Indights
  addInsight(data: GlobalInsight): Observable<any> {
    return this.http.post(this.url + "/globalInsight/addInsight", data, {headers:this.headers});
  }

  //Indights
  deleteInsight(id: number): Observable<any> {
    return this.http.delete(this.url + `/globalInsight/deleteInsight/${id}`, {headers:this.headers});
  }

  //This month's user's count
  getThisMonthUserCount(): Observable<any> {
    return this.http.get(this.url + "/this-month/count", {headers:this.headers});
  }

  //Last month's user's count
  getLastMonthUserCount(): Observable<any> {
    return this.http.get(this.url + "/last-month/count", {headers:this.headers});
  }

  //Get total users count
  getTotalUsersCount(): Observable<any> {
    return this.http.get(this.url + "/total/count", {headers:this.headers});
  }

  //latest user account created
  getLatestUserAccount(): Observable<any> {
    return this.http.get(this.url + "/latest-created", {headers:this.headers});
  }

  //latest user login
  getLatestUserLogin(): Observable<any> {
    return this.http.get(this.url + "/latest-logins", {headers:this.headers});
  }

  //get total rewrd points and footprint
  getTotalRewrdPointsAndFootprint(): Observable<any> {
    return this.http.get(this.url + "/totals", {headers:this.headers});
  }

  //get total footprint this and last month
  getTotalFootprintThisAndLastMonth(): Observable<any> {
    return this.http.get(this.url + "/footprint/total", {headers:this.headers});
  }

  //get top two performers by total rewaed points
  getTopTwoPerformersByTotalRewaedPoints(): Observable<any> {
    return this.http.get(this.url + "/get/topPerformer", {headers:this.headers});
  }

  //get predefined goal and its completed count by type
  getPredefinedGoalAndItsCompletedCountByType(): Observable<any> {
    return this.http.get(this.url + "/predefinedGoal/with-goal-count", {headers:this.headers});
  }

  //get recently created predefined goal
  getRecentlyCreatedPredefinedGoal(): Observable<any> {
    return this.http.get(this.url + "/predefinedGoal/recent-goals/titles-descriptions", {headers:this.headers});
  }

  //update the emission factor
  updateEmissionFactor(data: any): Observable<any> {
    return this.http.put(this.url + "/footprint/factor", data, {headers:this.headers});
  }

  //get the emission factor
  getEmissionFactor(): Observable<any> {
    return this.http.get(this.url + "/footprint/factor", {headers:this.headers});
  }

  //get total completed predefined goal
  getTotalCompletedPredefinedGoal(): Observable<any> {
    return this.http.get(this.url + "/predefinedGoal/total-count", {headers:this.headers});
  }

  //get current logged in user count
  getCurrentLoggedInUserCount(): Observable<any> {
    return this.http.get(this.url + "/current-login-count", {headers:this.headers});
  }

  //get data retention period in months
  getDataRetentionPeriod(): Observable<any> {
    return this.http.get(this.url + "/get/for-data-retention", {headers:this.headers});
  }

  //update data retention period
  updateDataRetentionPeriod(data: number): Observable<any> {
    return this.http.put(this.url + `/update/for-data-retention/${data}`, {}, {headers:this.headers});
  }

  //get the retention date
  getRetentionDate(retentionDurationMonths: number): Observable<any> {
    return this.http.get(this.url + `/footprint/get/retention-date/${retentionDurationMonths}`, {headers:this.headers});
  }

  // log out
  logOut():Observable<any>{
    return this.http.post(environment.baseUrl + "/auth/logout", {}, {headers:this.headers});
  }

  //get admin details after login
  getAdminDetailsAfterLogin(): Observable<any> {
    return this.http.get(this.url + "/get/admin-details", {headers:this.headers});
  }

  //update password
  updatePassword(passwordData:any):Observable<any>{
    return this.http.put(environment.baseUrl + "/auth/updatePassword", passwordData, {headers:this.headers});
  }
}
