import { Component, OnDestroy } from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  Validators,
  NG_VALUE_ACCESSOR,
  NG_VALIDATORS,
  ControlValueAccessor,
  FormControl,
  ValidationErrors,
  Validator,
} from "@angular/forms";
import { Subscription } from "rxjs";
import { ErrorStateMatcher } from "@angular/material/core";

export class ConfirmValidParentMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null): boolean {
    return control.touched && control.parent.invalid;
  }
}

@Component({
  selector: "app-password-form",
  templateUrl: "./password-form.component.html",
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: PasswordFormComponent,
      multi: true,
    },
    {
      provide: NG_VALIDATORS,
      useExisting: PasswordFormComponent,
      multi: true,
    },
  ],
})
export class PasswordFormComponent
  implements ControlValueAccessor, Validator, OnDestroy {
  passwordForm = this.fb.group(
    {
      password: [
        "",
        [
          Validators.required,
          Validators.pattern(
            /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,32}$/
          ),
        ],
      ],
      confirmPassword: [""],
    },
    { validator: this.checkPasswords }
  );
  onChange: any = () => {};
  onTouched: any = () => {};
  subscription: Subscription;
  confirmValidParentMatcher = new ConfirmValidParentMatcher();

  constructor(private fb: FormBuilder) {
    this.subscription = this.passwordForm.valueChanges.subscribe((value) => {
      this.onChange(value);
      this.onTouched();
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  checkPasswords(group: FormGroup): ValidationErrors | null {
    let password = group.get("password").value;
    let confirmPassword = group.get("confirmPassword").value;

    return password === confirmPassword ? null : { missMatch: true };
  }

  writeValue(_: any): void {}

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  validate(_: FormControl): ValidationErrors | null {
    return this.passwordForm.valid ? null : { valid: true };
  }
}
