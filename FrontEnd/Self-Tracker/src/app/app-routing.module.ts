import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserMainLayoutComponent } from './user/user-main-layout/user-main-layout.component';
import { AuthLayoutComponent } from './auth/auth-layout/auth-layout.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { LeaderBoardComponent } from './user/leader-board/leader-board.component';
import { AdminMainLayoutComponent } from './admin/admin-main-layout/admin-main-layout.component';
import { PredefinedGoalComponent } from './admin/predefined-goal/predefined-goal.component';

const routes: Routes = [
  {
    path: 'user',
    component: UserMainLayoutComponent, 
    children: [
      { path: 'leaderBoard', component: LeaderBoardComponent },
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
      { path: 'predefinedGoal', component: PredefinedGoalComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
