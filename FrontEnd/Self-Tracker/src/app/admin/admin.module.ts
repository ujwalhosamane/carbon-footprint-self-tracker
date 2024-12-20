import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
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
import { AdminInsightsComponent } from './admin-insights/admin-insights.component';
import { AdminSettingComponent } from './admin-setting/admin-setting.component';
import { AdminProfileComponent } from './admin-profile/admin-profile.component';


@NgModule({
  declarations: [
    AdminMainLayoutComponent,
    PredefinedGoalComponent,
    AdminDashboardComponent,
    AdminHomeComponent,
    AnalyticsComponent,
    AdminOverviewComponent,
    AdminInsightsComponent,
    AdminSettingComponent,
    AdminProfileComponent
  ],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    RouterModule, 
    LucideAngularModule.pick({File, Home, Menu, UserCheck,LogOut }),
    ReactiveFormsModule,
    FormsModule,
  ]
})
export class AdminModule { }
