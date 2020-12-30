import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, EMPTY, Observable, Subject, timer } from 'rxjs';
import {
  catchError,
  tap,
  switchAll,
  retryWhen,
  delayWhen,
  map,
  filter,
} from 'rxjs/operators';
import { Notification } from 'src/app/models/notification.model';

const NOTIFICATION_API_ENDPOINT: string =
  environment.API_ENDPOINT + '/notifications';
const WS_ENDPOINT = environment.SOCKET_ENDPOINT;

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private notifications$: BehaviorSubject<Notification[]>;
  public notifications: Observable<Notification[]>;

  constructor(private http: HttpClient) {
    this.notifications$ = new BehaviorSubject<Notification[]>([]);
    this.notifications = this.notifications$.asObservable();
    this.getUnreadNotifications().subscribe();
  }

  setupWebsocket() {
    const connection = new WebSocket(WS_ENDPOINT);

    connection.onmessage = (message) => {
      const newNotifications = this.notifications$.value;
      newNotifications.unshift({
        title: message.data,
        read: null,
      });
      this.notifications$.next(newNotifications);
    };
  }

  getUnreadNotifications(): Observable<Notification[]> {
    return this.http.get<Notification[]>(NOTIFICATION_API_ENDPOINT).pipe(
      map((notifications) =>
        notifications.filter((notification) => notification.read === null)
      ),
      tap((notifications) =>
        this.notifications$.next(notifications as Notification[])
      )
    );
  }

  getNotifications(): Observable<Notification[]> {
    return this.http.get<Notification[]>(NOTIFICATION_API_ENDPOINT);
  }

  readAllNotifications() {
    return this.http
      .get<any>(NOTIFICATION_API_ENDPOINT + '/readAll')
      .pipe(tap(() => this.notifications$.next([])));
  }
}
