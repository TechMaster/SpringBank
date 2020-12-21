import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { BankAccount } from 'src/app/models/bank-account.model';
import { AccountService } from 'src/app/services/account.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
})
export class AccountComponent implements OnInit {
  bankAccounts: BankAccount[];

  constructor(
    private titleService: Title,
    private userService: UserService,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Danh sách tài khoản');

    const currentUserId = this.userService.currentUserValue?.id;

    this.accountService.getAccounts().subscribe((data) => {
      this.bankAccounts = data;
    });
  }
}
