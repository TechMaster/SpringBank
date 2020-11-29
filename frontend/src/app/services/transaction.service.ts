import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Transaction } from '../models/transaction.model';
import { environment } from 'src/environments/environment';

const TRANSACTION_API_ENDPOINT: string = environment.API_ENDPOINT + '/transactions';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  constructor(private http: HttpClient) {}

  createTransaction(transaction: Transaction) {
    return this.http.post<Transaction>(TRANSACTION_API_ENDPOINT, transaction);
  }
}
