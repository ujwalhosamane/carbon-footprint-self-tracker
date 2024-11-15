import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserMainLayoutComponent } from './user/user-main-layout/user-main-layout.component';
import { AuthLayoutComponent } from './auth/auth-layout/auth-layout.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { LeaderBoardComponent } from './user/leader-board/leader-board.component';
import { AdminMainLayoutComponent } from './admin/admin-main-layout/admin-main-layout.component';
import { PredefinedGoalComponent } from './admin/predefined-goal/predefined-goal.component';
import { AdminDashboardComponent } from './admin/admin-dashboard/admin-dashboard.component';
import { AdminHomeComponent } from './admin/admin-home/admin-home.component';
import { AnalyticsComponent } from './admin/analytics/analytics.component';
import { AdminUsersComponent } from './admin/admin-users/admin-users.component';
import { AdminOverviewComponent } from './admin/admin-overview/admin-overview.component';
import { UserHomeComponent } from './user/user-home/user-home.component';
import { AdminInsightsComponent } from './admin/admin-insights/admin-insights.component';
import { AuthHomeComponent } from './auth/auth-home/auth-home.component';
import { UserOverviewComponent } from './user/user-overview/user-overview.component';
import { UserGoalComponent } from './user/user-goal/user-goal.component';
const routes: Routes = [
  {
    path: 'user',
    component: UserMainLayoutComponent, 
    children: [
      { path: 'leaderboard', component: LeaderBoardComponent },
      { path: 'overview', component: UserOverviewComponent },
      { path: 'goal', component: UserGoalComponent },
      { path: 'home', component: UserHomeComponent },
    ],
  },
  {
    path: 'auth',
    component: AuthLayoutComponent,
    children: [
      { path: 'home', component: AuthHomeComponent },
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent},
      { path: '', redirectTo: 'home', pathMatch: 'full' }
    ],
  },
  {
    path: 'admin',
    component: AdminMainLayoutComponent,
    children: [
      { 
        path: 'dashboard', 
        component: AdminDashboardComponent,
        children: [
          { path: 'goal', component: PredefinedGoalComponent },
          { path: 'analytics', component: AnalyticsComponent },
          { path: 'users', component: AdminUsersComponent },
          { path: 'overview', component: AdminOverviewComponent },
          { path: 'insights', component: AdminInsightsComponent },  
        ]
      },
      { path: 'home', component: AdminHomeComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
