import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AccountService} from '../../shared/service/account.service';
import {ActivatedRoute} from '@angular/router';
import {AccountModel} from '../../shared/domain/account-model';
import {GymPeriod} from '../../shared/domain/gym-period';
import {requiredValidationConditionally} from "../../core/validator/required-validator";
import {UserNotificationService} from "../../shared/service/user-notification.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  triedToSave = false;
  profileForm: FormGroup;
  success: boolean;

  constructor(
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private userNotificationService: UserNotificationService
  ) {
  }

  ngOnInit() {
    this.success = false;
    this._initFormGroup();
    this.activatedRoute.data.subscribe(({account}) => {
      this.updateForm(account);
    });
  }

  private _initFormGroup() {
    const formGroupControlsConfig = {
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
      lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
      email: [{value: '', disabled: true},
        [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
      studentStatus: [false],
      institution: ['', []],
      faculty: [''],
      collegian: [false],
      roomNumber: [''],
      subOnNewArticles: [false],
      subOnNewTrainings: [false],
      subOnSubscribedTrainings: [false]
    };

    this.profileForm = this.formBuilder.group(formGroupControlsConfig,
      {
        validators: [
          requiredValidationConditionally('studentStatus','institution'),
          requiredValidationConditionally('studentStatus','faculty'),
          requiredValidationConditionally('collegian','roomNumber')
        ]
      });
  }

  updateForm(account: AccountModel) {
    this.profileForm.patchValue({
      id: account.id,
      firstName: account.firstName,
      lastName: account.lastName,
      email: account.email,
      studentStatus: account.studentStatus,
      institution: account.institution,
      faculty: account.faculty,
      collegian: account.collegian,
      roomNumber: account.roomNumber,
      subOnNewArticles: account.subscriptions.includes('ON_NEW_ARTICLES'),
      subOnNewTrainings: account.subscriptions.includes('ON_NEW_TRAININGS'),
      subOnSubscribedTrainings: account.subscriptions.includes('ON_SUBSCRIBED_TRAININGS')
    });
  }

  save() {
    this.triedToSave = true;
    this.profileForm.updateValueAndValidity();
    if (this.profileForm.invalid) {
      return;
    }
    const subscriptions = [];
    if (this.profileForm.controls.subOnNewArticles.value) {
      subscriptions.push('ON_NEW_ARTICLES');
    }
    if (this.profileForm.controls.subOnNewTrainings.value) {
      subscriptions.push('ON_NEW_TRAININGS');
    }
    if (this.profileForm.controls.subOnSubscribedTrainings.value) {
      subscriptions.push('ON_SUBSCRIBED_TRAININGS');
    }
    const userData = {
      firstName: this.profileForm.controls.firstName.value,
      lastName: this.profileForm.controls.lastName.value,
      studentStatus: this.profileForm.controls.studentStatus.value,
      institution: this.profileForm.controls.institution.value,
      faculty: this.profileForm.controls.faculty.value,
      collegian: this.profileForm.controls.collegian.value,
      roomNumber: this.profileForm.controls.roomNumber.value,
      subscriptions: subscriptions
    };

    this.accountService.updateProfile(userData).subscribe(
      response => {
        if (response.status === 200) {
          this.success = true;
          this.userNotificationService.notifyUser("Mentés sikeres!", false);
        }
      }
    );
  }

  showValidationMessage(formControl: AbstractControl) {
    return (formControl.invalid && (formControl.dirty || formControl.touched)) || (formControl.invalid && this.triedToSave);
  }

  isStudentStateChecked() {
    return this.profileForm.controls.studentStatus.value;
  }

  isCollegianChecked() {
    return this.profileForm.controls.collegian.value;
  }

}
