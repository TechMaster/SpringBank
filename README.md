# SpringBank
Ứng dụng demo Internet Bank sử dụng SpringBoot + Angular cho khoá học tại BIDV tháng 11 đến 12/2020.

## Chạy demo ứng dụng trên môi trường development

### Backend

Chạy server qua docker:

```
  cd backend
  docker-compose build && docker-compose up -d
```

Xem API document tại: http://localhost:8080/swagger-ui.html

### Frontend

Chạy server dev bằng Angular CLI:

```
  cd frontend
  npm install && ng serve
```

Website chạy ở địa chỉ: http://localhost:4200
