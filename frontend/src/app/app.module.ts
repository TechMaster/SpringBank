import { BrowserModule, Title } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { ShareModule } from './share/share.module';

import { AppComponent } from './app.component';
import { LayoutComponent } from './components/layout/layout.component';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { AboutComponent } from './pages/about/about.component';
import { UsersComponent } from './pages/users/users.component';
import { CreateUserComponent } from './pages/users/create-user/create-user.component';
import { EditUserComponent } from './pages/users/edit-user/edit-user.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { UserFormComponent } from './components/user-form/user-form.component';
import { PasswordFormComponent } from './components/password-form/password-form.component';

import { httpInterceptorProviders } from './http-interceptors';
import { NotificationsBoxComponent } from './components/notifications-box/notifications-box.component';

@NgModule({
  declarations: [
    AppComponent,
    LayoutComponent,
    LoginComponent,
    HomeComponent,
    AboutComponent,
    UsersComponent,
    CreateUserComponent,
    EditUserComponent,
    NotFoundComponent,
    UserFormComponent,
    PasswordFormComponent,
    NotificationsBoxComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ShareModule,
  ],
  providers: [Title, httpInterceptorProviders],
  bootstrap: [AppComponent],
})
export class AppModule {}
