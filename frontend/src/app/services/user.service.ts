import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { BankAccount } from '../models/bank-account.model';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

const USER_API_ENDPOINT: string = environment.API_ENDPOINT + '/users';
const LOCALSTORAGE_KEY = 'user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  public redirectUrl: string = '/';
  public currentUser: Observable<User>;
  private currentUserSubject: BehaviorSubject<User>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(
      JSON.parse(localStorage.getItem(LOCALSTORAGE_KEY))
    );
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  register() {}

  login(email: string, password: string): Observable<User> {
    return this.http
      .post<User>(environment.API_ENDPOINT + '/login', {
        email,
        password,
      })
      .pipe(
        map((user) => {
          localStorage.setItem(LOCALSTORAGE_KEY, JSON.stringify(user));
          this.currentUserSubject.next(user);
          return user;
        })
      );
  }

  loginWithKeycloak(email: string, password: string) {
    const body = new HttpParams()
      .set('client_id', 'xbank')
      .set('client_secret', '60bee81a-b06d-4e2f-8d26-7e0f0782bc7d')
      .set('username', email)
      .set('password', password)
      .set('grant_type', 'password');

    return this.http.post(environment.KEYCLOAK_ENDPOIT, body.toString(), {
      headers: new HttpHeaders().set(
        'Content-Type',
        'application/x-www-form-urlencoded'
      ),
    });

    // return this.http
    //   .post<any>(environment.KEYCLOAK_ENDPOIT, {
    //     client_id: 'xbank',
    //     client_secret: '60bee81a-b06d-4e2f-8d26-7e0f0782bc7d',
    //     username: email,
    //     password: password,
    //     grant_type: 'password',
    //   })
    //   .pipe(
    //     map((user) => {
    //       localStorage.setItem(LOCALSTORAGE_KEY, JSON.stringify(user));
    //       this.currentUserSubject.next(user);
    //       return user;
    //     })
    //   );
  }

  logout(): void {
    localStorage.removeItem(LOCALSTORAGE_KEY);
    this.currentUserSubject.next(null);
  }

  getUsers(options): Observable<HttpResponse<User[]>> {
    let queryParams = [];
    queryParams.push('_page=' + (options.currentPage || 1));
    queryParams.push('_sort=' + (options.column || 'id'));
    queryParams.push('_order=' + (options.direction || 'desc'));
    queryParams.push('_limit=' + (options.itemsPerPage || 10));
    queryParams.push('q=' + (options.keyword || ''));
    const queryParamsUrl = queryParams.join('&');

    const api = USER_API_ENDPOINT + `?${queryParamsUrl}`;
    return this.http.get<User[]>(api, { observe: 'response' });
  }

  getUserById(userId: string): Observable<User> {
    return this.http.get<User>(USER_API_ENDPOINT + '/' + userId);
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(USER_API_ENDPOINT, user);
  }

  updateUser(user: User): Observable<User> {
    return this.http.put<User>(USER_API_ENDPOINT + '/' + user.id, user);
  }

  deleteUser(userId: string): Observable<any> {
    return this.http.delete<User>(USER_API_ENDPOINT + '/' + userId);
  }

  getUserBankAccounts(userId: string): Observable<BankAccount[]> {
    const api = environment.API_ENDPOINT + `/bank_accounts?userId=${userId}`;
    return this.http.get<BankAccount[]>(api);
  }
}
