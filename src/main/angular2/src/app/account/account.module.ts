import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { SharedModule } from '../shared/shared.module';
import { AccountRoutingModule } from './account-routing.module';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ProfileComponent } from './profile/profile.component';

@NgModule({
    imports: [
        AccountRoutingModule,
        SharedModule,
        ReactiveFormsModule,
        CommonModule,
        TranslateModule
    ],
    declarations: [
        ProfileComponent,
        ChangePasswordComponent
    ]
})
export class AccountModule {
}
