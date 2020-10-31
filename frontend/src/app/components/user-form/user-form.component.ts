import { Component, OnDestroy } from "@angular/core";
import {
  FormBuilder,
  ControlValueAccessor,
  FormControl,
  ValidationErrors,
  Validators,
  NG_VALUE_ACCESSOR,
  NG_VALIDATORS,
  Validator,
} from "@angular/forms";
import { Subscription } from "rxjs";

@Component({
  selector: "app-user-form",
  templateUrl: "./user-form.component.html",
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: UserFormComponent,
      multi: true,
    },
    {
      provide: NG_VALIDATORS,
      useExisting: UserFormComponent,
      multi: true,
    },
  ],
})
export class UserFormComponent implements ControlValueAccessor, Validator, OnDestroy {
  userForm = this.fb.group({
    name: ["", [Validators.required]],
    email: ["", [Validators.email]],
    birthday: [""],
    phone: [""],
  });
  onChange: any = () => {};
  onTouched: any = () => {};
  subscription: Subscription;

  constructor(private fb: FormBuilder) {
    this.subscription = this.userForm.valueChanges.subscribe((value) => {
      this.onChange(value);
      this.onTouched();
    });
  }

  ngOnDestroy() {}

  writeValue(obj: any): void {
    if (obj) {
      this.userForm.patchValue(obj);
    }
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  validate(_: FormControl): ValidationErrors | null {
    return this.userForm.valid ? null : { valid: false };
  }
}
