import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {EnvironmentService} from "../../shared/service/environment.service";
import {LocalStorageService, SessionStorageService} from "ngx-webstorage";


@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private environmentService: EnvironmentService,
    private localStorage: LocalStorageService,
    private sessionStorage: SessionStorageService
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!request || !request.url || (/^http/.test(request.url) && !(this.environmentService.apiUrl && request.url.startsWith(this.environmentService.apiUrl)))) {
      return next.handle(request);
    }

    const token = this.localStorage.retrieve('authenticationToken') || this.sessionStorage.retrieve('authenticationToken');
    if (!!token) {
      request = request.clone({
        setHeaders: {
          Authorization: 'Bearer ' + token
        }
      });
    }
    return next.handle(request);
  }
}
