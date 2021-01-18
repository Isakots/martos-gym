import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { SharedModule } from '../shared/shared.module';
import { ArticleDetailComponent } from './article-detail/article-detail.component';
import { ArticleRoutingModule } from './article-routing.module';
import { ArticleUpdateComponent } from './article-update/article-update.component';

@NgModule({
    imports: [
        ArticleRoutingModule,
        FontAwesomeModule,
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        SharedModule,
        TranslateModule
    ],
    declarations: [
        ArticleDetailComponent,
        ArticleUpdateComponent
    ]
})
export class ArticleModule {
}
