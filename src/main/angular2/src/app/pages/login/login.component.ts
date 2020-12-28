import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from '../../core/services/account.service';
import { StateStorageService } from '../../core/services/state-storage.service';
import { UserNotificationService } from '../../core/services/user-notification.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
    loginForm: any;

    constructor(private readonly formBuilder: FormBuilder,
                private readonly stateStorageService: StateStorageService,
                private readonly router: Router,
                private readonly accountService: AccountService,
                private readonly userNotificationService: UserNotificationService) {
    }

    ngOnInit(): void {
        this.loginForm = this.formBuilder.group({
            username: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]]
        });
    }

    login(): void {
        const loginDTO = this.loginForm.getRawValue();
        this.accountService.login(loginDTO).subscribe(
            () => this.handleSuccess(),
            () => this.handleError());
    }

    private handleSuccess(): void {
        const redirect = this.stateStorageService.getUrl();
        if (redirect) {
            this.stateStorageService.removeUrl();
            this.userNotificationService.notifyUser('Sikeres authentikáció!', true);
            this.router.navigateByUrl(redirect);
        } else {
            this.router.navigateByUrl('/');
        }
    }

    private handleError(): void {
        this.userNotificationService.notifyUser('Sikertelen authentikáció!', true);
    }
}
