import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; // Ensure this is included
import { UserMainLayoutComponent } from './user-main-layout/user-main-layout.component';
import { LucideAngularModule, File, Home, Menu, UserCheck,LogOut } from 'lucide-angular';
import { RouterModule } from '@angular/router';
import { LeaderBoardComponent } from './leader-board/leader-board.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HomeComponent } from './home/home.component';
@NgModule({
  declarations: [
    UserMainLayoutComponent, 
    LeaderBoardComponent, 
    DashboardComponent, 
    HomeComponent
  ],
  
  imports: [
    CommonModule, 
    BrowserAnimationsModule,
    RouterModule, 
    LucideAngularModule.pick({File, Home, Menu, UserCheck,LogOut }) 
  ],  // Include both here
})
export class UserModule {}
