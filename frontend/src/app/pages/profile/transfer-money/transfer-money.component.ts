import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-transfer-money',
  templateUrl: './transfer-money.component.html',
  styles: [':host {width: 100%; max-width: 500px; margin: 30px auto;}'],
})
export class TransferMoneyComponent implements OnInit {
  transferForm = this.fb.group({
    name: ['', [Validators.required]],
    email: ['', [Validators.email]],
    birthday: [''],
    phone: [''],
  });

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {}
}
