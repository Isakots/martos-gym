import {NgModule} from '@angular/core';
import {ArticleRoutingModule} from './article-routing.module';
import {ArticleDetailComponent} from './article-detail/article-detail.component';
import {ArticleUpdateComponent} from './article-update/article-update.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CKEditorModule} from '@ckeditor/ckeditor5-angular';
import {SharedModule} from '../shared/shared.module';

@NgModule({
  imports: [
    ArticleRoutingModule,
    FontAwesomeModule,
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    CKEditorModule,
    SharedModule
  ],
  declarations: [ArticleDetailComponent, ArticleUpdateComponent]
})
export class ArticleModule {
}
