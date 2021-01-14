import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-user-credentials',
  templateUrl: 'user-credentials.component.html',
  styles: [':host {width: 100%; max-width: 500px; margin: 30px auto;}'],
})
export class UserCredentialsComponent implements OnInit {
  userId: string;

  userCredentialsForm = this.fb.group({
    clientRoles: [['customer']],
    password: [],
  });

  constructor(
    private titleService: Title,
    private fb: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.titleService.setTitle('Thiết lập tài khoản');

    const id = this.route.snapshot.paramMap.get('id');
    this.userId = id;

    // this.userService.getUserById(id).subscribe((user) => {
    //   this.userCredentialsForm.patchValue({ profile: user });
    // });
  }

  setCredentials() {
    const formValues = this.userCredentialsForm.value;
    const user = { id: this.userId, ...formValues.profile };

    this.userService.updateUser(user).subscribe(
      () => this.router.navigate(['/users']),
      () =>
        this._snackBar.open('Cập nhật thất bại', 'Đóng', {
          duration: 2000,
          verticalPosition: 'top',
        })
    );
  }
}
