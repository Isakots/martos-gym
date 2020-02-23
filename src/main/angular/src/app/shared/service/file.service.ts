import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {EnvironmentService} from "./environment.service";
import {Injectable} from "@angular/core";


@Injectable({
  providedIn: 'root'
})
export class FileService {
  apiUrl: string;
  uploadUrl: string
  downloadUrl: string;

  constructor(
    private environmentService: EnvironmentService,
    private http: HttpClient
  ) {
    this.apiUrl = this.environmentService.apiUrl;
    this.uploadUrl = this.apiUrl + '/upload';
    this.downloadUrl = this.apiUrl + '/download';
  }

  uploadImage(file: File): Observable<HttpEvent<{}>> {
    const formdata: FormData = new FormData();
    formdata.append('file', file);
    const req = new HttpRequest('POST', this.uploadUrl, formdata, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.http.request(req);
  }

  getImage(): Observable<HttpEvent<any>> {
    const req = new HttpRequest('GET', this.downloadUrl);
    return this.http.request(req);
  }
}
