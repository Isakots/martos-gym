import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../shared/service/account.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {
  triedToRegister: boolean = false;
  success: boolean;
  registerForm: FormGroup;

  constructor(
    private _formBuilder: FormBuilder,
    private accountService: AccountService,
    private _router: Router) {
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
      password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
      studentStatus: [false],
      institution: ['', [Validators.required, Validators.maxLength(4)]],
      faculty: ['', [Validators.required, Validators.maxLength(4)]],
      collegian: [false],
      roomNumber: ['']
    };

    this.registerForm = this._formBuilder.group(formGroupControlsConfig);
  }

  register() {
    this.triedToRegister = true;
    this.registerForm.updateValueAndValidity();
    if (this.registerForm.invalid) {
      return;
    }
    let userData = this.registerForm.getRawValue();
    this.accountService.registration(userData).subscribe(
      () => {
        // TODO notify User about registration
        setTimeout(() => {
          this._router.navigate(['/']);
        }, 1000)
      }
    );
  }

  showValidationMessage(formControl: AbstractControl) {
    return (formControl.invalid && (formControl.dirty || formControl.touched)) || (formControl.invalid && this.triedToRegister);
  }
}
