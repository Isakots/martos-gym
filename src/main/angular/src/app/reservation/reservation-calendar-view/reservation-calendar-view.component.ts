import {Component, Input, OnInit, ViewChild} from '@angular/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactions from '@fullcalendar/interaction';
import bootstrapPlugin from '@fullcalendar/bootstrap';
import listPlugin from '@fullcalendar/list';
import {FullCalendarComponent} from '@fullcalendar/angular';
import {Reservation} from '../../shared/domain/reservation';

@Component({
  selector: 'app-reservation-calendar-view',
  templateUrl: './reservation-calendar-view.component.html',
  styleUrls: ['./reservation-calendar-view.component.scss']
})
export class ReservationCalendarViewComponent implements OnInit {
  @ViewChild('calendar') calendarComponent: FullCalendarComponent;

  @Input()
  reservations: Reservation[];

  calendarPlugins = [dayGridPlugin, timeGridPlugin, interactions, listPlugin, bootstrapPlugin];
  themeSystem: 'bootstrap';

  constructor() {
  }

  ngOnInit(): void {
  }

  getEvents() {
    let events = [];
    this.reservations.forEach((reservation) => {
      const isAllDay: boolean = reservation.startDate.substring(0, 10) < reservation.endDate.substring(0, 10);
      events.push({
        title: reservation.subjectName + ' ' + reservation.quantity + ' db',
        start: reservation.startDate,
        end: reservation.endDate,
        allDay: isAllDay
      });
    });
    return events;
  }

  getHeaders() {
    return {
      left: 'prev,next today myCustomButton',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay'
    };
  }
}
