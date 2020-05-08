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
import {MailSendingComponent} from "./mail-sending/mail-sending.component";
import {CKEditorModule} from "@ckeditor/ckeditor5-angular";

@NgModule({
  imports: [
    AdminRoutingModule,
    FontAwesomeModule,
    SharedModule,
    ReactiveFormsModule,
    CommonModule,
    FormsModule,
    NgbDatepickerModule,
    CKEditorModule
  ],
  declarations: [ManagementComponent, UserManagementComponent, GymPeriodComponent, ManagedUserControlComponent, MailSendingComponent]
})
export class AdminModule {
}
