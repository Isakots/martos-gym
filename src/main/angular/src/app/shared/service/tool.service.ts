import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {createRequestOption} from '../util/request-util';
import {EnvironmentService} from './environment.service';
import {Tool} from '../domain/tool';

@Injectable({providedIn: 'root'})
export class ToolService {
  public resourceUrl;

  constructor(
    protected http: HttpClient,
    private environmentService: EnvironmentService) {
    this.resourceUrl = environmentService.apiUrl + '/tools';
  }

  create(tool: Tool): Observable<HttpResponse<Tool>> {
    return this.http.post<Tool>(this.resourceUrl, tool, {observe: 'response'});
  }

  update(tool: Tool): Observable<HttpResponse<Tool>> {
    return this.http.put<Tool>(this.resourceUrl, tool, {observe: 'response'});
  }

  find(id: string): Observable<HttpResponse<Tool>> {
    return this.http.get<Tool>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  findAll(): Observable<HttpResponse<Tool[]>> {
    return this.http.get<Tool[]>(`${this.resourceUrl}`, {observe: 'response'});
  }

  query(req?: any): Observable<HttpResponse<Tool[]>> {
    const options = createRequestOption(req);
    return this.http.get<Tool[]>(this.resourceUrl, {params: options, observe: 'response'});
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }
}
