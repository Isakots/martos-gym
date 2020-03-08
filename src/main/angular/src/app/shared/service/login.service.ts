import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {EnvironmentService} from "./environment.service";
import {User} from "../domain/user";
import {JWT_TOKEN_KEY, USER_DATA_KEY} from "../constants";
import {LoginResponse} from "../domain/interfaces";
import {Router} from "@angular/router";
import {AccountService} from "./account.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  readonly AUTH = '/auth';

  constructor(
    private httpClient: HttpClient,
    private environmentService: EnvironmentService,
    private router: Router,
    private accountService: AccountService) {
  }

  authenticate(loginDTO: any) : Observable<any> {
    return this.httpClient.post<LoginResponse>(this.environmentService.apiUrl + this.AUTH, loginDTO);
  }

  isUserLoggedIn() {
    return sessionStorage.getItem(JWT_TOKEN_KEY) !== null;
  }

  logOut() {
    this.accountService.logOut();
    sessionStorage.removeItem(JWT_TOKEN_KEY);
    this.router.navigate(['/'])
  }

}
