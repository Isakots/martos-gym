import { Component, OnInit } from '@angular/core';
import {Article} from "../../shared/domain/article";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss']
})
export class ArticleDetailComponent implements OnInit {
  article: Article;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ article }) => {
      this.article = article;
    });
  }

}
