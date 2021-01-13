import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { IAccount } from './iaccount';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    myAppendedUser: BehaviorSubject<any>;
    myUserName: BehaviorSubject<any>;

    private _loginUrl = "http://localhost:8080/api/v1/login";
    myUsers: any;
    myUser: any;


    constructor(private http: HttpClient, private router: Router) {

        this.myAppendedUser = new BehaviorSubject(localStorage.getItem('admin'));
        this.myUserName = new BehaviorSubject(localStorage.getItem('username'));

        this.myUsers = this.myAppendedUser.asObservable();
        this.myUser = this.myUserName.asObservable();
    }

    getAdmin(){
        return this.myAppendedUser.value;
    }
    getUsername() {
        return this.myUserName.value;
    }

    getToken() {
        return localStorage.getItem('token');
    }

    loginUser(user) {
        console.log(user);
        return this.http.post<IAccount>(this._loginUrl, user);
    }

    logOutUser() {
        // remove user from local storage and set current user to null
        localStorage.clear();
        sessionStorage.clear();
        this.myAppendedUser.next(null);
        this.router.navigate(['/Login']);
    }
   
    loggedIn() {
        return !!localStorage.getItem('token');
    }
}