import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css'],
})
export class TransactionComponent implements OnInit {
  account: string;
  balance: string;
  transactions;

  constructor(
    private titleService: Title,
    private accountService: AccountService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Thông tin cá nhân');

    this.account = this.route.snapshot.paramMap.get('account');
    this.balance = this.route.snapshot.paramMap.get('balance');

    this.accountService.getTransaction(this.account).subscribe((data) => {
      this.transactions = data;
    });
  }
}
