import {
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private userService: UserService, private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    // Add Bearer token to request headers
    let currentUser = this.userService.currentUserValue;
    const authToken = currentUser?.token;
    const authReq = req.clone({
      setHeaders: { Authorization: 'Bearer ' + authToken },
    });

    return next.handle(authReq);
  }
}
