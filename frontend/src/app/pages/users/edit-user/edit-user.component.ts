import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styles: [':host {width: 100%; max-width: 500px; margin: 30px auto;}'],
})
export class EditUserComponent implements OnInit {
  userId: string;

  updateUserForm = this.fb.group({
    profile: [],
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
    this.titleService.setTitle('Cập nhật User');

    const id = this.route.snapshot.paramMap.get('id');
    this.userId = id;

    this.userService.getUserById(id).subscribe((user) => {
      this.updateUserForm.patchValue({ profile: user });
    });
  }

  updateUser() {
    const formValues = this.updateUserForm.value;
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
