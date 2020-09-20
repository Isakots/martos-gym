import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable, of} from 'rxjs';
import {filter, map} from 'rxjs/operators';
import {HttpResponse} from '@angular/common/http';
import {Reservation} from '../shared/domain/reservation';
import {ReservationService} from '../shared/service/reservation.service';

@Injectable({providedIn: 'root'})
export class ReservationResolve implements Resolve<Reservation[]> {
  constructor(private service: ReservationService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Reservation[]> {
    const id = route.params['id'];
    if (id) {
      return this.service.findAllByToolId(id).pipe(
        filter((response: HttpResponse<Reservation[]>) => response.ok),
        map((reservation: HttpResponse<Reservation[]>) => reservation.body)
      );
    }
    return of([]);
  }
}
