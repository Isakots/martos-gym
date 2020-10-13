import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {TrainingModel} from '../../shared/domain/training-model';
import {TrainingService} from '../../shared/service/training.service';
import {UserNotificationService} from '../../shared/service/user-notification.service';
import {GeneralConfirmationModalComponent} from '../../shared/component/general-confirmation-modal/general-confirmation-modal.component';

@Component({
  selector: 'app-training-item',
  templateUrl: './training-item.component.html',
  styleUrls: ['./training-item.component.scss']
})
export class TrainingItemComponent implements OnInit {
  @ViewChild(GeneralConfirmationModalComponent) confirmDeletionModal: GeneralConfirmationModalComponent;

  detailsViewOpenState = false;

  @Input()
  training: TrainingModel;

  @Output()
  public trainingDeleted: EventEmitter<boolean> = new EventEmitter();

  @Output()
  public onSubscription: EventEmitter<void> = new EventEmitter();

  constructor(
    private trainingService: TrainingService,
    private userNotificationService: UserNotificationService) {
  }

  ngOnInit(): void {

  }

  toggleDetailsView() {
    this.detailsViewOpenState = !this.detailsViewOpenState;
  }

  subscribe(trainingId: string, subscription: boolean) {
    if (this.isSubscribeDisabled()) {
      this.userNotificationService.notifyUser('Az edzés már sajnos betelt!', true);
      return;
    }
    if (subscription) {
      this.trainingService.subscribe(trainingId).subscribe(() => {
          this.userNotificationService.notifyUser('Sikeresen feliratkoztál az edzésre!', false);
          this.onSubscription.emit();
        },
        () => {
          this.userNotificationService.notifyUser('Hiba történt feliratkozás közben!', true);
        });
    } else {
      this.trainingService.unsubscribe(trainingId).subscribe(() => {
          this.userNotificationService.notifyUser('Sikeresen leiratkoztál az edzésről!', false);
          this.onSubscription.emit();
        },
        () => {
          this.userNotificationService.notifyUser('Hiba történt leiratkozás közben!', true);
        });
    }

  }

  hasImage() {
    // TODO new feature: add img to training
    return false;
  }

  openConfirmationModal() {
    this.confirmDeletionModal.open();
  }

  onEventConfirmation(confirmed: boolean) {
    if (confirmed) {
      this.trainingService.delete(this.training.id).subscribe(
        () => {
          this.trainingDeleted.emit(true);
        },
        () => {
          this.trainingDeleted.emit(false);
        });
    }
  }

  getTrainingDateTime() {
    return this.training.startDate.substring(0, 16).replace(/T/g, ' ') + ' - ' + this.training.endDate.substring(11, 16);
  }

  isSubscribeDisabled(): boolean {
    return this.training.actualParticipants == this.training.maxParticipants;
  }
}
