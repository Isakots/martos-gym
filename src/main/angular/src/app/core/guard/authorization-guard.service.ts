import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {AccountService} from "../../shared/service/account.service";

@Injectable({providedIn: 'root'})
export class AuthorizationGuard implements CanActivate {
  constructor(
    private router: Router,
    private accountService: AccountService
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
    const authority = route.data['authority'];
    // We need to call the checkLogin / and so the accountService.identity() function, to ensure,
    // that the client has a principal too, if they already logged in by the server.
    return this.checkLogin(authority);
  }

  checkLogin(authority: string): Promise<boolean> {
    return this.accountService.identity().then(account => {
      if (account) {
        if (account.authorities.includes(authority)) {
          return true;
        } else {
          this.router.navigate(['error']);
          return false;
        }
      }
      this.router.navigate(['login']);
      return false;
    });
  }
}
