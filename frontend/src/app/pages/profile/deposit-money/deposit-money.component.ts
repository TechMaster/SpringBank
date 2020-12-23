import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';
import { BankAccount } from 'src/app/models/bank-account.model';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-deposit-money',
  templateUrl: './deposit-money.component.html',
  styles: [':host {width: 100%; max-width: 500px; margin: 30px auto;}'],
})
export class DepositMoneyComponent implements OnInit {
  depositForm = this.fb.group({
    account: ['', Validators.pattern(/^[0-9]+$/)],
    balance: ['', Validators.pattern(/^[0-9]+$/)],
  });

  accounts: BankAccount[] = [];

  isDone: boolean = false;

  constructor(
    private titleService: Title,
    private fb: FormBuilder,
    private accountService: AccountService,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Nạp tiền');

    this.accountService.getAccounts().subscribe(console.log)
  }

  depositMoney() {}
}
