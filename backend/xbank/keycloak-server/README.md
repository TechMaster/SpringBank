# Keycloak Server

### Chạy standalone server trực tiếp trên máy

Truy cập thư mục bin của keycloak chạy lệnh:

Chú ý: `standalone.bat` (đối với Window) hoặc `standalone.sh` (đối với Linux hoặc Mac)

Lệnh: `standlone.bat -Dkeycloak.migration.action=export -Dkeycloak.migration.provider=dir -Dkeycloak.migration.dir=thư_mục_của_bạn`

Ví dụ:

```
    standalone.bat -Dkeycloak.migration.action=export -Dkeycloak.migration.provider=dir -Dkeycloak.migration.dri=C:/users/dinh.nguyen/Documents/keycloak/backup
```

### Chạy bằng Docker

Sử dụng Docker Compose

```
    docker-compose up
```
