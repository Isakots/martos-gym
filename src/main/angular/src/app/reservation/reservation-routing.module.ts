import {RouterModule, Routes} from "@angular/router";
import {AuthorizationGuard} from "../core/guard/authorization-guard.service";
import {NgModule} from "@angular/core";
import {ReservationViewComponent} from "./reservation-view/reservation-view.component";
import {ReservationResolve} from "./reservation.resolver";
import {ToolResolve} from "../tool/tool.resolver";

const reservationRoutes: Routes = [
  {
    path: 'reservations',
    children: [
      {
        path: '',
        redirectTo: 'view',
        pathMatch: 'full'
      },
      {
        path: 'view',
        component: ReservationViewComponent,
        resolve: {
          reservations: ReservationResolve,
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
    RouterModule.forChild(reservationRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class ReservationRoutingModule {
}
