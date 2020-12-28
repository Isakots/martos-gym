import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { GeneralConfirmationModalComponent } from './components/general-confirmation-modal/general-confirmation-modal.component';
import { MessageBoxComponent } from './components/message-box/message-box.component';
import { HasRoleDirective } from './directives/has-role.directive';

@NgModule({
    declarations: [
        HasRoleDirective,
        MessageBoxComponent,
        GeneralConfirmationModalComponent
    ],
    imports: [
        CommonModule,
        FontAwesomeModule,
        TranslateModule
    ],
    exports: [
        HasRoleDirective,
        MessageBoxComponent,
        GeneralConfirmationModalComponent
    ]
})
export class SharedModule {
}
