import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {EnvironmentService} from "./environment.service";
import {User} from "../domain/user";
import {JWT_TOKEN_KEY, USER_DATA_KEY} from "../constants";
import {LoginResponse} from "../domain/interfaces";
import {Router} from "@angular/router";
import {AccountService} from "./account.service";

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

  authenticate(loginDTO: any) {
    return this.httpClient.post<LoginResponse>(this.environmentService.apiUrl + this.AUTH, loginDTO).subscribe(
      response => {
        let tokenStr = response.token;
        sessionStorage.setItem(JWT_TOKEN_KEY, tokenStr);
        this.router.navigate(['/'])
      })
  }

  getProfile() {
    return this.httpClient.get<User>(this.environmentService.apiUrl + '/profile').subscribe(
      response => {
        console.log('User: ', response);
      })
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
