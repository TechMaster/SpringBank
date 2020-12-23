import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(
    private titleService: Title,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Đăng nhập');
  }

  login(): void {
    // this.userService.login(this.username, this.password).subscribe(
    //   () => {
    //     this.router.navigateByUrl(this.userService.redirectUrl);
    //   },
    //   (err) => {
    //     this.errorMessage = err?.error?.message || err?.message;
    //   }
    // );
  }
}
