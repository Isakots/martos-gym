import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {ArticleUpdateComponent} from "./article-update/article-update.component";
import {ArticleDetailComponent} from "./article-detail/article-detail.component";
import {ArticleResolve} from "./article.resolver";
import {AuthorizationGuard} from "../core/guard/authorization-guard.service";

const articleRoutes: Routes = [
  {
    path: 'articles',
    children: [
      {
        path: ':id/view',
        component: ArticleDetailComponent,
        resolve: {
          article: ArticleResolve
        },
      },
      {
        path: 'new',
        component: ArticleUpdateComponent,
        resolve: {
          article: ArticleResolve
        },
        data : {
          authority: "ROLE_MEMBER"
        },
        canActivate: [AuthorizationGuard]
      },
      {
        path: ':id/edit',
        component: ArticleUpdateComponent,
        resolve: {
          article: ArticleResolve
        },
        data : {
          authority: "ROLE_MEMBER"
        },
        canActivate: [AuthorizationGuard]
      }
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(articleRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class ArticleRoutingModule {
}
