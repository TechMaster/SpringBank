import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { TransactionService } from 'src/app/services/transaction.service';

@Component({
  selector: 'app-transfer-money',
  templateUrl: './transfer-money.component.html',
  styles: [':host {width: 100%; max-width: 500px; margin: 30px auto;}'],
})
export class TransferMoneyComponent implements OnInit {
  transferForm = this.fb.group({
    account: [''],
    owner: ['robin'],
    toAccount: [''],
    bankTarget: [''],
    amount: [''],
    cost: ['1'],
    note: [''],
  });

  isDone: boolean = false;

  constructor(
    private titleService: Title,
    private fb: FormBuilder,
    private transactionService: TransactionService,
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Chuyển tiền');
  }

  transferMoney() {
    const formValues = this.transferForm.value;
    this.transactionService.createTransaction(formValues).subscribe(
      () => (this.isDone = true),
      () => alert('Giao dịch thất bại!')
    );
  }
}
