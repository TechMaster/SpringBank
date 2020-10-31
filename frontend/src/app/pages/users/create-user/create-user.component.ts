import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styles: [':host {width: 100%; max-width: 500px; margin: 30px 0;}'],
})
export class CreateUserComponent {
  createUserForm = this.fb.group({
    profile: [],
    password: [],
  });

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {}

  createUser() {
    const formValues = this.createUserForm.value;
    this.userService
      .createUser({
        ...formValues.profile,
        ...formValues.password,
      })
      .subscribe(
        () => this.router.navigate(['/users']),
        () => alert('Cannot create user at this time!')
      );
  }
}
