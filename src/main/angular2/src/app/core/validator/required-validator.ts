import { FormGroup } from '@angular/forms';

// eslint-disable-next-line prefer-arrow/prefer-arrow-functions
export function requiredValidationConditionally(controlName: string, otherControlName: string) {
  return (formGroup: FormGroup) => {
    const conditionControl = formGroup.controls[controlName];
    const validatedControl = formGroup.controls[otherControlName];

    if (conditionControl.value && (null === validatedControl.value || '' === validatedControl.value || 0 === validatedControl.value)) {
      validatedControl.setErrors({ required: true });
    } else {
      validatedControl.setErrors(null);
    }
  };
}

