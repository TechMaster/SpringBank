import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-notifications-box',
  templateUrl: './notifications-box.component.html',
  styleUrls: ['./notifications-box.component.css'],
})
export class NotificationsBoxComponent {
  @Input() notifications;
}
