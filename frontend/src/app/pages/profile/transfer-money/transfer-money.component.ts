import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-transfer-money',
  templateUrl: './transfer-money.component.html',
  styles: [':host {width: 100%; max-width: 500px; margin: 30px auto;}'],
})
export class TransferMoneyComponent implements OnInit {
  transferForm = this.fb.group({
    account: ['', Validators.required],
    owner: ['robin'],
    toAccount: ['', Validators.required],
    bankTarget: [''],
    amount: ['', Validators.pattern(/^[0-9]+$/)],
    cost: ['1'],
    note: [''],
  });

  isDone: boolean = false;

  constructor(
    private titleService: Title,
    private fb: FormBuilder,
    private accountService: AccountService,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Chuyển tiền');
  }

  transferMoney() {
    const formValues = this.transferForm.value;
    this.accountService.transferMoney(formValues).subscribe(
      () => (this.isDone = true),
      () =>
        this._snackBar.open('Giao dịch thất bại', 'Đóng', {
          duration: 2000,
          verticalPosition: 'top',
        })
    );
  }
}
