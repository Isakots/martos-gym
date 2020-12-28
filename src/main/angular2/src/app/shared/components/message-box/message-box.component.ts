import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { UserNotificationService } from '../../../core/services/user-notification.service';

@Component({
    selector: 'app-message-box',
    templateUrl: './message-box.component.html',
    styleUrls: ['./message-box.component.scss']
})
export class MessageBoxComponent implements OnInit, OnDestroy {
    notificationText: any = undefined;
    subscription!: Subscription;

    constructor(private readonly userNotificationService: UserNotificationService) {
    }

    ngOnInit(): void {
        this.subscription = this.userNotificationService.getMessage().subscribe(message => {
            if (message) {
                this.notificationText = message;
            } else {
                this.notificationText = undefined;
            }
        });
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

}
