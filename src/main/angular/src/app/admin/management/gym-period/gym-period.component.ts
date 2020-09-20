import {Component, Input, OnInit} from '@angular/core';
import {GymPeriod} from '../../../shared/domain/gym-period';
import {GymPeriodService} from '../../../shared/service/gym-period.service';
import {UserNotificationService} from '../../../shared/service/user-notification.service';
import {NgbCalendar, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {FormControl, Validators} from '@angular/forms';

@Component({
  selector: 'app-gym-period',
  templateUrl: './gym-period.component.html',
  styleUrls: ['./gym-period.component.scss']
})
export class GymPeriodComponent implements OnInit {
  startDate: NgbDateStruct;
  endDate: NgbDateStruct;
  isNewPeriodFormVisible = false;
  periodNameFormControl: FormControl;

  activePeriod: GymPeriod;

  @Input()
  periods: GymPeriod[];

  constructor(
    private calendar: NgbCalendar,
    private gymPeriodService: GymPeriodService,
    private userNotificationService: UserNotificationService) {
  }

  ngOnInit(): void {
    if (this.periods !== null && this.periods.length !== 0) {
      this.activePeriod = this.periods.find(period => period.active);
    } else {
      this.activePeriod = null;
    }
    this.periodNameFormControl = new FormControl('', Validators.required);
  }

  public get notActivePeriods() {
    return this.periods.filter(period => !period.active);
  }

  isActivePeriodExist() {
    if (this.periods !== null && this.periods.length !== 0) {
      return this.activePeriod !== undefined && this.activePeriod !== null;
    } else {
      return false;
    }
  }

  closeActivePeriod() {
    this.gymPeriodService.patch().subscribe(
      response => {
        this.periods = response.body;
        this.activePeriod = null;
        this.userNotificationService.notifyUser('Időszak lezárása sikeres!', false);
      },
      () => {
        this.userNotificationService.notifyUser('Időszak lezárása sikertelen!', true);
      }
    );
  }

  setFormVisible() {
    this.isNewPeriodFormVisible = true;
  }

  saveNewPeriod() {
    this.gymPeriodService.create({
      id: null,
      name: this.periodNameFormControl.value,
      active: null,
      startDate: new Date(this.startDate.year, this.startDate.month - 1, this.startDate.day).toISOString().substring(0, 10),
      endDate: new Date(this.endDate.year, this.endDate.month - 1, this.endDate.day).toISOString().substring(0, 10)
    }).subscribe(
      response => {
        this.activePeriod = response.body;
        this.isNewPeriodFormVisible = false;
        this.userNotificationService.notifyUser('Időszak hozzáadása sikeres!', false);
      },
      () => {
        this.userNotificationService.notifyUser('Időszak hozzáadása sikertelen!', true);
      }
    );
  }
}
