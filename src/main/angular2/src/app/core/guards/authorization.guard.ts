import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AccountService } from '../services/account.service';
import { StateStorageService } from '../services/state-storage.service';

@Injectable({
    providedIn: 'root'
})
export class AuthorizationGuard implements CanActivate {
    constructor(private readonly router: Router,
                private readonly accountService: AccountService,
                private readonly stateStorageService: StateStorageService) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
        const authority = route.data.authority;
        if (this.accountService.getAuthenticationState()) {
            if (this.accountService.hasRole(authority)) {
                return true;
            } else {
                this.router.navigate(['error']);
                return false;
            }
        }

        this.stateStorageService.storeUrl(state.url);
        this.router.navigate(['login']);
        return false;
    }

}
