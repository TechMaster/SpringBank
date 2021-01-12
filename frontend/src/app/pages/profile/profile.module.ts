import { NgModule } from '@angular/core';
import { ShareModule } from 'src/app/share/share.module';
import { ProfileRoutingModule } from './profile-routing.module';
import { ProfileComponent } from './profile.component';
import { AccountsComponent } from './accounts/accounts.component';
import { CreateAccountComponent } from './create-account/create-account.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { TransferMoneyComponent } from './transfer-money/transfer-money.component';
import { DepositMoneyComponent } from './deposit-money/deposit-money.component';
import { WithdrawMoneyComponent } from './withdraw-money/withdraw-money.component';
import { InfoComponent } from './info/info.component';
import { TransactionComponent } from './transaction/transaction.component';

@NgModule({
  declarations: [
    ProfileComponent,
    AccountsComponent,
    CreateAccountComponent,
    NotificationsComponent,
    TransferMoneyComponent,
    DepositMoneyComponent,
    WithdrawMoneyComponent,
    InfoComponent,
    TransactionComponent,
  ],
  imports: [ShareModule, ProfileRoutingModule],
})
export class ProfileModule {}
