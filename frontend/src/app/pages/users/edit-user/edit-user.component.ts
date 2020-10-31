import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styles: [':host {width: 100%; max-width: 500px; margin: 30px 0;}'],
})
export class EditUserComponent implements OnInit {
  userId: string;

  updateUserForm = this.fb.group({
    profile: [],
  });

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
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
      () => alert('Cannot update user at this time!')
    );
  }
}
