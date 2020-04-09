import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Reservation} from "../../shared/domain/reservation";
import {FormControl, FormGroup} from "@angular/forms";
import {NgbCalendar, NgbDateStruct, NgbTimeStruct} from "@ng-bootstrap/ng-bootstrap";
import {Tool} from "../../shared/domain/tool";

@Component({
  selector: 'app-reservation-view',
  templateUrl: './reservation-view.component.html',
  styleUrls: ['./reservation-view.component.scss']
})
export class ReservationViewComponent implements OnInit {
  startTime: NgbTimeStruct = {hour: 8, minute: 0, second: 0};
  endTime: NgbTimeStruct = {hour: 16, minute: 0, second: 0};
  startDate: NgbDateStruct;
  endDate: NgbDateStruct;

  reservation: Reservation;

  toolToReserve: Tool;

  reservationForm: FormGroup;
  minuteStep = 30;

  constructor(protected activatedRoute: ActivatedRoute,
              private calendar: NgbCalendar) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({reservation, toolToReserve}) => {
        this.reservation = reservation;
        this.toolToReserve = toolToReserve;
        console.log(this.reservation);
        console.log(this.toolToReserve);
      },
      error => {
        console.log(error);
      });
    this._initFormGroup();
  }

  private _initFormGroup() {
    this.reservationForm = new FormGroup({
      subjectName: new FormControl(''),
      startDate: new FormControl(''),
      endDate: new FormControl('')
    });
  }

  getReservations() {
    return [];
  }

  sendReservation() {

  }

  isReservationDisabled() {

  }
}
