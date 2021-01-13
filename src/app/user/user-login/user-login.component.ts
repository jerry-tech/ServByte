import { Component, Input, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';
import { loginData } from './loginData';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.scss']
})
export class UserLoginComponent implements OnInit {

  options = [
    { value: 'restaurant', label: 'restaurant' },
    { value: 'delivery', label: 'delivery' },
    { value: 'user', label: 'user' },
  ];

  model = new loginData('', '', '');
  constructor(private _auth: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  loginUser(form: NgForm) {
    this._auth.loginUser(this.model).subscribe(
      res => {
        //saving Json web token to local storage
        localStorage.setItem('token', res.data.JWT);
        console.log(res.data);
        if (res.data.accountType == 'user') {

          localStorage.setItem('username', res.data.username);
          //setting the value of the behavoural subject
          this._auth.myUserName.next(res.data.username);

        } else {
          localStorage.setItem('admin', res.data.username);

          //setting the value of the behavoural subject
          this._auth.myAppendedUser.next(res.data.username);
        }
        //routing to single product page
        this.router.navigate(['/restaurant']);
      },
      err => {
        window.alert('Wrong Username or Password');
      }
    )
  }

}
