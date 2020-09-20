import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TrainingModel} from '../../shared/domain/training-model';
import {UserNotificationService} from '../../shared/service/user-notification.service';
import {NgbCalendar, NgbDateStruct, NgbTimeStruct} from '@ng-bootstrap/ng-bootstrap';
import {AbstractControl, FormControl, FormGroup, Validators} from '@angular/forms';
import {TrainingService} from '../../shared/service/training.service';
import * as moment from 'moment';

@Component({
  selector: 'app-training-update',
  templateUrl: './training-update.component.html',
  styleUrls: ['./training-update.component.scss']
})
export class TrainingUpdateComponent implements OnInit {
  readonly minuteStep = 15;
  triedToSave: boolean;

  startTime: NgbTimeStruct = {hour: 18, minute: 0, second: 0};
  endTime: NgbTimeStruct = {hour: 19, minute: 30, second: 0};
  date: NgbDateStruct;

  training: TrainingModel;
  title: string;

  trainingForm: FormGroup;

  showDateValidationErrorMessage = false;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private calendar: NgbCalendar,
    private userNotificationService: UserNotificationService,
    private trainingService: TrainingService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({training}) => {
        this.training = training;
      },
      () => {
        this.userNotificationService.notifyUser('Hiba történt!', true);
      });

    this._initTitle();
    this._initTraining();
    this._initFormGroupAndControls();
    this._initDateStruct();
    this.triedToSave = false;
  }

  private _initTitle() {
    if (Object.keys(this.training).length === 0) {
      this.title = 'Új edzés kiírása';
    } else {
      this.title = 'Edzés módosítása';
    }
  }

  private _initTraining() {
    if (Object.keys(this.training).length === 0) {
      this.training = {
        id: null,
        name: '',
        maxParticipants: null,
        actualParticipants: null,
        description: '',
        startDate: null,
        endDate: null,
        subscribed: null
      };
    }
  }

  private _initFormGroupAndControls() {
    this.trainingForm = new FormGroup({
      name: new FormControl(this.training.name, Validators.required),
      maxParticipants: new FormControl(this.training.maxParticipants, Validators.required),
      description: new FormControl(this.training.description)
    });
    if (this.training.id !== null) {
      const startDate = new Date(this.training.startDate);
      this.date = {year: startDate.getFullYear(), month: startDate.getMonth() + 1, day: startDate.getDate()};
      this.startTime = {hour: startDate.getHours(), minute: startDate.getMinutes(), second: 0};
      const endDate = new Date(this.training.endDate);
      this.endTime = {hour: endDate.getHours(), minute: endDate.getMinutes(), second: 0};
    }
  }

  private _initDateStruct() {
    const currentDate = new Date();
    this.date = {year: currentDate.getFullYear(), month: currentDate.getMonth() + 1, day: currentDate.getDate()};
  }

  showValidationMessage(formControl: AbstractControl) {
    return (formControl.invalid && (formControl.dirty || formControl.touched)) || (formControl.invalid && this.triedToSave);
  }

  save() {
    this.triedToSave = true;
    this._dateValidation();
    this.trainingForm.updateValueAndValidity();
    if (this.trainingForm.invalid || this.showDateValidationErrorMessage) {
      return;
    }

    const offset = new Date().getTimezoneOffset() / (-60);

    const training = {
      id: this.training.id,
      name: this.trainingForm.controls.name.value,
      maxParticipants: this.trainingForm.controls.maxParticipants.value,
      description: this.trainingForm.controls.description.value,
      startDate: moment.utc(new Date(
        this.date.year, this.date.month - 1, this.date.day,
        this.startTime.hour, this.startTime.minute, this.startTime.second
      ).toISOString()).utcOffset(offset).local(true).format('YYYY-MM-DDTHH:mm:ss'),
      endDate: moment.utc(new Date(
        this.date.year, this.date.month - 1, this.date.day,
        this.endTime.hour, this.endTime.minute, this.endTime.second
      ).toISOString()).utcOffset(offset).local(true).format('YYYY-MM-DDTHH:mm:ss'),
      actualParticipants: null,
      subscribed: null
    };

    if (this.training.id !== null) {
      this._updateTraining(training);
    } else {
      this._createTraining(training);
    }
  }

  private _dateValidation() {
    const yesterday = new Date();
    yesterday.setDate(new Date().getDate() - 1);
    if (new Date(this.date.year, this.date.month - 1, this.date.day).getTime() < yesterday.getTime()) {
      this.showDateValidationErrorMessage = true;
    } else if (this.endTime.hour < this.startTime.hour ||
      (this.endTime.hour == this.startTime.hour && this.endTime.minute <= this.startTime.minute)) {
      this.showDateValidationErrorMessage = true;
    } else {
      this.showDateValidationErrorMessage = false;
    }
  }

  private _createTraining(training: TrainingModel) {
    this.trainingService.create(training).subscribe((response) => {
        this.router.navigate(['/trainings']);
      },
      (error) => {
        if (error.status === 406) {
          this.userNotificationService.notifyUser(
            'Validációs hiba! Edzés adatai nem megfelelőek. Kérlek ellenőrizd, hogy mindent megfelelően töltöttél-e ki!', true
          );
        } else {
          this.userNotificationService.notifyUser('Hiba történt!', true);
        }
      });
  }

  private _updateTraining(training: TrainingModel) {
    this.trainingService.update(training).subscribe((response) => {
        this.router.navigate(['/trainings']);
      },
      (error) => {
        if (error.status === 406) {
          this.userNotificationService.notifyUser(
            'Validációs hiba! Edzés adatai nem megfelelőek. Kérlek ellenőrizd, hogy mindent megfelelően töltöttél-e ki!', true
          );
        } else {
          this.userNotificationService.notifyUser('Hiba történt!', true);
        }
      });
  }

}
