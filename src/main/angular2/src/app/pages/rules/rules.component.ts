import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { filter, map } from 'rxjs/operators';
import { ArticleService } from '../../core/services/article.service';
import { ArticleType } from '../../shared/constants';
import { Article } from '../../shared/interfaces';

@Component({
    selector: 'app-rules',
    templateUrl: './rules.component.html'
})
export class RulesComponent implements OnInit {

    contentNotFound = true;
    article!: Article;

    constructor(private readonly articleService: ArticleService) {
    }

    ngOnInit(): void {
        this.loadAll();
    }

    loadAll(): void {
        this.articleService
            .query({type: ArticleType.RULES})
            .pipe(
                filter((res: HttpResponse<Array<Article>>) => res.ok),
                map((res: HttpResponse<Array<Article>>) => res.body as Array<Article>)
            )
            .subscribe(
                (response: Array<Article>) => this.handleSuccess(response),
                () => this.handleError()
            );
    }

    private handleSuccess(response: Array<Article>): void {
        this.article = response[0];
        if (0 === response.length) {
            this.contentNotFound = true;
        } else {
            this.contentNotFound = false;
            this.article = response[0];
        }
    }

    private handleError(): void {
        this.contentNotFound = true;
    }
}
