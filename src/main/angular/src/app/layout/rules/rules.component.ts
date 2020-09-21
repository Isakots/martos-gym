import { Component, OnInit } from '@angular/core';
import {ArticleType} from "../../shared/enums/article-type.enum";
import {filter, map} from "rxjs/operators";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Article} from "../../shared/domain/article";
import {ArticleService} from "../../shared/service/article.service";

@Component({
  selector: 'app-rules',
  templateUrl: './rules.component.html',
  styleUrls: ['./rules.component.scss']
})
export class RulesComponent implements OnInit {

  contentNotFound = true;
  rulesArticle: Article;

  constructor(private readonly articleService: ArticleService) { }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.articleService
      .query({type: ArticleType.RULES})
      .pipe(
        filter((res: HttpResponse<Article[]>) => res.ok),
        map((res: HttpResponse<Article[]>) => res.body)
      )
      .subscribe(
        (res: Article[]) => {
          if(res.length === 0)
          {
            this.contentNotFound = true;
          } else {
            this.contentNotFound = false;
            this.rulesArticle = res[0];
          }
        },
        () => this.onError()
      );
  }

  private onError() {
      this.contentNotFound = true;
      this.rulesArticle = null;
  }
}
