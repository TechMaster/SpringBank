import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-transfer-money',
  templateUrl: './transfer-money.component.html',
  styles: [':host {width: 100%; max-width: 500px; margin: 30px auto;}'],
})
export class TransferMoneyComponent implements OnInit {
  transferForm = this.fb.group({
    source: [''],
    target: [''],
    bankTarget: [''],
    money: [''],
    cost: ['1'],
    note: [''],
  });

  constructor(private titleService: Title, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.titleService.setTitle('Chuyển tiền');
  }

  transferMoney() {}
}
