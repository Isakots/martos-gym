import {RouterModule, Routes} from "@angular/router";
import {AuthorizationGuard} from "../core/guard/authorization-guard.service";
import {NgModule} from "@angular/core";
import {ReservationViewComponent} from "./reservation-view/reservation-view.component";
import {ReservationResolve} from "./reservation.resolver";
import {ToolResolve} from "../tool/tool.resolver";

const toolRoutes: Routes = [
  {
    path: 'reservation',
    children: [
      {
        path: ':id/view',
        component: ReservationViewComponent,
        resolve: {
          reservation: ReservationResolve,
          toolToReserve: ToolResolve
        },
        data: {
          authority: "ROLE_USER"
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
export class ReservationRoutingModule {
}
