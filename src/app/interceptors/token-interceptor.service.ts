import { HttpHandler, HttpRequest } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { AuthService } from '../user/user-login/auth.service';

@Injectable(
)
export class TokenInterceptorService {

  content: string;
  
  
  constructor(private injector: Injector) { }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    console.log(`AddHeaderInterceptor - ${req.url}`);//subject to removal
    let authService = this.injector.get(AuthService);
    let token = authService.getToken();

      let tohenizedReq = req.clone({
        //     setHeaders: {
        //   'token': token || ''
        // }
        headers: req.headers.set('Authorization', token || ''),
      });
      return next.handle(tohenizedReq);
  
  }
}
