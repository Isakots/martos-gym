import {RouterModule, Routes} from "@angular/router";
import {AuthorizationGuard} from "../core/guard/authorization-guard.service";
import {NgModule} from "@angular/core";
import {ToolUpdateComponent} from "./tool-update/tool-update.component";
import {ToolViewComponent} from "./tool-view/tool-view.component";
import {ToolResolve} from "./tool.resolver";

const toolRoutes: Routes = [
  {
    path: 'tools',
    children: [
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
        path: 'new',
        component: ToolUpdateComponent,
        resolve: {
          tool: ToolResolve
        },
        data: {
          authority: "ROLE_MEMBER"
        },
        canActivate: [AuthorizationGuard]
      },
      {
        path: ':id/edit',
        component: ToolUpdateComponent,
        resolve: {
          tool: ToolResolve
        },
        data: {
          authority: "ROLE_ADMIN"
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
