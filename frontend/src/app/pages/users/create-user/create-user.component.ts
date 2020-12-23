import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styles: [':host {width: 100%; max-width: 500px; margin: 30px auto;}'],
})
export class CreateUserComponent {
  createUserForm = this.fb.group({
    profile: [],
    password: [],
  });

  constructor(
    private titleService: Title,
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Thêm User');
  }

  createUser() {
    const formValues = this.createUserForm.value;

    this.userService
      .createUser({
        enabled: true,
        ...formValues.profile,
        credentials: [
          {
            type: 'password',
            value: formValues.password.password,
            temporary: false,
          },
        ],
      })
      .subscribe(
        () => this.router.navigate(['/users']),
        () =>
          this._snackBar.open('Thêm User thất bại', 'Đóng', {
            duration: 2000,
            verticalPosition: 'top',
          })
      );
  }
}
