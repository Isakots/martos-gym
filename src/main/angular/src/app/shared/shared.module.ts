import {NgModule} from '@angular/core';
import {GeneralConfirmationModalComponent} from "./component/general-confirmation-modal/general-confirmation-modal.component";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {ImageViewComponent} from './component/image-view/image-view.component';
import {FileUploadComponent} from './component/file-upload/file-upload.component';
import {CommonModule} from "@angular/common";
import {PasswordStrengthBarComponent} from "./component/password-strength-bar/password-strength-bar.component";
import {HasAnyAuthorityDirective} from "./directive/has-any-authority.directive";
import {NotificationDisplayerComponent} from "./component/error-message/notification-displayer.component";

@NgModule({
  declarations: [
    GeneralConfirmationModalComponent,
    ImageViewComponent,
    FileUploadComponent,
    PasswordStrengthBarComponent,
    HasAnyAuthorityDirective,
    NotificationDisplayerComponent
  ],
  imports: [
    FontAwesomeModule,
    CommonModule
  ],
  exports: [
    GeneralConfirmationModalComponent,
    ImageViewComponent,
    FileUploadComponent,
    PasswordStrengthBarComponent,
    HasAnyAuthorityDirective,
    NotificationDisplayerComponent
  ]
})
export class SharedModule {
}
