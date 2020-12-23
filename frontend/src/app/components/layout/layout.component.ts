import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css'],
})
export class LayoutComponent {
  currentUser: User;
  notifications;

  constructor(
    private userService: UserService,
    private notificationService: NotificationService,
    private router: Router
  ) {
    this.userService.currentUser.subscribe((user) => {
      if (user) {
        this.currentUser = user;
        this.router.navigateByUrl('/');
      }
    });
  }

  ngOnInit() {
    this.notificationService.connect();

    this.notificationService
      .getNotifications()
      .subscribe(console.log);
  }

  login() {
    this.userService.login();
  }

  logout() {
    this.userService.logout();
    // this.router.navigateByUrl('/login');
  }
}
