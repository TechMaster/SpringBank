import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { BankAccount } from 'src/app/models/bank-account.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
})
export class AccountComponent implements OnInit {
  bankAccounts: BankAccount[];

  constructor(private titleService: Title, private userService: UserService) {}

  ngOnInit(): void {
    this.titleService.setTitle('Danh sách tài khoản');

    const currentUserId = this.userService.currentUserValue?.id;

    this.userService.getUserBankAccounts(currentUserId).subscribe((data) => {
      console.log(data)
      this.bankAccounts = data;
    });
  }
}
