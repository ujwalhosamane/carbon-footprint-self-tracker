import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LucideAngularModule, File, Home, Menu, UserCheck,LogOut } from 'lucide-angular';
import { UserModule } from './user/user.module';
import { AuthModule } from './auth/auth.module';
import { AdminModule } from './admin/admin.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AuthModule,
    AdminModule,
    UserModule,
    RouterModule,
    LucideAngularModule.pick({File, Home, Menu, UserCheck,LogOut }) 
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
