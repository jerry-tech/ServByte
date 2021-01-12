import { Component, Input, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { loginData } from 'src/app/login/login.model';
import { AuthService } from 'src/app/login/service/auth.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.scss']
})
export class UserLoginComponent implements OnInit {

  @Input() rest: any; //using @Inject to pass in the data

  model = new loginData('user','', '');
  constructor(private _auth: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  loginUser(form: NgForm) {
    this._auth.loginUser(this.model).subscribe(
      res => {
        // if(res.data.)
        localStorage.setItem('userToken', res.data.JWT);
        console.log(res);
      },
      err => {
        console.log(err);
      }
    )
  }

}
