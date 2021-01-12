import { Component, OnDestroy, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { UserService } from 'src/app/services/user.service';
import Swipe from 'swipejs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit, OnDestroy {
  swipe: Swipe;

  constructor(private titleService: Title, private userService: UserService) {}

  ngOnInit(): void {
    this.titleService.setTitle('Spring Bank - Trang chủ');

    this.swipe = new Swipe(document.getElementById('slider'), {
      auto: 3000,
      disableScroll: true,
      stopPropagation: true,
    });

    this.userService.changePassword('ac7aa32d-aa9c-4d78-940e-fcef7f3e2bb2').subscribe()
  }

  ngOnDestroy(): void {
    this.swipe.kill();
  }
}
