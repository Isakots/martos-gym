import {Article} from "../shared/domain/article";
import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {ArticleService} from "../shared/service/article.service";
import {Observable, of} from "rxjs";
import {filter, map} from "rxjs/operators";
import {HttpResponse} from "@angular/common/http";

@Injectable({ providedIn: 'root' })
export class ArticleResolve implements Resolve<Article> {
  constructor(private service: ArticleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Article> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Article>) => response.ok),
        map((article: HttpResponse<Article>) => article.body)
      );
    }
    return of(new Article());
  }
}
