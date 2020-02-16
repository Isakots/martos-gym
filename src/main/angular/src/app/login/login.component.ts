import {Component, OnInit} from '@angular/core';
import {LoginService} from "../shared/service/login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private loginService: LoginService) {
  }

  ngOnInit() {
  }

  login() {
    this.loginService.authenticate('csokasi.marcell@gmail.com', '123');
  }

  profile() {
    this.loginService.getProfile();
  }

  update() {
    this.loginService.update({
        firstName: 'Cook',
        lastName: 'Pu'
      }
    )
  }
}
