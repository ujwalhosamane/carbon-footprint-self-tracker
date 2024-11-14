import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminMainLayoutComponent } from './admin-main-layout/admin-main-layout.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { LucideAngularModule, File, Home, Menu, UserCheck,LogOut } from 'lucide-angular';
import { PredefinedGoalComponent } from './predefined-goal/predefined-goal.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AnalyticsComponent } from './analytics/analytics.component';
import { AdminOverviewComponent } from './admin-overview/admin-overview.component';
import { AdminUsersComponent } from './admin-users/admin-users.component';


@NgModule({
  declarations: [
    AdminMainLayoutComponent,
    PredefinedGoalComponent,
    AdminDashboardComponent,
    AdminHomeComponent,
    AnalyticsComponent,
    AdminOverviewComponent,
    AdminUsersComponent
  ],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    RouterModule, 
    LucideAngularModule.pick({File, Home, Menu, UserCheck,LogOut }),
    ReactiveFormsModule,
  ]
})
export class AdminModule { }
