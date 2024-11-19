import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; 
import { UserMainLayoutComponent } from './user-main-layout/user-main-layout.component';
import { LucideAngularModule, File, Home, Menu, UserCheck,LogOut } from 'lucide-angular';
import { RouterModule } from '@angular/router';
import { LeaderBoardComponent } from './leader-board/leader-board.component';
import { UserHomeComponent } from './user-home/user-home.component';
import { UserGoalComponent } from './user-goal/user-goal.component';
import { UserOverviewComponent } from './user-overview/user-overview.component';
import { UserFootprintComponent } from './user-footprint/user-footprint.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    UserMainLayoutComponent, 
    LeaderBoardComponent, 
    UserHomeComponent, UserGoalComponent, UserOverviewComponent, UserFootprintComponent, UserProfileComponent
  ],
  
  imports: [
    CommonModule, 
    BrowserAnimationsModule,
    RouterModule, 
    LucideAngularModule.pick({File, Home, Menu, UserCheck,LogOut }),
    ReactiveFormsModule,
    FormsModule
  ], 
})
export class UserModule {}
