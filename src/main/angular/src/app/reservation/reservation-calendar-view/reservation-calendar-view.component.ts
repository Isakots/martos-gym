import {Component, Input, OnInit, ViewChild} from "@angular/core";
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactions from '@fullcalendar/interaction';
import bootstrapPlugin from '@fullcalendar/bootstrap';
import listPlugin from '@fullcalendar/list';
import {FullCalendarComponent} from "@fullcalendar/angular";
import {Reservation} from "../../shared/domain/reservation";

@Component({
  selector: 'app-reservation-calendar-view',
  templateUrl: './reservation-calendar-view.component.html',
  styleUrls: ['./reservation-calendar-view.component.scss']
})
export class ReservationCalendarViewComponent implements OnInit {
  @ViewChild('calendar') calendarComponent: FullCalendarComponent;

  @Input()
  reservations: Reservation[] = [];

  calendarPlugins = [dayGridPlugin, timeGridPlugin, interactions, listPlugin, bootstrapPlugin];
  themeSystem: 'bootstrap';

  constructor() {
  }

  ngOnInit(): void {
  }

  getEvents() {
    return {
      events: [
        { // this object will be "parsed" into an Event Object
          title: 'TRX edzés', // a property!
          start: '2020-04-04T12:30:00', // a property!
          end: '2020-04-04T14:30:00', // a property! ** see important note below about 'end' **
          allDay: false
        },
        { // this object will be "parsed" into an Event Object
          title: 'TRX edzés', // a property!
          start: '2020-04-04T13:30:00', // a property!
          end: '2020-04-04T15:30:00', // a property! ** see important note below about 'end' **
          allDay: false
        },
        { // this object will be "parsed" into an Event Object
          title: 'TRX edzés', // a property!
          start: '2020-04-04T13:30:00', // a property!
          end: '2020-04-04T14:0:00', // a property! ** see important note below about 'end' **
          allDay: false
        },
        { // this object will be "parsed" into an Event Object
          title: 'TRX edzés', // a property!
          start: '2020-04-04T11:30:00', // a property!
          end: '2020-04-04T15:30:00', // a property! ** see important note below about 'end' **
          allDay: false
        }
      ]
    };
  }

  getHeaders() {
    return {
      left: 'prev,next today myCustomButton',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay'
    };
  }
}
