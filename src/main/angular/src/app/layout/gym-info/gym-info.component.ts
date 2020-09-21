import { Component, OnInit } from '@angular/core';
import {Article} from "../../shared/domain/article";
import {ArticleService} from "../../shared/service/article.service";
import {ArticleType} from "../../shared/enums/article-type.enum";
import {filter, map} from "rxjs/operators";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-gym-info',
  templateUrl: './gym-info.component.html',
  styleUrls: ['./gym-info.component.scss']
})
export class GymInfoComponent implements OnInit {

  contentNotFound = true;
  gymInfoArticle: Article;

  constructor(private readonly articleService: ArticleService) { }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.articleService
      .query({type: ArticleType.GYM})
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
            this.gymInfoArticle = res[0];
          }
        },
        () => this.onError()
      );
  }

  private onError() {
    this.contentNotFound = true;
    this.gymInfoArticle = null;
  }

}
