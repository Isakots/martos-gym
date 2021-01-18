import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { ProfileService } from '../../core/services/profile.service';
import { UserNotificationService } from '../../core/services/user-notification.service';
import { matchValidation } from '../../core/validator/match-validator';

@Component({
    selector: 'app-change-password',
    templateUrl: './change-password.component.html'
})
export class ChangePasswordComponent implements OnInit {
    passwordForm!: FormGroup;

    constructor(private readonly formBuilder: FormBuilder,
                private readonly profileService: ProfileService,
                private readonly translate: TranslateService,
                private readonly userNotificationService: UserNotificationService) {
    }

    ngOnInit(): void {
        this.initFormGroup();
    }

    changePassword(): void {
        this.passwordForm.updateValueAndValidity();
        if (this.passwordForm.invalid) {
            return;
        }
        const passwordDto = {
            currentPassword: this.passwordForm.controls.currentPassword.value,
            newPassword: this.passwordForm.controls.newPassword.value
        };
        this.profileService.changePassword(passwordDto).subscribe(
            () => this.userNotificationService.notifyUser(this.translate.instant('CHANGE_PASSWORD.SUCCESSFUL_CHANGE'), false),
            () => this.userNotificationService.notifyUser(this.translate.instant('CHANGE_PASSWORD.FAILED_CHANGE'), true)
        );

    }

    private initFormGroup(): void {
        const formGroupControlsConfig = {
            currentPassword: ['', [Validators.required]],
            newPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(50)]],
            confirmPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(50)]]
        };

        this.passwordForm = this.formBuilder.group(formGroupControlsConfig,
            {
                validators: [
                    matchValidation('newPassword', 'confirmPassword')
                ]
            });
    }
}
