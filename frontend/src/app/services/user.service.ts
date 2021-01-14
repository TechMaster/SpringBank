import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, forkJoin, Observable } from 'rxjs';
import { KeycloakService } from 'keycloak-angular';

import { User, Role } from '../models/user.model';
import { KeyCloakTableOptions } from '../models/table-options.model';
import { environment } from 'src/environments/environment';
import { map, mergeMap } from 'rxjs/operators';

const KEYCLOAK = environment.keycloak;
const KEYCLOAK_ADMIN_ENDPOINT = `${KEYCLOAK.url}/admin/realms/${KEYCLOAK.realm}`;
const USER_API_ENDPOINT: string = `${KEYCLOAK_ADMIN_ENDPOINT}/users`;
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

  getUserById(userId: string): Observable<any> {
    return forkJoin([
      // Get User info
      this.http.get<User>(`${USER_API_ENDPOINT}/${userId}`),

      // Get User role
      this.http.get(
        `${USER_API_ENDPOINT}/${userId}/role-mappings/clients/${KEYCLOAK.clientUUID}`
      ),
    ]).pipe(map((result) => ({ ...result[0], roles: result[1] })));
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(USER_API_ENDPOINT, user);
  }

  updateUser(user: User): Observable<User> {
    return this.http.put<User>(`${USER_API_ENDPOINT}/${user.id}`, user);
  }

  deleteUser(userId: string): Observable<any> {
    return this.http.delete<User>(`${USER_API_ENDPOINT}/${userId}`);
  }

  getRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(
      `${KEYCLOAK_ADMIN_ENDPOINT}/clients/${KEYCLOAK.clientUUID}/roles`
    );
  }

  setRole(userId: string, allRoles: Role[], roles: Role[]) {
    const roleMappingsAPI = `${USER_API_ENDPOINT}/${userId}/role-mappings/clients/${KEYCLOAK.clientUUID}`;

    return (
      this.http
        // Remove all roles of user
        .request('delete', roleMappingsAPI, { body: allRoles })
        // Add new roles
        .pipe(mergeMap(() => this.http.post(roleMappingsAPI, roles)))
    );
  }
}
