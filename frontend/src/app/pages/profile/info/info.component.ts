import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css'],
})
export class InfoComponent implements OnInit {
  avatarUrl: string = 'assets/img/default-avatar.png';

  profileForm = this.fb.group({
    name: ['Robin', [Validators.required]],
    birthday: [''],
    phone: ['', Validators.required],
    bio: [''],
  });

  constructor(private titleService: Title, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.titleService.setTitle('Thông tin cá nhân');
  }

  onFileChange(event): void {
    const files = event.target.files;
    let reader = new FileReader();

    if (files && files.length) {
      const file = event.target.files[0];
      reader.readAsDataURL(file);

      reader.onload = () => {
        this.avatarUrl = reader.result.toString();
      };
    }
  }

  updateProfile() {
    console.log(this.profileForm.value);
  }
}
