import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';
import { NotificationService } from 'src/app/services/notification.service';
import { Notification } from 'src/app/models/notification.model';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css'],
})
export class LayoutComponent {
  currentUser: User;
  notifications: Notification[];

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
    // Listen notifications change
    this.notificationService.notifications.subscribe(
      (data) => (this.notifications = data)
    );

    // Connect Websocket server
    this.notificationService.setupWebsocket();
  }

  login() {
    this.userService.login();
  }

  logout() {
    this.userService.logout();
  }
}
