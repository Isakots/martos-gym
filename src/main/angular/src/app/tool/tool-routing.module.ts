import {RouterModule, Routes} from "@angular/router";
import {AuthorizationGuard} from "../core/guard/authorization-guard.service";
import {NgModule} from "@angular/core";
import {ToolViewComponent} from "./tool-view/tool-view.component";
import {ToolResolve} from "./tool.resolver";
import {ReservationViewComponent} from "../reservation/reservation-view/reservation-view.component";
import {ReservationResolve} from "../reservation/reservation.resolver";

const toolRoutes: Routes = [
  {
    path: 'tools',
    children: [
      {
        path: '',
        redirectTo: 'view',
        pathMatch: 'full'
      },
      {
        path: 'view',
        component: ToolViewComponent,
        resolve: {
          tool: ToolResolve
        },
        data: {
          authority: "ROLE_USER"
        },
        canActivate: [AuthorizationGuard]
      },
      {
        path: ':id/reserve',
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
    RouterModule.forChild(toolRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class ToolRoutingModule {
}
