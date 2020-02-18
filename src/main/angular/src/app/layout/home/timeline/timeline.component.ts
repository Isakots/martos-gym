import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {filter, map} from 'rxjs/operators';

import {Article} from "../../../shared/domain/article";
import {ArticleService} from "../../../shared/service/article.service";
import {ArticleType} from "../../../shared/enums/article-type.enum";

@Component({
  selector: 'timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.scss']
})
export class TimelineComponent implements OnInit {
  articles: Article[];
  content: string;

  constructor(private articleService: ArticleService) {
  }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.articleService
      .query({type: ArticleType.NEWS})
      .pipe(
        filter((res: HttpResponse<Article[]>) => res.ok),
        map((res: HttpResponse<Article[]>) => res.body)
      )
      .subscribe(
        (res: Article[]) => {
          this.articles = res;
          this.articles.sort((a, b) => {
            if (a.createdDate === b.createdDate) {
              return 0;
            }
            return a.createdDate > b.createdDate ? -1 : 1;
          });
        },
        (res: HttpErrorResponse) => this.onError()
      );
  }

  trackId(index: number, item: Article) {
    return item.id;
  }

  protected onError() {
    console.log('Error occured during getting Articles from server...')
  }
}
