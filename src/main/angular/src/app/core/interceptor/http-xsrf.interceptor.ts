import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpXsrfTokenExtractor} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class HttpXsrfInterceptor implements HttpInterceptor {

  constructor(private readonly tokenExtractor: HttpXsrfTokenExtractor) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const headerName = 'X-XSRF-TOKEN';
    const token = this.tokenExtractor.getToken();
    let request = req;
    if (null !== token && !req.headers.has(headerName)) {
      request = req.clone({headers: req.headers.set(headerName, token)});
    }
    return next.handle(request);
  }
}
