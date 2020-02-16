import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {EnvironmentService} from "./environment.service";
import {JwtResponse} from "../domain/jwt.response";
import {User} from "../domain/user";
import {JWT_TOKEN_KEY} from "../constants";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  readonly AUTH = '/auth';

  constructor(
    private httpClient: HttpClient, private environmentService: EnvironmentService) {
  }

  authenticate(username, password) {
    return this.httpClient.post<JwtResponse>(this.environmentService.apiUrl + this.AUTH, {
      username,
      password
    }).subscribe(
      response => {
        let tokenStr = response.token;
        sessionStorage.setItem(JWT_TOKEN_KEY, tokenStr);
      })
  }

  getProfile() {
    return this.httpClient.get<User>(this.environmentService.apiUrl + '/profile').subscribe(
      response => {
        console.log('User: ', response);
      })
  }


  isUserLoggedIn() {
    // TODO
    let user = sessionStorage.getItem(JWT_TOKEN_KEY);
    return !(user === null)
  }

  logOut() {
    // TODO
    sessionStorage.removeItem(JWT_TOKEN_KEY)
  }

  update(param: any) {
    return this.httpClient.put<User>(this.environmentService.apiUrl + '/profile', param).subscribe(
      response => {
        console.log('Updated user: ', response);
      })
  }
}
