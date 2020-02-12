import {Component, OnInit} from '@angular/core';
import {EnvironmentService} from "../shared/service/environment.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private environmentService: EnvironmentService) {
  }

  ngOnInit() {
  }

}
