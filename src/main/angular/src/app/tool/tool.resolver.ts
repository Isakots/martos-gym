import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Observable, of} from "rxjs";
import {filter, map} from "rxjs/operators";
import {HttpResponse} from "@angular/common/http";
import {ToolService} from "../shared/service/tool.service";
import {Tool} from "../shared/domain/tool";

@Injectable({providedIn: 'root'})
export class ToolResolve implements Resolve<Tool> {
  constructor(private service: ToolService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Tool> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Tool>) => response.ok),
        map((tool: HttpResponse<Tool>) => tool.body)
      );
    }
    return of(new Tool());
  }
}
