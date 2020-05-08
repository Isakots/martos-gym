import {RouterModule, Routes} from "@angular/router";
import {AuthorizationGuard} from "../core/guard/authorization-guard.service";
import {NgModule} from "@angular/core";
import {ManagementComponent} from "./management/management.component";
import {ManagedUserResolver} from "./managed-user.resolver";
import {GymPeriodResolver} from "./gym-period.resolver";
import {MailSendingComponent} from "./mail-sending/mail-sending.component";

const toolRoutes: Routes = [
  {
    path: 'admin',
    children: [
      {
        path: 'management',
        component: ManagementComponent,
        resolve: {
          managedUsers: ManagedUserResolver,
          gymPeriods: GymPeriodResolver
        },
        data: {
          authority: "ROLE_MEMBER"
        },
        canActivate: [AuthorizationGuard]
      },
      {
        path: 'mail-sending',
        component: MailSendingComponent,
        data: {
          authority: "ROLE_MEMBER"
        },
        canActivate: [AuthorizationGuard]
      }
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(toolRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class AdminRoutingModule {
}
