import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable, of} from 'rxjs';
import {filter, map} from 'rxjs/operators';
import {HttpResponse} from '@angular/common/http';
import {TrainingModel} from '../shared/domain/training-model';
import {TrainingService} from '../shared/service/training.service';

@Injectable({providedIn: 'root'})
export class TrainingResolver implements Resolve<TrainingModel> {
  constructor(private service: TrainingService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TrainingModel> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TrainingModel>) => response.ok),
        map((managedUsers: HttpResponse<TrainingModel>) => managedUsers.body)
      );
    }
    return of(new TrainingModel());
  }

}
