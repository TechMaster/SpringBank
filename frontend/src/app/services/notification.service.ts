import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { EMPTY, Observable, Subject, timer } from 'rxjs';
import {
  catchError,
  tap,
  switchAll,
  retryWhen,
  delayWhen,
} from 'rxjs/operators';

const NOTIFICATION_API_ENDPOINT: string =
  environment.API_ENDPOINT + '/notifications';
const WS_ENDPOINT = environment.SOCKET_ENDPOINT;

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private socket$: WebSocketSubject<any>;
  private messagesSubject$ = new Subject();
  public messages$ = this.messagesSubject$.pipe(
    switchAll(),
    catchError((e) => {
      throw e;
    })
  );

  constructor(private http: HttpClient) {}

  getNotifications(): Observable<any> {
    return this.http.get<any>(NOTIFICATION_API_ENDPOINT);
  }

  public connect(): void {
    if (!this.socket$ || this.socket$.closed) {
      this.socket$ = this.getNewWebSocket();
      const messages = this.socket$.pipe(
        tap({
          error: (error) => console.log(error),
        }),
        catchError((_) => EMPTY)
      );
      this.messagesSubject$.next(messages);
    }
  }

  private getNewWebSocket() {
    return webSocket(WS_ENDPOINT);
  }

  sendMessage(msg: any) {
    this.socket$.next(msg);
  }

  close() {
    this.socket$.complete();
  }

  private reconnect(observable: Observable<any>): Observable<any> {
    return observable.pipe(
      retryWhen((errors) =>
        errors.pipe(
          tap((val) => console.log('[Data Service] Try to reconnect', val)),
          delayWhen((_) => timer(10000))
        )
      )
    );
  }
}
