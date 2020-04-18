import {NgModule} from "@angular/core";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {AdminRoutingModule} from "./admin-routing.module";
import {ManagementComponent} from "./management/management.component";
import {SharedModule} from "../shared/shared.module";
import {UserManagementComponent} from "./management/user-management/user-management.component";
import {GymPeriodComponent} from "./management/gym-period/gym-period.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {ManagedUserControlComponent} from "./management/user-management/managed-user-control/managed-user-control.component";
import {NgbDatepickerModule} from "@ng-bootstrap/ng-bootstrap";

@NgModule({
  imports: [
    AdminRoutingModule,
    FontAwesomeModule,
    SharedModule,
    ReactiveFormsModule,
    CommonModule,
    FormsModule,
    NgbDatepickerModule
  ],
  declarations: [ManagementComponent, UserManagementComponent, GymPeriodComponent, ManagedUserControlComponent]
})
export class AdminModule {
}
