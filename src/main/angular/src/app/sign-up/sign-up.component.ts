import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AccountService} from '../shared/service/account.service';
import {Router} from '@angular/router';
import {matchValidation} from '../core/validator/match-validator';
import {UserNotificationService} from '../shared/service/user-notification.service';
import {requiredValidationConditionally} from '../core/validator/required-validator';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html'
})
export class SignUpComponent implements OnInit {
  triedToRegister: boolean = false;
  success: boolean;
  registerForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService,
    private router: Router,
    private userNotificationService: UserNotificationService) {
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
          requiredValidationConditionally('studentStatus','institution'),
          requiredValidationConditionally('studentStatus','faculty'),
          requiredValidationConditionally('collegian','roomNumber')
        ]
      });
  }

  register() {
    this.triedToRegister = true;
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
      subscriptions: subscriptions
    };
    this.accountService.registration(userData).subscribe(
      () => {
        this.userNotificationService.notifyUser('Sikeres regisztráció!', false);
        setTimeout(() => {
          this.router.navigate(['/']);
        }, 1500);
      },
      () => {
        this.userNotificationService.notifyUser('Sikertelen regisztráció!', true);
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

