import {RouterModule, Routes} from "@angular/router";
import {AuthorizationGuard} from "../core/guard/authorization-guard.service";
import {NgModule} from "@angular/core";
import {TrainingResolver} from "./training.resolver";
import {TrainingSubscriptionViewComponent} from "./training-subscription-view/training-subscription-view.component";
import {TrainingUpdateComponent} from "./training-update/training-update.component";

const trainingRoutes: Routes = [
  {
    path: 'trainings',
    children: [
      {
        path: '',
        redirectTo: 'view',
        pathMatch: 'full'
      },
      {
        path: 'view',
        component: TrainingSubscriptionViewComponent,
        data: {
          authority: "ROLE_USER"
        },
        canActivate: [AuthorizationGuard]
      },
      {
        path: 'new',
        component: TrainingUpdateComponent,
        resolve: {
          training: TrainingResolver
        },
        data: {
          authority: "ROLE_MEMBER"
        },
        canActivate: [AuthorizationGuard]
      },
      {
        path: 'update',
        component: TrainingUpdateComponent,
        resolve: {
          training: TrainingResolver
        },
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
    RouterModule.forChild(trainingRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class TrainingRoutingModule {
}
