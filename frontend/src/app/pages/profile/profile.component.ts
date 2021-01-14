import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  currentUser: User;

  constructor(private titleService: Title, private userService: UserService) {
    this.userService.currentUser.subscribe((user) => (this.currentUser = user));
  }

  ngOnInit(): void {
    this.titleService.setTitle('Trang cá nhân');
  }
}
