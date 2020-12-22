import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({providedIn: 'root'})
export class UserNotificationService {
    private readonly messageSubject = new Subject<any>();

    notifyUser(message: string, isError: boolean): void {
        this.messageSubject.next(
            {
                text: message,
                error: isError
            });
        setTimeout(() => {
            this.clearNotification();
        }, 3000);
    }

    clearNotification(): void {
        this.messageSubject.next();
    }

    getMessage(): Observable<any> {
        return this.messageSubject.asObservable();
    }
}
