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
      return localStorage.getItem("__auth");
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
    return this.http.delete(this.url + `/delete/${id}`, {headers:this.headers});
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

}
