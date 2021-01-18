import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProfileService } from '../../core/services/profile.service';
import { AccountModel } from '../../shared/interfaces';

@Injectable({providedIn: 'root'})
export class ProfileResolver implements Resolve<AccountModel> {
    constructor(private readonly service: ProfileService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AccountModel> {
        return this.service.get().pipe(
            filter((response: HttpResponse<AccountModel>) => response.ok),
            map((model: HttpResponse<AccountModel>) => model.body as AccountModel)
        );
    }
}
