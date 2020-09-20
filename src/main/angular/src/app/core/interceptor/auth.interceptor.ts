import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {EnvironmentService} from '../../shared/service/environment.service';
import {JWT_TOKEN_KEY} from '../../shared/constants';


@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private environmentService: EnvironmentService
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!request || !request.url || (/^http/.test(request.url) &&
      !(this.environmentService.apiUrl && request.url.startsWith(this.environmentService.apiUrl)))) {
      return next.handle(request);
    }


    const token = sessionStorage.getItem(JWT_TOKEN_KEY);
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
