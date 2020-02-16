import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {EnvironmentService} from "./environment.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  readonly REGISTER = '/register';

  constructor(
    private httpClient: HttpClient, private environmentService: EnvironmentService) {
  }

  registration(userData: any): Observable<any> {
    return this.httpClient.post<HttpResponse<any>>(this.environmentService.apiUrl + this.REGISTER, userData);
  }
}
