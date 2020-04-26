import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {createRequestOption} from "../util/request-util";
import {EnvironmentService} from "./environment.service";
import {Reservation} from "../domain/reservation";
import {TrainingModel} from "../domain/training-model";

@Injectable({providedIn: 'root'})
export class TrainingService {
  public resourceUrl;
  readonly RESOURCE_ENDPOINT = '/trainings';

  constructor(
    protected http: HttpClient,
    private environmentService: EnvironmentService) {
    this.resourceUrl = environmentService.apiUrl + this.RESOURCE_ENDPOINT;
  }

  subscribe(trainingId: number): Observable<HttpResponse<TrainingModel>> {
    return this.http.post<TrainingModel>(`${this.resourceUrl}/${trainingId}/subscribe`, null, {observe: 'response'});
  }

  unsubscribe(trainingId: number): Observable<HttpResponse<TrainingModel>> {
    return this.http.post<TrainingModel>(`${this.resourceUrl}/${trainingId}/unsubscribe`, null, {observe: 'response'});
  }

  create(trainingModel: TrainingModel): Observable<HttpResponse<TrainingModel>> {
    return this.http.post<TrainingModel>(this.resourceUrl, trainingModel, {observe: 'response'});
  }

  update(trainingModel: TrainingModel): Observable<HttpResponse<TrainingModel>> {
    return this.http.put<TrainingModel>(this.resourceUrl, trainingModel, {observe: 'response'});
  }

  find(id: number): Observable<HttpResponse<TrainingModel>> {
    return this.http.get<TrainingModel>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  findAllByUser(userId: number): Observable<HttpResponse<TrainingModel[]>> {
    return this.http.get<TrainingModel[]>(`${this.environmentService.apiUrl}/user/${userId}${this.RESOURCE_ENDPOINT}`, {observe: 'response'});
  }

  findAll(): Observable<HttpResponse<TrainingModel[]>> {
    return this.http.get<TrainingModel[]>(`${this.resourceUrl}`, {observe: 'response'});
  }

  query(req?: any): Observable<HttpResponse<TrainingModel[]>> {
    const options = createRequestOption(req);
    return this.http.get<TrainingModel[]>(this.resourceUrl, {params: options, observe: 'response'});
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }
}