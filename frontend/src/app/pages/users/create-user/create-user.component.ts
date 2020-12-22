import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styles: [':host {width: 100%; max-width: 500px; margin: 30px auto;}'],
})
export class CreateUserComponent {
  createUserForm = this.fb.group({
    profile: [],
  });

  constructor(
    private titleService: Title,
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Thêm User');
  }

  createUser() {
    const formValues = this.createUserForm.value;
    this.userService
      .createUser({
        ...formValues.profile,
        ...formValues.password,
      })
      .subscribe(
        () => this.router.navigate(['/users']),
        () => alert('Thêm User thất bại!')
      );
  }
}
