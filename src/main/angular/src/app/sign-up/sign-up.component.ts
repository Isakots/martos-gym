import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../shared/service/account.service";
import {Router} from "@angular/router";
import {matchValidation} from "../core/validator/match-validator";
import {UserNotificationService} from "../shared/service/user-notification.service";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html'
})
export class SignUpComponent implements OnInit {
  triedToRegister: boolean = false;
  success: boolean;
  registerForm: FormGroup;

  constructor(
    private _formBuilder: FormBuilder,
    private accountService: AccountService,
    private _router: Router,
    private _userNotificationService: UserNotificationService) {
  }

  ngOnInit() {
    this.success = false;
    this._initFormGroup();
  }

  private _initFormGroup() {
    const formGroupControlsConfig = {
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
      lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
      email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
      confirmEmail: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(50)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(50)]],
      studentStatus: [false],
      institution: ['', [Validators.required, Validators.maxLength(4)]],
      faculty: ['', [Validators.required, Validators.maxLength(4)]],
      collegian: [false],
      roomNumber: [''],
      subOnNewArticles: [false],
      subOnNewTrainings: [false],
      subOnSubscribedTrainings: [false]
    };

    this.registerForm = this._formBuilder.group(formGroupControlsConfig,
      {
        validators: [
          matchValidation('password', 'confirmPassword'),
          matchValidation('email', 'confirmEmail')
        ]
      });
  }

  register() {
    this.triedToRegister = true;
    this.registerForm.updateValueAndValidity();
    if (this.registerForm.invalid) {
      return;
    }
    let subscriptions = [];
    if( this.registerForm.controls.subOnNewArticles.value) {
      subscriptions.push('ON_NEW_ARTICLES');
    }
    if( this.registerForm.controls.subOnNewTrainings.value) {
      subscriptions.push('ON_NEW_TRAININGS');
    }
    if( this.registerForm.controls.subOnSubscribedTrainings.value) {
      subscriptions.push('ON_SUBSCRIBED_TRAININGS');
    }
    let userData = {
      firstName: this.registerForm.controls.firstName.value,
      lastName: this.registerForm.controls.lastName.value,
      studentStatus: this.registerForm.controls.studentStatus.value,
      institution: this.registerForm.controls.institution.value,
      faculty: this.registerForm.controls.faculty.value,
      collegian: this.registerForm.controls.collegian.value,
      roomNumber: this.registerForm.controls.roomNumber.value,
      subscriptions: subscriptions
    };
    this.accountService.registration(userData).subscribe(
      () => {
        this._userNotificationService.notifyUser("Sikeres regisztráció!", false);
        setTimeout(() => {
          this._router.navigate(['/']);
        }, 1500)
      },
      () => {
        this._userNotificationService.notifyUser("Sikertelen regisztráció!", true);
      }
    );
  }

  showValidationMessage(formControl: AbstractControl) {
    return (formControl.invalid && (formControl.dirty || formControl.touched)) || (formControl.invalid && this.triedToRegister);
  }

  isStudentStateChecked() {
    return this.registerForm.controls.studentStatus.value;
  }

  isCollegianChecked() {
    return this.registerForm.controls.collegian.value;
  }

}

