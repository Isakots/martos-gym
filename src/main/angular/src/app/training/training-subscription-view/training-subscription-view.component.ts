import {Component, OnInit} from '@angular/core';
import {TrainingModel} from '../../shared/domain/training-model';
import {UserNotificationService} from '../../shared/service/user-notification.service';
import {TrainingService} from '../../shared/service/training.service';

@Component({
  selector: 'app-training-subscription-view',
  templateUrl: './training-subscription-view.component.html'
})
export class TrainingSubscriptionViewComponent implements OnInit {

  trainings: TrainingModel[] = [];

  constructor(
    protected trainingService: TrainingService,
    private userNotificationService: UserNotificationService
  ) {
  }

  ngOnInit(): void {
    this.trainingService.findAll().subscribe(response => {
      this.trainings = response.body;
    });
  }

  onTrainingDelete(deleted: boolean) {
    if (deleted) {
      this.userNotificationService.notifyUser('Edzés sikeresen törölve!', false);
      this.trainingService.findAll().subscribe(
        response => {
          this.trainings = response.body;
        }
      );
    } else {
      this.userNotificationService.notifyUser('Edzés törlése sikertelen', true);
    }
  }

}
