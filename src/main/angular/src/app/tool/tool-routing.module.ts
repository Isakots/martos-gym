import {RouterModule, Routes} from "@angular/router";
import {AuthorizationGuard} from "../core/guard/authorization-guard.service";
import {NgModule} from "@angular/core";
import {ToolViewComponent} from "./tool-view/tool-view.component";
import {ToolResolve} from "./tool.resolver";

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
