import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Reservation} from '../../shared/domain/reservation';
import {AbstractControl, FormControl, FormGroup, Validators} from '@angular/forms';
import {NgbCalendar, NgbDateStruct, NgbTimeStruct} from '@ng-bootstrap/ng-bootstrap';
import {Tool} from '../../shared/domain/tool';
import {ReservationService} from '../../shared/service/reservation.service';
import {UserNotificationService} from '../../shared/service/user-notification.service';

@Component({
  selector: 'app-reservation-view',
  templateUrl: './reservation-view.component.html',
  styleUrls: ['./reservation-view.component.scss']
})
export class ReservationViewComponent implements OnInit {
  readonly minuteStep = 30;
  triedToSave = false;

  startTime: NgbTimeStruct = {hour: 8, minute: 0, second: 0};
  endTime: NgbTimeStruct = {hour: 16, minute: 0, second: 0};
  startDate: NgbDateStruct;
  endDate: NgbDateStruct;

  reservations: Reservation[];

  toolToReserve: Tool;

  reservationForm: FormGroup;

  showDateValidationErrorMessage = false;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private calendar: NgbCalendar,
    private reservationService: ReservationService,
    private userNotificationService: UserNotificationService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.reservations = [];
    this.activatedRoute.data.subscribe(({reservations, toolToReserve}) => {
        this.reservations = reservations;
        this.toolToReserve = toolToReserve;
      },
      () => {
        this.router.navigateByUrl('/tools');
      });
    this._initFormGroup();
    this._initDateStructs();
  }

  private _initFormGroup() {
    this.reservationForm = new FormGroup({
      quantity: new FormControl('', Validators.required)
    });
  }

  private _initDateStructs() {
    const currentDate = new Date();
    this.startDate = {year: currentDate.getFullYear(), month: currentDate.getMonth() + 1, day: currentDate.getDate()};
    this.endDate = {year: currentDate.getFullYear(), month: currentDate.getMonth() + 1, day: currentDate.getDate() + 1};
  }

  getReservations() {
    return this.reservations;
  }

  showValidationMessage(formControl: AbstractControl) {
    return (formControl.invalid && (formControl.dirty || formControl.touched)) || (formControl.invalid && this.triedToSave);
  }

  sendReservation() {
    this.triedToSave = true;
    this._dateValidation();
    this.reservationForm.updateValueAndValidity();
    if (this.reservationForm.invalid || this.showDateValidationErrorMessage) {
      return;
    }

    this.reservationService.create({
      id: null,
      subjectName: this.toolToReserve.name,
      quantity: this.reservationForm.controls.quantity.value,
      startDate: new Date(
        this.startDate.year, this.startDate.month - 1, this.startDate.day,
        this.startTime.hour, this.startTime.minute, this.startTime.second
      ).toISOString().substring(0, 19),
      endDate: new Date(
        this.endDate.year, this.endDate.month - 1, this.endDate.day,
        this.endTime.hour, this.endTime.minute, this.endTime.second
      ).toISOString().substring(0, 19),
      returned: null
    }).subscribe(() => {
        this.router.navigate(['/profile/reservations']);
      },
      (error) => {
        if (error.status === 406) {
          this.userNotificationService.notifyUser('Validációs hiba! Foglalás nem megfelelő. Kérlek ellenőrizd, hogy mindent megfelelően töltöttél-e ki!', true);
        }
      });
  }

  private _dateValidation() {
    const yesterday = new Date();
    yesterday.setDate(new Date().getDate() - 1);
    if (new Date(this.startDate.year, this.startDate.month - 1, this.startDate.day).getTime() < yesterday.getTime()) {
      this.showDateValidationErrorMessage = true;
    } else if (new Date(this.endDate.year, this.endDate.month - 1, this.endDate.day).getTime() <
      new Date(this.startDate.year, this.startDate.month - 1, this.startDate.day).getTime()) {
      this.showDateValidationErrorMessage = true;
    } else if (new Date(this.startDate.year, this.startDate.month - 1, this.startDate.day).getTime() ==
      new Date(this.endDate.year, this.endDate.month - 1, this.endDate.day).getTime() &&
      (this.endTime.hour < this.startTime.hour ||
        (this.endTime.hour == this.startTime.hour && this.endTime.minute <= this.startTime.minute))) {
      this.showDateValidationErrorMessage = true;
    } else {
      this.showDateValidationErrorMessage = false;
    }
  }

}
