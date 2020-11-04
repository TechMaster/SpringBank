# JWT Spring Security Demo
## Backend
Có ba tài khoản người dùng để chứng minh các cấp độ truy cập khác nhau vào các API và các exception khác nhau:
```
Admin - admin:admin
User - user:password
Disabled - disabled:password (tài khoản bị vô hiệu hóa)
```

Có bốn endpoints hợp lý cho bản demo:
```
/api/authenticate - endpoint xác thực với quyền truy cập không hạn chế
/api/user - trả về thông tin chi tiết cho người dùng đã xác thực (mã thông báo JWT hợp lệ phải có trong request header)
/api/persons - một ví dụ endpoint được giới hạn cho người dùng được ủy quyền với quyền 'ROLE_USER' (mã thông báo JWT hợp lệ phải có trong request header)
/api/hiddenmessage - một ví dụ endpoint được giới hạn cho người dùng được ủy quyền với quyền 'ROLE_ADMIN' (mã thông báo JWT hợp lệ phải có trong request header)
```

## Frontend
một ứng dụng client sử dụng Javascript.
Xem tại [/src/main/resources/static/js/client.js](/src/main/resources/static/js/client.js).

### Generate password for new user

Dự án sử dụng [bcrypt](https://en.wikipedia.org/wiki/Bcrypt) để mã hóa mật khẩu. Bạn có thể tạo mã băm của mình bằng công cụ sau: 
[BCrypt Generator](https://www.bcrypt-generator.com)

### Using another database
```
spring:
  jpa:
    hibernate:
      # possible values: validate | update | create | create-drop
      ddl-auto: create-drop
  datasource:
    url: jdbc:mysql://localhost/myDatabase
    username: myUser
    password: myPassword
    driver-class-name: com.mysql.jdbc.Driver
```

*Chú ý: với các database khác như MySQL sequences ko làm việc cho việc tạo ID. Vậy lên bạn cần thay GenerationType trong entity beans sang 'AUTO' hoặc 'IDENTITY'.*

Bạn có thể tìm thấy một tham chiếu của tất cả các application properties [tại đây](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html).
