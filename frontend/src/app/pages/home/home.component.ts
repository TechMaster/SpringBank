import { Component, OnDestroy, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import Swipe from 'swipejs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit, OnDestroy {
  swipe: Swipe;

  constructor(private titleService: Title) {}

  ngOnInit(): void {
    this.titleService.setTitle('Spring Bank - Trang chủ');

    this.swipe = new Swipe(document.getElementById('slider'), {
      auto: 3000,
      disableScroll: true,
      stopPropagation: true,
    });
  }

  ngOnDestroy(): void {
    this.swipe.kill();
  }
}
