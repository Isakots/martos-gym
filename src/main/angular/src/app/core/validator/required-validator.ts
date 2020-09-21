import {FormGroup} from '@angular/forms';

export function requiredValidationConditionally(controlName: string, otherControlName: string) {
  return (formGroup: FormGroup) => {
    const conditionControl = formGroup.controls[controlName];
    const validatedControl = formGroup.controls[otherControlName];

    if (conditionControl.value && (validatedControl.value === null || validatedControl.value === '' || validatedControl.value === 0)) {
      validatedControl.setErrors({ required: true });
    } else {
      validatedControl.setErrors(null);
    }
  };
}

