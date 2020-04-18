import {Component, forwardRef, OnInit} from '@angular/core';
import {ControlValueAccessor, FormBuilder, FormControl, NG_VALUE_ACCESSOR} from '@angular/forms';
import {ManagedUser} from "../../../../shared/domain/managed-user";

@Component({
  selector: 'app-managed-user-control',
  templateUrl: './managed-user-control.component.html',
  styleUrls: ['./managed-user-control.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ManagedUserControlComponent),
      multi: true
    }
  ]
})
export class ManagedUserControlComponent implements OnInit, ControlValueAccessor {

  initialization : boolean = true;

  public model: ManagedUser;
  public ticketControl: FormControl;
  public roleControl: FormControl;

  onChange = (_: ManagedUser) => {
  };
  onTouched = () => {
  };

  constructor(private _formBuilder: FormBuilder) {
    this.ticketControl = this._formBuilder.control(false);
    this.roleControl = this._formBuilder.control('Felhasználó');
  }

  ngOnInit() {
    this.ticketControl.valueChanges.subscribe((value: boolean) => {
      this.model.hasTicketForActivePeriod = value;
      this.onChange(this.model);
    });

    this.roleControl.valueChanges.subscribe((value: string) => {
      if('Körtag' === value) {
        this.model.authorities = ['ROLE_USER','ROLE_MEMBER'];
      } else {
        this.model.authorities = ['ROLE_USER'];
      }
      this.onChange(this.model);
    });
  }

  registerOnChange(fn: (_: ManagedUser) => {}): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => {}): void {
    this.onTouched = fn;
  }

  writeValue(newValue: ManagedUser): void {
    this.model = newValue;
    if(this.initialization) {
      this.roleControl.setValue(this.getRole(this.model));
      this.initialization = false;
    }
    this.ticketControl.setValue(newValue.hasTicketForActivePeriod);
    this.roleControl.setValue(this.getRole(newValue))
  }

  public getRole(user: ManagedUser): string {
    if (user.authorities.includes('ROLE_MEMBER')) {
      return 'Körtag';
    } else {
      return 'Felhasználó';
    }
  }

}
