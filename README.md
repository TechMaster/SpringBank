# SpringBank
## Chạy demo ứng dụng trên môi trường development

### Backend

Chạy server qua docker bằng lệnh:

```
  docker-compose build && docker-compose up -d
```

Xem API document tại: http://localhost:8080/swagger-ui.html

### Frontend

Cài đặt dependencies: `npm install`

Cài đặt Angular CLI: `npm install -g @angular/cli`

Start server bằng lệnh: `ng serve`

Website chạy ở địa chỉ: http://localhost:4200

Admin client H2 DB: http://localhost:8080/h2
