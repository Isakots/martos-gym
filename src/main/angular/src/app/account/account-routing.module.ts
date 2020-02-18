import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {ProfileComponent} from "./profile/profile.component";
import {ChangePasswordComponent} from "./change-password/change-password.component";
import {UserAccessGuard} from "../core/guard/user-access.guard";

const accountRoutes: Routes = [
  {
    path: 'user',
    children: [
      {path: 'profile', component: ProfileComponent},
      {path: 'change-password', component: ChangePasswordComponent}
    ],
    canActivate: [UserAccessGuard]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(accountRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class AccountRoutingModule {
}
