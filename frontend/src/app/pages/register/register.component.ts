import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  email: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(
    private titleService: Title,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Đăng ký tài khoản');
  }

  register() {
    this.userService.register();
  }
}
