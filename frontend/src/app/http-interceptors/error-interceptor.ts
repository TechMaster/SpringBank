import {
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { tap } from "rxjs/operators";
import { UserService } from "../services/user.service";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private userService: UserService, private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    return next.handle(req).pipe(
      tap(
        () => {},
        // Log out user if unauthorized (token expired)
        (error) => {
          if (error.status === 401) {
            this.userService.logout();
            this.router.navigateByUrl("/admin/login");
          } else if (error.status === 404) {
            this.router.navigateByUrl("/admin/404-not-found");
          }
        }
      )
    );
  }
}
