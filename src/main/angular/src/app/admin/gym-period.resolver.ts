import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Observable, of} from "rxjs";
import {catchError, filter, map} from "rxjs/operators";
import {HttpResponse} from "@angular/common/http";
import {GymPeriodService} from "../shared/service/gym-period.service";
import {GymPeriod} from "../shared/domain/gym-period";

@Injectable({providedIn: 'root'})
export class GymPeriodResolver implements Resolve<GymPeriod[]> {
  constructor(private service: GymPeriodService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<GymPeriod[]> {
    return this.service.findAll().pipe(
      filter((response: HttpResponse<GymPeriod[]>) => response.ok),
      map((managedUsers: HttpResponse<GymPeriod[]>) => managedUsers.body),
      catchError(() => {
        return of([])
      })
    );
  }
}
