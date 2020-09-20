import {NgModule} from '@angular/core';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {ReservationRoutingModule} from './reservation-routing.module';
import {ReservationCalendarViewComponent} from './reservation-calendar-view/reservation-calendar-view.component';
import {ReservationItemComponent} from './reservation-item/reservation-item.component';
import {ReservationViewComponent} from './reservation-view/reservation-view.component';
import {FullCalendarModule} from '@fullcalendar/angular';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgbDatepickerModule, NgbTimepickerModule} from '@ng-bootstrap/ng-bootstrap';
import {SharedModule} from '../shared/shared.module';
import {CommonModule} from '@angular/common';

@NgModule({
  imports: [
    ReservationRoutingModule,
    FontAwesomeModule,
    FullCalendarModule,
    FormsModule,
    NgbTimepickerModule,
    NgbDatepickerModule,
    SharedModule,
    CommonModule,
    ReactiveFormsModule
  ],
  declarations: [ReservationItemComponent, ReservationCalendarViewComponent, ReservationViewComponent]
})
export class ReservationModule {
}
