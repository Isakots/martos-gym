import {NgModule} from '@angular/core';
import {AccountRoutingModule} from "./account-routing.module";
import {ProfileComponent} from "./profile/profile.component";
import {ChangePasswordComponent} from "./change-password/change-password.component";

@NgModule({
  imports: [
    AccountRoutingModule
  ],
  declarations: [
    ProfileComponent,
    ChangePasswordComponent
  ]
})
export class AccountModule {
}
