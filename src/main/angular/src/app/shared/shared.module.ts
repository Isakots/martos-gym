import {NgModule} from '@angular/core';
import {GeneralConfirmationModalComponent} from "./component/general-confirmation-modal/general-confirmation-modal.component";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {ImageViewComponent} from './component/image-view/image-view.component';
import {FileUploadComponent} from './component/file-upload/file-upload.component';
import {CommonModule} from "@angular/common";
import {PasswordStrengthBarComponent} from "./component/password-strength-bar/password-strength-bar.component";

@NgModule({
  declarations: [
    GeneralConfirmationModalComponent,
    ImageViewComponent,
    FileUploadComponent,
    PasswordStrengthBarComponent
  ],
  imports: [
    FontAwesomeModule,
    CommonModule
  ],
  exports: [
    GeneralConfirmationModalComponent,
    ImageViewComponent,
    FileUploadComponent,
    PasswordStrengthBarComponent
  ]
})
export class SharedModule {
}
