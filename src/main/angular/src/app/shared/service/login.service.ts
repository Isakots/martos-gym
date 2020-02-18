import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {EnvironmentService} from "./environment.service";
import {User} from "../domain/user";
import {JWT_TOKEN_KEY, USER_DATA_KEY} from "../constants";
import {LoginResponse} from "../domain/interfaces";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  readonly AUTH = '/auth';

  constructor(
    private httpClient: HttpClient,
    private environmentService: EnvironmentService,
    private router: Router) {
  }

  authenticate(loginDTO: any) {
    return this.httpClient.post<LoginResponse>(this.environmentService.apiUrl + this.AUTH, loginDTO).subscribe(
      response => {
        let tokenStr = response.token;
        let user = response.userWithRoles;
        sessionStorage.setItem(JWT_TOKEN_KEY, tokenStr);
        sessionStorage.setItem(USER_DATA_KEY, JSON.stringify(user))
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
    return sessionStorage.getItem(JWT_TOKEN_KEY) !== null && sessionStorage.getItem(USER_DATA_KEY) !== null;
  }

  logOut() {
    sessionStorage.removeItem(JWT_TOKEN_KEY);
    sessionStorage.removeItem(USER_DATA_KEY);
    this.router.navigate(['/'])
  }

}
