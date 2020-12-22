import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { KeycloakService } from 'keycloak-angular';

import { User } from '../models/user.model';
import { KeyCloakTableOptions } from '../models/table-options.model';

import { environment } from 'src/environments/environment';

const KEYCLOAK = environment.keycloak;
const USER_API_ENDPOINT: string = `${KEYCLOAK.url}/admin/realms/${KEYCLOAK.realm}/users`;
const KEYCLOAK_REALMS = 'microservice';
const LOCALSTORAGE_KEY = 'user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  public redirectUrl: string = '/';
  public currentUser: Observable<User>;
  private currentUserSubject: BehaviorSubject<User>;
  public isLoggedIn = false;

  constructor(private http: HttpClient, private keycloak: KeycloakService) {
    this.currentUserSubject = new BehaviorSubject<User>(
      JSON.parse(localStorage.getItem(LOCALSTORAGE_KEY))
    );
    this.currentUser = this.currentUserSubject.asObservable();

    this.checkLogin();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  async checkLogin() {
    this.isLoggedIn = await this.keycloak.isLoggedIn();

    if (this.isLoggedIn) {
      const userProfile = await this.keycloak.loadUserProfile();
      const roles = this.keycloak.getUserRoles();

      this.currentUserSubject.next({ ...userProfile, roles } as User);
    }
  }

  login() {
    this.keycloak.login();
  }

  loginWithKeycloakREST(username: string, password: string) {
    const body = new HttpParams()
      .set('client_id', environment.keycloak.clientId)
      .set('client_secret', environment.keycloak.clientSecret)
      .set('username', username)
      .set('password', password)
      .set('grant_type', 'password');

    return this.http.post(
      environment.keycloak.url +
        `/realms/${KEYCLOAK_REALMS}/protocol/openid-connect/token`,
      body.toString(),
      {
        headers: new HttpHeaders().set(
          'Content-Type',
          'application/x-www-form-urlencoded'
        ),
      }
    );
  }

  logout(): void {
    this.currentUserSubject.next(null);
    this.keycloak.logout();
  }

  countUsers(): Observable<number> {
    return this.http.get<number>(USER_API_ENDPOINT + '/count');
  }

  getUsers(options: KeyCloakTableOptions): Observable<HttpResponse<User[]>> {
    let queryParams = [];
    queryParams.push('first=' + (options.first || 0));
    queryParams.push('max=' + (options.max || 100));
    queryParams.push('search=' + (options.search || ''));
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
}
