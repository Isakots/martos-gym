import {Router} from '@angular/router';
import {Location} from '@angular/common';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EnvironmentService {

  contextRoot: string;
  apiUrl: string;

  constructor(private router: Router, private location: Location) {
    this.contextRoot = window.location.origin + (this.location as any)._platformStrategy._baseHref;
    this.apiUrl = this.contextRoot + 'api';
  }

}
