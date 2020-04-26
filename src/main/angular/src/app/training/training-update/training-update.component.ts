import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {TrainingModel} from "../../shared/domain/training-model";
import {UserNotificationService} from "../../shared/service/user-notification.service";
import {NgbCalendar, NgbDateStruct, NgbTimeStruct} from "@ng-bootstrap/ng-bootstrap";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {TrainingService} from "../../shared/service/training.service";
import * as moment from "moment";

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

  constructor(
    protected _activatedRoute: ActivatedRoute,
    private _calendar: NgbCalendar,
    private _userNotificationService: UserNotificationService,
    private _trainingService: TrainingService,
    private _router: Router
  ) {
  }

  ngOnInit(): void {
    this._activatedRoute.data.subscribe(({training}) => {
        this.training = training;
      },
      () => {
        this._userNotificationService.notifyUser("Hiba történt!", true);
      });

    this._initTitle();
    this._initTraining();
    this._initFormGroupAndControls();
    this.triedToSave = false;
  }

  private _initTitle() {
    if (Object.keys(this.training).length === 0) {
      this.title = "Új edzés kiírása";
    } else {
      this.title = "Edzés módosítása";
    }
  }

  private _initFormGroupAndControls() {
    this.trainingForm = new FormGroup({
      name: new FormControl(this.training.name, Validators.required),
      maxParticipants: new FormControl(this.training.maxParticipants, Validators.required),
      description: new FormControl(this.training.description)
    });
    if (this.training.id !== null) {
      let startDate = new Date(this.training.startDate);
      this.date = {year: startDate.getFullYear(), month: startDate.getMonth() + 1, day: startDate.getDate()};
      this.startTime = {hour: startDate.getHours(), minute: startDate.getMinutes(), second: 0};
      let endDate = new Date(this.training.endDate);
      this.endTime = {hour: endDate.getHours(), minute: endDate.getMinutes(), second: 0};
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
      }
    }
  }

  showValidationMessage(formControl: AbstractControl) {
    return (formControl.invalid && (formControl.dirty || formControl.touched)) || (formControl.invalid && this.triedToSave);
  }

  save() {
    this.triedToSave = true;
    this.trainingForm.updateValueAndValidity();
    if (this.trainingForm.invalid) {
      return;
    }

    let offset = new Date().getTimezoneOffset() / (-60);

    let training = {
      id: this.training.id,
      name: this.trainingForm.controls.name.value,
      maxParticipants: this.trainingForm.controls.maxParticipants.value,
      description: this.trainingForm.controls.description.value,
      startDate: moment.utc(new Date(this.date.year, this.date.month - 1, this.date.day, this.startTime.hour, this.startTime.minute, this.startTime.second)
        .toISOString()).utcOffset(offset).local(true).format('YYYY-MM-DDTHH:mm:ss'),
      endDate: moment.utc(new Date(this.date.year, this.date.month - 1, this.date.day, this.endTime.hour, this.endTime.minute, this.endTime.second)
        .toISOString()).utcOffset(offset).local(true).format('YYYY-MM-DDTHH:mm:ss'),
      actualParticipants: null,
      subscribed: null
    };

    if (this.training.id !== null) {
      this._updateTraining(training);
    } else {
      this._createTraining(training);
    }

  }

  private _createTraining(training: TrainingModel) {
    this._trainingService.create(training).subscribe((response) => {
        this._router.navigate(['/trainings']);
      },
      (error) => {
        if (error.status === 406) {
          this._userNotificationService.notifyUser(
            "Validációs hiba! Edzés adatai nem megfelelőek. Kérlek ellenőrizd, hogy mindent megfelelően töltöttél-e ki!", true
          );
        } else {
          this._userNotificationService.notifyUser("Hiba történt!", true);
        }
      });
  }

  private _updateTraining(training: TrainingModel) {
    this._trainingService.update(training).subscribe((response) => {
        this._router.navigate(['/trainings']);
      },
      (error) => {
        if (error.status === 406) {
          this._userNotificationService.notifyUser(
            "Validációs hiba! Edzés adatai nem megfelelőek. Kérlek ellenőrizd, hogy mindent megfelelően töltöttél-e ki!", true
          );
        } else {
          this._userNotificationService.notifyUser("Hiba történt!", true);
        }
      });
  }

}
