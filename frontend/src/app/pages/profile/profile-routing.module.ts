import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProfileComponent } from './profile.component';
import { InfoComponent } from './info/info.component';
import { AccountsComponent } from './accounts/accounts.component';
import { CreateAccountComponent } from './create-account/create-account.component';
import { TransferMoneyComponent } from './transfer-money/transfer-money.component';
import { DepositMoneyComponent } from './deposit-money/deposit-money.component';
import { WithdrawMoneyComponent } from './withdraw-money/withdraw-money.component';
import { TransactionComponent } from './transaction/transaction.component';
import { NotificationsComponent } from './notifications/notifications.component';

const routes: Routes = [
  { path: '', component: ProfileComponent },
  { path: 'info', component: InfoComponent },
  { path: 'accounts', component: AccountsComponent },
  { path: 'create-account', component: CreateAccountComponent },
  { path: 'transfer-money', component: TransferMoneyComponent },
  { path: 'deposit-money', component: DepositMoneyComponent },
  { path: 'withdraw-money', component: WithdrawMoneyComponent },
  { path: 'transaction/:account/:balance', component: TransactionComponent },
  { path: 'notifications', component: NotificationsComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProfileRoutingModule {}
