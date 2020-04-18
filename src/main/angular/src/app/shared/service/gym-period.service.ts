import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {EnvironmentService} from "./environment.service";
import {GymPeriod} from "../domain/gym-period";

@Injectable({providedIn: 'root'})
export class GymPeriodService {
  public resourceUrl;
  readonly RESOURCE_ENDPOINT = '/gym-periods';

  constructor(
    protected http: HttpClient,
    private environmentService: EnvironmentService) {
    this.resourceUrl = environmentService.apiUrl + this.RESOURCE_ENDPOINT;
  }

  findAll(): Observable<HttpResponse<GymPeriod[]>> {
    return this.http.get<GymPeriod[]>(`${this.resourceUrl}`, {observe: 'response'});
  }

  create(period: GymPeriod): Observable<HttpResponse<GymPeriod>> {
    return this.http.post<GymPeriod>(this.resourceUrl, period, {observe: 'response'});
  }

  patch(): Observable<HttpResponse<GymPeriod[]>> {
    return this.http.patch<GymPeriod[]>(`${this.resourceUrl}`, null, {observe: 'response'});
  }

}
