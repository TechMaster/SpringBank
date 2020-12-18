import { NgModule } from '@angular/core';
import { ShareModule } from '../../share/share.module';
import { UsersRoutingModule } from './users-routing.module';
import { UsersComponent } from './users.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { EditUserComponent } from './edit-user/edit-user.component';

@NgModule({
  declarations: [UsersComponent, CreateUserComponent, EditUserComponent],
  imports: [ShareModule, UsersRoutingModule],
})
export class UsersModule {}
