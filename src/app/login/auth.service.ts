import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IAccount } from './iaccount';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private _loginUrl = "http://localhost:8080/api/v1/login";

  constructor(private http: HttpClient) { }

  getToken() {
    return localStorage.getItem('token');
  }

  loginUser(user) {
    console.log(user);
    return this.http.post<IAccount>(this._loginUrl, user);
  } 
}
