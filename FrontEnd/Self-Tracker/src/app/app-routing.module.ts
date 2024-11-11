import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserMainLayoutComponent } from './user/user-main-layout/user-main-layout.component';

const routes: Routes = [
  {
    path: 'user',
    component: UserMainLayoutComponent, 
    children: [
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
