import {Component, OnInit} from "@angular/core";
import {TrainingModel} from "../../shared/domain/training-model";
import {UserNotificationService} from "../../shared/service/user-notification.service";
import {TrainingService} from "../../shared/service/training.service";

@Component({
  selector: 'app-training-subscription-view',
  templateUrl: './training-subscription-view.component.html'
})
export class TrainingSubscriptionViewComponent implements OnInit {

  trainings: TrainingModel[] = [];

  constructor(
    protected _trainingService: TrainingService,
    private _userNotificationService: UserNotificationService
  ) {
  }

  ngOnInit(): void {
    this._trainingService.findAll().subscribe(response => {
        this.trainings = response.body;
      },
      () => {
        this._userNotificationService.notifyUser("Edzések betöltése sikertelen!", true);
      });
  }

  onTrainingDelete(deleted: boolean) {
    if(deleted) {
      this._userNotificationService.notifyUser("Edzés sikeresen törölve!", false);
      this._trainingService.findAll().subscribe(
        response => {
          this.trainings = response.body;
        }
      )
    } else {
      this._userNotificationService.notifyUser("Edzés törlése sikertelen", true);
    }
  }



}
