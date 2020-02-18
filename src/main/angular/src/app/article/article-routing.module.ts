import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {ArticleUpdateComponent} from "./article-update/article-update.component";
import {ArticleDetailComponent} from "./article-detail/article-detail.component";
import {AdminAccessGuard} from "../core/guard/admin-access.guard";
import {ArticleResolve} from "./article.resolver";

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
        canActivate: [AdminAccessGuard]
      },
      {
        path: ':id/edit',
        component: ArticleUpdateComponent,
        canActivate: [AdminAccessGuard]
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
