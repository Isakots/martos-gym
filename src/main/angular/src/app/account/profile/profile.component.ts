import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../../shared/service/account.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AccountModel} from "../../shared/domain/account-model";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  triedToSave: boolean = false;
  profileForm: FormGroup;
  success: boolean;

  constructor(
    private accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    private _formBuilder: FormBuilder,
    private _router: Router
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
      institution: ['', [this._customRequiredValidator]],
      faculty: [''],
      collegian: [false],
      roomNumber: ['']
    };

    this.profileForm = this._formBuilder.group(formGroupControlsConfig);
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
      roomNumber: account.roomNumber
    });
  }

  save() {
    this.triedToSave = true;
    this.profileForm.updateValueAndValidity();
    if (this.profileForm.invalid) {
      return;
    }
    let userData = this.profileForm.getRawValue();
    this.accountService.updateProfile(userData).subscribe(
      response => {
        if (response.status == 200) {
          this.success = true;
        }
      }
    );
  }

  private _customRequiredValidator() {
    // TODO
    return "";
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
