import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-about',
  template: `
    <h1 class="text-center" style="margin-top: 10%;">
      Trang này hiện đang được xây dựng!
    </h1>

    <p class="text-center" style="margin-top: 3rem;">
      <button mat-raised-button routerLink="/">
        <span>Quay lại trang chủ</span>
      </button>
    </p>
  `,
})
export class AboutComponent implements OnInit {
  constructor(private titleService: Title) {}

  ngOnInit(): void {
    this.titleService.setTitle('Giới thiệu');
  }
}
