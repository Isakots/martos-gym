import {NgModule} from '@angular/core';
import {GeneralConfirmationModalComponent} from "./component/general-confirmation-modal/general-confirmation-modal.component";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";

@NgModule({
  declarations: [GeneralConfirmationModalComponent],
  imports: [
    FontAwesomeModule
  ],
  exports: [GeneralConfirmationModalComponent]
})
export class SharedModule {
}
