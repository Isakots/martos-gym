import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {EnvironmentService} from "./environment.service";
import {Observable, Subject} from "rxjs";
import {User} from "../domain/user";
import {UserWithRoles} from "../domain/interfaces";
import {AccountModel} from "../domain/account-model";

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

  fetch(): Observable<HttpResponse<UserWithRoles>> {
    return this.http.get<UserWithRoles>(this.environmentService.apiUrl + '/identity', {observe: 'response'});
  }

  get(): Observable<HttpResponse<AccountModel>> {
    return this.http.get<AccountModel>(this.environmentService.apiUrl + '/profile', {observe: 'response'});
  }

  updateProfile(param: any): Observable<HttpResponse<User>> {
    return this.http.put<User>(this.environmentService.apiUrl + '/profile', param, {observe: 'response'});
  }

  changePassword(param: any): Observable<any> {
    return this.http.post(this.environmentService.apiUrl + '/profile/change-password', param, {observe: 'response'});
  }

  identity(force?: boolean): Promise<UserWithRoles> {
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
        const account: UserWithRoles = response.body;
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
        return Promise.resolve(identity.authorities && identity.authorities.includes(authority));
      },
      () => {
        return Promise.resolve(false);
      }
    );
  }

  hasAnyAuthority(authorities: string[]): boolean {
    if (!this.authenticated || !this.userIdentity || !this.userIdentity.authorities) {
      return false;
    }

    for (let i = 0; i < authorities.length; i++) {
      if (this.userIdentity.authorities.includes(authorities[i])) {
        return true;
      }
    }

    return false;
  }

  isAuthenticated(): boolean {
    return this.authenticated;
  }

  getAuthenticationState(): Observable<any> {
    return this.authenticationState.asObservable();
  }

  logOut() {
    this.userIdentity = null;
    this.authenticated = false;
    this.authenticationState.next(this.userIdentity);
  }

}
