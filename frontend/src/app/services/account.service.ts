import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Transaction } from '../models/transaction.model';
import { environment } from 'src/environments/environment';
import { BankAccount } from '../models/bank-account.model';
import { Observable } from 'rxjs';

const TRANSACTION_API_ENDPOINT: string = environment.API_ENDPOINT + '/accounts';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  constructor(private http: HttpClient) {}

  getAccounts(): Observable<BankAccount[]> {
    return this.http.get<BankAccount[]>(TRANSACTION_API_ENDPOINT);
  }

  createTransaction(transaction: Transaction) {
    return this.http.post<Transaction>(TRANSACTION_API_ENDPOINT, transaction);
  }
}
