import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';

import { LayoutComponent } from './components/layout/layout.component';
import { AboutComponent } from './pages/about/about.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { UsersComponent } from './pages/users/users.component';
import { RegisterComponent } from './pages/register/register.component';
import { CreateUserComponent } from './pages/users/create-user/create-user.component';
import { EditUserComponent } from './pages/users/edit-user/edit-user.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '', component: HomeComponent },
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'about', component: AboutComponent },
      {
        path: 'users',
        component: UsersComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'users/create',
        component: CreateUserComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'users/edit/:id',
        component: EditUserComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'profile',
        loadChildren: () =>
          import('./pages/profile/profile.module').then((m) => m.ProfileModule),
        canActivate: [AuthGuard],
      },
      { path: '**', component: NotFoundComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
