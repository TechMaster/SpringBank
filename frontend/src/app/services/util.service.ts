import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UtilService {
  public alertMessage: string = '';

  setAlertMessage(message: string) {
    this.alertMessage = message;
  }
}
