import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ArticleService } from '../core/services/article.service';
import { ArticleType } from '../shared/constants';
import { Article } from '../shared/interfaces';

@Injectable({providedIn: 'root'})
export class ArticleResolve implements Resolve<Article> {
    constructor(private readonly service: ArticleService) {
    }

    // eslint-disable-next-line @typescript-eslint/no-unused-vars-experimental
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Article>{
        const id = route.params.id;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Article>) => response.ok),
                map((article: HttpResponse<Article>) => article.body as Article)
            );
        }
        // eslint-disable-next-line @typescript-eslint/consistent-type-assertions
        const emptyArticle: Article = {
            id: '',
            title: '',
            type: ArticleType.NEWS,
            introduction: '',
            content: '',
            createdDate: ''
        };
        return of(emptyArticle);
    }
}
