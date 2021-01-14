import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-about',
  template: `
    <mat-card  style="margin-top: 30px" class="text-center">
      <h1>Awards and Accolades</h1>

      <p style="margin: auto; max-width: 500px;">
        Spring Bank has been recognized and awarded by many international and
        major financial institutions for its outstanding business performance.
        The bank is listed in the Top 500 world’s most valuable banking brands
        (by Brand Finance), named “Best Retail Bank in Vietnam” for the fourth
        consecutive year (by The Asian Banker) and received a series of IT
        awards. Since 2009, BIDV has ranked first amongst Vietnamese commercial
        banks in terms of readiness for IT application and development in the
        Vietnam ICT Index of Vietnam Ministry of Information and Communications
        and Vietnam Association for Information Processing.
      </p>
    </mat-card>
  `,
})
export class AboutComponent implements OnInit {
  constructor(private titleService: Title) {}

  ngOnInit(): void {
    this.titleService.setTitle('Giới thiệu');
  }
}
