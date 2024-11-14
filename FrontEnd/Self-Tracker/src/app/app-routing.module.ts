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
import { HomeComponent } from './user/home/home.component';
import { UserHomeComponent } from './user/user-home/user-home.component';

const routes: Routes = [
  {
    path: 'user',
    component: UserMainLayoutComponent, 
    children: [
      { path: 'leaderBoard', component: LeaderBoardComponent },
      { path: 'home', component: UserHomeComponent },
    ],
  },
  {
    path: 'auth',
    component: AuthLayoutComponent,
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent},
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
