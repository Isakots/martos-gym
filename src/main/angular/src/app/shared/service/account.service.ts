import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {EnvironmentService} from "./environment.service";
import {Observable, Subject} from "rxjs";
import {User} from "../domain/user";
import {Authority} from "../domain/interfaces";

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private userIdentity: any;
  private authenticated = false;
  private authenticationState = new Subject<any>();

  readonly REGISTER = '/register';

  constructor(
    private http: HttpClient, private environmentService: EnvironmentService) {
  }

  registration(userData: any): Observable<any> {
    return this.http.post<HttpResponse<any>>(this.environmentService.apiUrl + this.REGISTER, userData);
  }

  fetch(): Observable<HttpResponse<User>> {
    return this.http.get<User>(this.environmentService.apiUrl + '/identity', { observe: 'response' });
  }

  updateProfile(param: any) {
    return this.http.put<User>(this.environmentService.apiUrl + '/profile', param).subscribe(
      response => {
        console.log('Updated user: ', response);
      })
  }

  identity(force?: boolean): Promise<User> {
    if (force) {
      this.userIdentity = undefined;
    }

    // check and see if we have retrieved the userIdentity data from the server.
    // if we have, reuse it by immediately resolving
    if (this.userIdentity) {
      return Promise.resolve(this.userIdentity);
    }

    // retrieve the userIdentity data from the server, update the identity object, and then resolve.
    return this.fetch()
      .toPromise()
      .then(response => {
        const account: User = response.body;
        if (account) {
          this.userIdentity = account;
          this.authenticated = true;
        } else {
          this.userIdentity = null;
          this.authenticated = false;
        }
        this.authenticationState.next(this.userIdentity);
        return this.userIdentity;
      })
      .catch(err => {
        this.userIdentity = null;
        this.authenticated = false;
        this.authenticationState.next(this.userIdentity);
        return null;
      });
  }

  hasAuthority(authority: string): Promise<boolean> {
    if (!this.authenticated) {
      return Promise.resolve(false);
    }

    return this.identity().then(
      identity => {
        return Promise.resolve(identity.authorities && identity.authorities.includes(new Authority(authority)));
      },
      () => {
        return Promise.resolve(false);
      }
    );
  }
}
