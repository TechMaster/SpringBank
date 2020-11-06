import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProfileComponent } from './profile.component';
import { AccountComponent } from './account/account.component';
import { TransferMoneyComponent } from './transfer-money/transfer-money.component';
import { NotificationsComponent } from './notifications/notifications.component';

const routes: Routes = [
  { path: '', component: ProfileComponent },
  { path: 'account', component: AccountComponent },
  { path: 'transfer-money', component: TransferMoneyComponent },
  { path: 'notifications', component: NotificationsComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProfileRoutingModule {}
