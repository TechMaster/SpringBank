import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { BankAccount } from 'src/app/models/bank-account.model';
import { AccountService } from 'src/app/services/account.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styles: [':host {width: 100%; max-width: 500px; margin: 30px auto;}'],
})
export class CreateAccountComponent implements OnInit {
  createAccountForm = this.fb.group({
    account: ['', [Validators.required, Validators.pattern(/^[0-9]{9}$/)]],
    balance: ['', [Validators.required, Validators.pattern(/^[0-9]+$/)]],
  });

  constructor(
    private titleService: Title,
    private fb: FormBuilder,
    private accountService: AccountService,
    private router: Router,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Tạo tài khoản');
  }

  createAccount() {
    const formValues = this.createAccountForm.value;
    this.accountService.createBankAccount(formValues).subscribe(
      () => this.router.navigate(['/profile/accounts']),
      () =>
        this._snackBar.open('Tạo tài khoản thất bại', 'Đóng', {
          duration: 2000,
          verticalPosition: 'top',
        })
    );
  }
}
