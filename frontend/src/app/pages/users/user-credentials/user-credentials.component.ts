import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Title } from '@angular/platform-browser';

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
    private router: Router
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
      () => alert('Cập nhật thất bại!')
    );
  }
}
