import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { filter, map } from 'rxjs/operators';
import { ArticleService } from '../../../core/services/article.service';
import { UserNotificationService } from '../../../core/services/user-notification.service';
import { ArticleType } from '../../../shared/constants';
import { Article } from '../../../shared/interfaces';

@Component({
    selector: 'app-timeline',
    templateUrl: './timeline.component.html',
    styleUrls: ['./timeline.component.scss']
})
export class TimelineComponent implements OnInit {
    articles: Array<Article> = [];
    content!: string;

    constructor(private readonly articleService: ArticleService,
                private readonly userNotificationService: UserNotificationService,
                private readonly translate: TranslateService) {
    }

    ngOnInit(): void {
        this.loadAll();
    }

    private loadAll(): void {
        this.articleService
            .query({type: ArticleType.NEWS})
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
        this.articles = response;
        this.sortArticlesByCreatedDate();
    }

    private sortArticlesByCreatedDate(): void {
        this.articles.sort((a, b) => {
            if (a.createdDate === b.createdDate) {
                return 0;
            }
            return a.createdDate > b.createdDate ? -1 : 1;
        });
    }

    private handleError(): void {
        this.userNotificationService.notifyUser(this.translate.instant('ERROR_MESSAGE.ARTICLES_NOT_FOUND'), true);
    }
}
