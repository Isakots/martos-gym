import {NgModule} from '@angular/core';
import {ArticleRoutingModule} from "./article-routing.module";
import { ArticleDetailComponent } from './article-detail/article-detail.component';
import { ArticleUpdateComponent } from './article-update/article-update.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {CommonModule} from "@angular/common";

@NgModule({
  imports: [
    ArticleRoutingModule,
    FontAwesomeModule,
    CommonModule
  ],
  declarations: [ArticleDetailComponent, ArticleUpdateComponent]
})
export class ArticleModule {
}
