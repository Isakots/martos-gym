import {NgModule} from '@angular/core';
import {AccountRoutingModule} from './account-routing.module';
import {ProfileComponent} from './profile/profile.component';
import {ChangePasswordComponent} from './change-password/change-password.component';
import {SharedModule} from '../shared/shared.module';
import {ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

@NgModule({
  imports: [
    AccountRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    CommonModule
  ],
  declarations: [
    ProfileComponent,
    ChangePasswordComponent
  ]
})
export class AccountModule {
}
