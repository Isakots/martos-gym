import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {EnvironmentService} from "./environment.service";
import {ManagedUser} from "../domain/managed-user";

@Injectable({providedIn: 'root'})
export class ManagedUserService {
  public resourceUrl;
  readonly RESOURCE_ENDPOINT = '/managed-users';

  constructor(
    protected http: HttpClient,
    private environmentService: EnvironmentService) {
    this.resourceUrl = environmentService.apiUrl + this.RESOURCE_ENDPOINT;
  }

  find(id: number): Observable<HttpResponse<ManagedUser>> {
    return this.http.get<ManagedUser>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  findAll(): Observable<HttpResponse<ManagedUser[]>> {
    return this.http.get<ManagedUser[]>(`${this.resourceUrl}`, {observe: 'response'});
  }

  updateOne(managedUser: ManagedUser): Observable<HttpResponse<ManagedUser[]>> {
    return this.http.put<ManagedUser[]>(`${this.resourceUrl}/${managedUser.id}`, managedUser, {observe: 'response'});
  }

  updateAll(managedUsers: ManagedUser[]): Observable<HttpResponse<ManagedUser[]>> {
    return this.http.put<ManagedUser[]>(this.resourceUrl, managedUsers, {observe: 'response'});
  }

}
