import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { AccountService } from '../../core/services/account.service';
import { UserNotificationService } from '../../core/services/user-notification.service';
import { matchValidation } from '../../core/validator/match-validator';
import { requiredValidationConditionally } from '../../core/validator/required-validator';

@Component({
    selector: 'app-sign-up',
    templateUrl: './sign-up.component.html'
})
export class SignUpComponent implements OnInit {
    isFormSubmitted = false;
    registerForm: any;

    constructor(private readonly formBuilder: FormBuilder,
                private readonly accountService: AccountService,
                private readonly router: Router,
                private readonly userNotificationService: UserNotificationService,
                private readonly translate: TranslateService) {
    }

    ngOnInit(): void {
        this.initFormGroup();
    }

    submitRegistration(): void {
        this.isFormSubmitted = true;
        this.registerForm.updateValueAndValidity();
        if (this.registerForm.invalid) {
            return;
        }
        const subscriptions = [];
        if (this.registerForm.controls.subOnNewArticles.value) {
            subscriptions.push('ON_NEW_ARTICLES');
        }
        if (this.registerForm.controls.subOnNewTrainings.value) {
            subscriptions.push('ON_NEW_TRAININGS');
        }
        if (this.registerForm.controls.subOnSubscribedTrainings.value) {
            subscriptions.push('ON_SUBSCRIBED_TRAININGS');
        }
        const userData = {
            firstName: this.registerForm.controls.firstName.value,
            lastName: this.registerForm.controls.lastName.value,
            password: this.registerForm.controls.password.value,
            email: this.registerForm.controls.email.value,
            studentStatus: this.registerForm.controls.studentStatus.value,
            institution: this.registerForm.controls.institution.value,
            faculty: this.registerForm.controls.faculty.value,
            collegian: this.registerForm.controls.collegian.value,
            roomNumber: this.registerForm.controls.roomNumber.value,
            subscriptions
        };
        this.accountService.signup(userData).subscribe(
            () => this.handleSuccess(),
            () => this.handleError()
        );
    }

    showValidationMessage(formControl: AbstractControl): boolean {
        return (formControl.invalid && (formControl.dirty || formControl.touched)) || (formControl.invalid && this.isFormSubmitted);
    }

    isStudentStateChecked(): boolean {
        return this.registerForm.controls.studentStatus.value;
    }

    isCollegianChecked(): boolean {
        return this.registerForm.controls.collegian.value;
    }

    private initFormGroup(): void {
        const formGroupControlsConfig = {
            firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
            lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
            email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
            confirmEmail: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
            password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(50)]],
            confirmPassword: ['', []], // matching validation only
            studentStatus: [false],
            institution: ['', []],
            faculty: ['', []],
            collegian: [false],
            roomNumber: ['', []],
            subOnNewArticles: [false],
            subOnNewTrainings: [false],
            subOnSubscribedTrainings: [false]
        };

        this.registerForm = this.formBuilder.group(formGroupControlsConfig,
            {
                validators: [
                    matchValidation('password', 'confirmPassword'),
                    matchValidation('email', 'confirmEmail'),
                    requiredValidationConditionally('studentStatus', 'institution'),
                    requiredValidationConditionally('studentStatus', 'faculty'),
                    requiredValidationConditionally('collegian', 'roomNumber')
                ]
            });
    }

    private handleSuccess(): void {
        this.userNotificationService.notifyUser(this.translate.instant('SIGN_UP.SUCCESSFUL_REGISTRATION'), false);
        setTimeout(() => {
            this.router.navigate(['/']);
        }, 1500);
    }

    private handleError(): void {
        this.userNotificationService.notifyUser(this.translate.instant('SIGN_UP.UNSUCCESSFUL_REGISTRATION'), true);
    }

}

