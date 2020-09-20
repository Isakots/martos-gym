import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {createRequestOption} from '../util/request-util';
import {EnvironmentService} from './environment.service';
import {Reservation} from '../domain/reservation';

@Injectable({providedIn: 'root'})
export class ReservationService {
  public resourceUrl;
  readonly RESOURCE_ENDPOINT = '/reservations';

  constructor(
    protected http: HttpClient,
    private environmentService: EnvironmentService) {
    this.resourceUrl = environmentService.apiUrl + this.RESOURCE_ENDPOINT;
  }

  create(reservation: Reservation): Observable<HttpResponse<Reservation>> {
    return this.http.post<Reservation>(this.resourceUrl, reservation, {observe: 'response'});
  }

  update(reservation: Reservation): Observable<HttpResponse<Reservation>> {
    return this.http.put<Reservation>(this.resourceUrl, reservation, {observe: 'response'});
  }

  find(id: number): Observable<HttpResponse<Reservation>> {
    return this.http.get<Reservation>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  findAllByUser(userId: number): Observable<HttpResponse<Reservation[]>> {
    return this.http.get<Reservation[]>(
      `${this.environmentService.apiUrl}/user/${userId}${this.RESOURCE_ENDPOINT}`,
      {observe: 'response'}
    );
  }

  findAllByToolId(toolId: number): Observable<HttpResponse<Reservation[]>> {
    return this.http.get<Reservation[]>(
      `${this.environmentService.apiUrl}/tools/${toolId}${this.RESOURCE_ENDPOINT}`,
      {observe: 'response'}
    );
  }

  findAll(): Observable<HttpResponse<Reservation[]>> {
    return this.http.get<Reservation[]>(`${this.resourceUrl}`, {observe: 'response'});
  }

  query(req?: any): Observable<HttpResponse<Reservation[]>> {
    const options = createRequestOption(req);
    return this.http.get<Reservation[]>(this.resourceUrl, {params: options, observe: 'response'});
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }
}
