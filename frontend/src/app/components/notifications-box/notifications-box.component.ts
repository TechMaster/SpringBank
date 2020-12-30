import { Component, Input } from '@angular/core';
import { NotificationService } from 'src/app/services/notification.service';
import { Notification } from 'src/app/models/notification.model';

@Component({
  selector: 'app-notifications-box',
  templateUrl: './notifications-box.component.html',
  styleUrls: ['./notifications-box.component.css'],
})
export class NotificationsBoxComponent {
  @Input() notifications: Notification[];

  constructor(private notificationService: NotificationService) {}

  readAllNotifications() {
    this.notificationService.readAllNotifications().subscribe();
  }
}
