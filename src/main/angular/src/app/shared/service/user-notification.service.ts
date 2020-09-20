import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';

@Injectable({providedIn: 'root'})
export class UserNotificationService {
  private messageSubject = new Subject<any>();

  constructor() {
  }

  notifyUser(message: string, isError: boolean) {
    this.messageSubject.next(
      {
        text: message,
        error: isError
      });
    setTimeout(() => {
      this.clearNotification();
    }, 3000);
  }

  clearNotification() {
    this.messageSubject.next();
  }

  getMessage(): Observable<any> {
    return this.messageSubject.asObservable();
  }
}
