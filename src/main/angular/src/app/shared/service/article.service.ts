import {Injectable} from "@angular/core";
import {Article} from "../domain/article";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {createRequestOption} from "../util/request-util";
import {EnvironmentService} from "./environment.service";

type EntityResponseType = HttpResponse<Article>;
type EntityArrayResponseType = HttpResponse<Article[]>;

@Injectable({ providedIn: 'root' })
export class ArticleService {
  public resourceUrl;

  constructor(
    protected http: HttpClient,
    private environmentService: EnvironmentService) {
    this.resourceUrl = environmentService.apiUrl + '/articles';
  }

  create(article: Article): Observable<EntityResponseType> {
    return this.http.post<Article>(this.resourceUrl, article, { observe: 'response' });
  }

  update(article: Article): Observable<EntityResponseType> {
    return this.http.put<Article>(this.resourceUrl, article, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<Article>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<Article[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
