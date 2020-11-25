## X-BANK ##
#### A demo project explaining the backend authentication using JWT (Json Web Token) authentication using Spring Security &amp; PostGreSQL JPA.
There's support for the following features:

* Conventional email/username based registration with admin support
* Conventional Login using Spring Security and generation of JWT token
* Multiple device login and logout support
* In memory store for blacklisting logged out tokens
* Support for expiration bases email verification. Mail is sent upon registration. 
* Resend the email confirmation email if old one expires
* Support for password updation once logged in
* Support for forgot-password functionality with password reset token sent to mail.
* Supports admin protected urls leveraging Spring security
* API to refresh JWT tokens once the temporary JWT expires. 
* API to check availability of username/email during registration.

![](https://cdn-images-1.medium.com/max/1334/1*7T41R0dSLEzssIXPHpvimQ.png)

---

## Swagger Docs ##
The project has been configured with a basic Swagger docket that exposes the commonly used API's along with the expected params.
![image](https://user-images.githubusercontent.com/12872673/45046897-24ded880-b095-11e8-8930-7b678e2843bb.png)


---

## JWT ##
JSON Web Tokens are an open, industry standard RFC 7519 method for representing claims securely between two parties.


## Exception Handling ##
The app throws custom exceptions wherever necessary which are captured through a controller advice. It then returns the appropriate error response to the caller
* AppException
* BadRequestException
* ResourceAlreadyInUseException
* ResourceNotFoundException
* UserLoginException
* UserRegistrationException
* MethodArgumentNotValidException
* UserLogoutException
* TokenRefreshException
* UpdatePasswordException
* PasswordResetException
* PasswordResetLinkException

Moreover, entities are validated using JSR-303 Validation constraints. 

---

## Steps to set up the X-BANK app

1. **Create a PostGreSQL database**

	```bash
    sudo -u postgres psql;
    create database xbank;
    create user root with encrypted password 'xbank@123';
    grant all privileges on database xbank to root;
	```

3. **Change MySQL username and password as per your MySQL installation**

	+ open `src/main/resources/application.yml` file.

	+ change `spring.datasource.username` and `spring.datasource.password` properties as per your mysql installation
	
	+ open `src/main/resources/mail.properties` file.

	+ change `spring.mail.username` and `spring.mail.password` properties as per your mail installation

4. **Run the app**

	You can run the spring boot app by typing the following command -

	```bash
    cd x-bank
    mvn clean install -Dmaven.test.skip=true
    mvn spring-boot:run
	```

	The server will start on port 8080. Token default expiration is 600000ms ~ 10 minutes.

5. **Add the default Roles**
	
	The spring boot app uses role based authorization powered by spring security. Please execute the following sql queries in the database to insert the `USER` and `ADMIN` roles.

	```sql
    INSERT INTO ROLE (ROLE_NAME) VALUES ('ROLE_CUSTOMER');
    INSERT INTO ROLE (ROLE_NAME) VALUES ('ROLE_BANK_OPERATOR');
	```

	Any new user who signs up to the app is assigned the `ROLE_CUSTOMER` by default.
