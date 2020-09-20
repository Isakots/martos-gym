import {Component, OnInit} from '@angular/core';
import {LoginService} from '../shared/service/login.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {JWT_TOKEN_KEY} from '../shared/constants';
import {StateStorageService} from '../shared/service/state-storage.service';
import {Router} from '@angular/router';
import {AccountService} from '../shared/service/account.service';
import {UserNotificationService} from '../shared/service/user-notification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private stateStorageService: StateStorageService,
    private router: Router,
    private accountService: AccountService,
    private userNotificationService: UserNotificationService
    ) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]]
    });
  }

  login() {
    const loginDTO = this.loginForm.getRawValue();
    this.loginService.authenticate(loginDTO).subscribe(
      response => {
        const tokenStr = response.token;
        sessionStorage.setItem(JWT_TOKEN_KEY, tokenStr);
        // this call is necessary to update userIdentity.. TODO refactor
        this.accountService.identity();
        const redirect = this.stateStorageService.getUrl();
        if (redirect) {
          this.stateStorageService.storeUrl(null);
          this.router.navigateByUrl(redirect);
        } else {
          this.router.navigateByUrl('/');
        }
      },
      () => {
        this.userNotificationService.notifyUser('Bejelentkezés sikertelen!', true);
      });
  }
}
