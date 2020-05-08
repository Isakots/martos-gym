import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {EnvironmentService} from "./environment.service";
import {EmailRequestModel} from "../domain/email-request-model";

@Injectable({providedIn: 'root'})
export class SendEmailService {
  public resourceUrl;
  readonly RESOURCE_ENDPOINT = '/users/mail';

  constructor(
    protected http: HttpClient,
    private environmentService: EnvironmentService) {
    this.resourceUrl = environmentService.apiUrl + this.RESOURCE_ENDPOINT;
  }

  send(emailRequestModel: EmailRequestModel): Observable<HttpResponse<any>> {
    return this.http.post<any>(this.resourceUrl, emailRequestModel, {observe: 'response'});
  }


}
