import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {TrainingModel} from "../../shared/domain/training-model";
import {UserNotificationService} from "../../shared/service/user-notification.service";

@Component({
  selector: 'app-training-subscription-view',
  templateUrl: './training-subscription-view.component.html'
})
export class TrainingSubscriptionViewComponent implements OnInit {

  trainings: TrainingModel[];

  constructor(
    protected activatedRoute: ActivatedRoute,
    private _userNotificationService: UserNotificationService
  ) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({trainings}) => {
        this.trainings = trainings;
      },
      () => {
        // TODO notify user about error (notificationservice), then route back to tools
      });
  }


}
