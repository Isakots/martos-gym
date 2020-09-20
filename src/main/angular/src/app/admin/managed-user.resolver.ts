import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable, of} from 'rxjs';
import {catchError, filter, map} from 'rxjs/operators';
import {HttpResponse} from '@angular/common/http';
import {ManagedUser} from '../shared/domain/managed-user';
import {ManagedUserService} from '../shared/service/managed-user.service';

@Injectable({providedIn: 'root'})
export class ManagedUserResolver implements Resolve<ManagedUser[]> {
  constructor(private service: ManagedUserService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ManagedUser[]> {
    return this.service.findAll().pipe(
      filter((response: HttpResponse<ManagedUser[]>) => response.ok),
      map((managedUsers: HttpResponse<ManagedUser[]>) => managedUsers.body),
      catchError(() => {
        return of([]);
      })
    );
  }
}
