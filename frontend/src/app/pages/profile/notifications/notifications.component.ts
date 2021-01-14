import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { NotificationService } from 'src/app/services/notification.service';
import { Notification } from 'src/app/models/notification.model';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css'],
})
export class NotificationsComponent implements OnInit {
  notifications: Notification[];

  constructor(
    private titleService: Title,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Thông báo');

    this.notificationService.getNotifications().subscribe((data) => {
      this.notifications = data;
    });
  }
}
