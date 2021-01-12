import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
// MDB Angular Free
import { CheckboxModule, WavesModule, ButtonsModule, InputsModule, IconsModule, CardsModule } from 'angular-bootstrap-md'
import { AuthService } from './service/auth.service';
import { loginData } from './login.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  options = [
    { value: 'restaurant', label: 'restaurant' },
    { value: 'delivery', label: 'delivery' },
    { value: 'user', label: 'user' },
  ];

  model = new loginData('', '', '');
  constructor(private _auth: AuthService, private router: Router) { }

  ngOnInit(): void {
  }
  

  loginUser() {
    this._auth.loginUser(this.model).subscribe(
      res => {
        if(this.model.accountType == 'user'){
          localStorage.setItem('userToken', res.data.JWT);
          console.log(res);
        }
       
      },
      err => {
        console.log(err);
      }
    )
  }

}
