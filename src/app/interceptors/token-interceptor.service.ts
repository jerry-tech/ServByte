import { HttpHandler, HttpRequest } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { AuthService } from '../login/service/auth.service';

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
        headers: req.headers.set('token', token || ''),
      });
      return next.handle(tohenizedReq);
  
  }
}
