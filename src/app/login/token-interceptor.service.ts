import { HttpHandler, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';

@Injectable(
)
export class TokenInterceptorService {

  content: string;
  injector: any;
  
  constructor() { }

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
