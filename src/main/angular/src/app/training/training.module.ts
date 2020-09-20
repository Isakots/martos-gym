import {NgModule} from '@angular/core';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {FullCalendarModule} from '@fullcalendar/angular';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgbDatepickerModule, NgbTimepickerModule} from '@ng-bootstrap/ng-bootstrap';
import {SharedModule} from '../shared/shared.module';
import {CommonModule} from '@angular/common';
import {TrainingRoutingModule} from './training-routing.module';
import {TrainingSubscriptionViewComponent} from './training-subscription-view/training-subscription-view.component';
import {TrainingUpdateComponent} from './training-update/training-update.component';
import {TrainingItemComponent} from './training-item/training-item.component';

@NgModule({
  imports: [
    TrainingRoutingModule,
    FontAwesomeModule,
    FullCalendarModule,
    FormsModule,
    NgbTimepickerModule,
    NgbDatepickerModule,
    SharedModule,
    CommonModule,
    ReactiveFormsModule
  ],
  declarations: [TrainingSubscriptionViewComponent, TrainingUpdateComponent, TrainingItemComponent]
})
export class TrainingModule {
}
