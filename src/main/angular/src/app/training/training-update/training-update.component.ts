import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {TrainingModel} from "../../shared/domain/training-model";
import {UserNotificationService} from "../../shared/service/user-notification.service";

@Component({
  selector: 'app-training-update',
  templateUrl: './training-update.component.html'
})
export class TrainingUpdateComponent implements OnInit {

  training: TrainingModel;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private _userNotificationService: UserNotificationService
  ) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({training}) => {
        this.training = training;
      },
      () => {
        // TODO notify user about error (notificationservice), then route back to tools
      });
  }


}
