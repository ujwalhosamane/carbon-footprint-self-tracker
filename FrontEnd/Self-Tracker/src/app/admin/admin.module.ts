import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminMainLayoutComponent } from './admin-main-layout/admin-main-layout.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { LucideAngularModule, File, Home, Menu, UserCheck,LogOut } from 'lucide-angular';
import { PredefinedGoalComponent } from './predefined-goal/predefined-goal.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AdminMainLayoutComponent,
    PredefinedGoalComponent
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
