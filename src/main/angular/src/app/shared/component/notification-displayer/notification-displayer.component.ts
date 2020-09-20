import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserNotificationService} from '../../service/user-notification.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-notification-displayer',
  templateUrl: './notification-displayer.component.html'
})
export class NotificationDisplayerComponent implements OnInit, OnDestroy {
  notificationText: any = {};
  subscription: Subscription;

  constructor(private userNotificationService: UserNotificationService) {
  }

  ngOnInit(): void {
    // subscribe to home component messages
    this.subscription = this.userNotificationService.getMessage().subscribe(message => {
      if (message) {
        this.notificationText = message;
      } else {
        // clear messages when empty message received
        this.notificationText = '';
      }
    });
  }

  ngOnDestroy(): void {
    // unsubscribe to ensure no memory leaks
    this.subscription.unsubscribe();
  }

}
