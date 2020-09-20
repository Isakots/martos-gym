import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {matchValidation} from '../../core/validator/match-validator';
import {AccountService} from '../../shared/service/account.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html'
})
export class ChangePasswordComponent implements OnInit {
  private triedToSave: boolean;
  passwordForm: FormGroup;
  success: boolean;
  error: boolean;

  constructor(private formBuilder: FormBuilder, private accountService: AccountService) {
  }

  ngOnInit() {
    this.triedToSave = false;
    this.error = false;
    this.success = false;
    this._initFormGroup();
  }

  private _initFormGroup() {
    const formGroupControlsConfig = {
      currentPassword: ['', [Validators.required]],
      newPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(50)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(50)]]
    };

    this.passwordForm = this.formBuilder.group(formGroupControlsConfig,
      {
        validators: [
          matchValidation('newPassword', 'confirmPassword'),
        ]
      });
  }

  changePassword() {
    this.triedToSave = true;
    this.error = false;
    this.success = false;
    this.passwordForm.updateValueAndValidity();
    if (this.passwordForm.invalid) {
      return;
    }
    const passwordDto = {
      currentPassword: this.passwordForm.controls.currentPassword.value,
      newPassword: this.passwordForm.controls.newPassword.value
    };
    this.accountService.changePassword(passwordDto).subscribe(
      () => {
        this.success = true;
      },
      () => {
        this.error = true;
      }
    );

  }
}
