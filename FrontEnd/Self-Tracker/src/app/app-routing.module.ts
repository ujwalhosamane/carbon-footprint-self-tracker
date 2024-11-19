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
import { AdminOverviewComponent } from './admin/admin-overview/admin-overview.component';
import { UserHomeComponent } from './user/user-home/user-home.component';
import { AdminInsightsComponent } from './admin/admin-insights/admin-insights.component';
import { AuthHomeComponent } from './auth/auth-home/auth-home.component';
import { UserOverviewComponent } from './user/user-overview/user-overview.component';
import { UserGoalComponent } from './user/user-goal/user-goal.component';
import { UserFootprintComponent } from './user/user-footprint/user-footprint.component';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { AdminSettingComponent } from './admin/admin-setting/admin-setting.component';
import { AdminGuardGuard } from './guards/admin-guard.guard';
import { UserGuardGuard } from './guards/user-guard.guard';
import { NotFoundComponent } from './not-found/not-found/not-found.component';
import { AuthGuard } from './guards/auth.guard';
import { AdminProfileComponent } from './admin/admin-profile/admin-profile.component';
const routes: Routes = [
  {
    path: 'user',
    component: UserMainLayoutComponent,
    canActivate: [UserGuardGuard],
    children: [
      { path: 'leaderboard', component: LeaderBoardComponent },
      { path: 'overview', component: UserOverviewComponent },
      { path: 'goal', component: UserGoalComponent },
      { path: 'home', component: UserHomeComponent },
      { path: 'footprint', component: UserFootprintComponent },
      { path: 'profile', component: UserProfileComponent },
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      {
        path: '**',
        component: NotFoundComponent,
      }
    ],
  },
  {
    path: 'auth',
    component: AuthLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'home', component: AuthHomeComponent },
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent},
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      {
        path: '**',
        component: NotFoundComponent,
      }
    ],
  },
  {
    path: 'admin',
    component: AdminMainLayoutComponent,
    canActivate: [AdminGuardGuard],
    children: [
      { 
        path: 'dashboard', 
        component: AdminDashboardComponent,
        children: [
          { path: 'goal', component: PredefinedGoalComponent },
          { path: 'analytics', component: AnalyticsComponent },
          { path: 'overview', component: AdminOverviewComponent },
          { path: 'insights', component: AdminInsightsComponent },
          { path: 'settings', component: AdminSettingComponent },
          { path: '', redirectTo: 'insights', pathMatch: 'full' },
          {
            path: '**',
            component: NotFoundComponent,
          }  
        ]
      },
      { path: 'home', component: AdminHomeComponent },
      { path: 'profile', component: AdminProfileComponent },
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      {
        path: '**',
        component: NotFoundComponent,
      }
    ],
  },
  {
    path: '**',
    component: NotFoundComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
