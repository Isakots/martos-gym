import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {filter, map} from 'rxjs/operators';
import {HttpResponse} from '@angular/common/http';
import {AccountService} from '../../shared/service/account.service';
import {AccountModel} from '../../shared/domain/account-model';

@Injectable({providedIn: 'root'})
export class ProfileResolver implements Resolve<AccountModel> {
  constructor(private service: AccountService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AccountModel> {
    return this.service.get().pipe(
      filter((response: HttpResponse<AccountModel>) => response.ok),
      map((model: HttpResponse<AccountModel>) => model.body)
    );
  }
}
