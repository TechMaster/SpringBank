import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { BankAccount } from '../models/bank-account.model';
import { Observable } from 'rxjs';

const ACCOUNT_API_ENDPOINT: string = environment.API_ENDPOINT + '/accounts';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  constructor(private http: HttpClient) {}

  getAccounts(): Observable<BankAccount[]> {
    return this.http.get<BankAccount[]>(ACCOUNT_API_ENDPOINT + '/user');
  }

  getAccountById(id: string) {
    return this.http.get<BankAccount>(ACCOUNT_API_ENDPOINT + '/' + id);
  }

  createBankAccount(bankAccount: BankAccount) {
    return this.http.post<BankAccount>(ACCOUNT_API_ENDPOINT, {
      ...bankAccount,
      action: 1,
    });
  }

  transferMoney(data) {
    return this.http.post(ACCOUNT_API_ENDPOINT + '/transfer', data);
  }

  depositMoney(data) {
    return this.http.post(ACCOUNT_API_ENDPOINT + '/deposit', data);
  }

  withdrawMoney(data) {
    return this.http.post(ACCOUNT_API_ENDPOINT + '/withdraw', data);
  }

  getTransaction(bankAccount: string) {
    return this.http.get(
      `${environment.API_ENDPOINT}/transactions/user/${bankAccount}`
    );
  }
}
