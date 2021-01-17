import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from '../../shared/interfaces';
import { createRequestOption } from '../util/request-util';
import { EnvironmentService } from './environment.service';

@Injectable({
    providedIn: 'root'
})
export class ArticleService {
    resourceUrl: string;

    constructor(private readonly http: HttpClient,
                private readonly environmentService: EnvironmentService) {
        this.resourceUrl = `${this.environmentService.apiUrl}/articles`;
    }

    create(article: Article): Observable<HttpResponse<Article>> {
        return this.http.post<Article>(this.resourceUrl, article, {observe: 'response'});
    }

    update(article: Article): Observable<HttpResponse<Article>> {
        return this.http.put<Article>(this.resourceUrl, article, {observe: 'response'});
    }

    find(id: string): Observable<HttpResponse<Article>> {
        return this.http.get<Article>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    query(req?: any): Observable<any> {
        const options = createRequestOption(req);
        return this.http.get<Array<Article>>(this.resourceUrl, {params: options, observe: 'response'});
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }
}
