import { Component, OnInit } from '@angular/core';
import { DataService } from '../service/data.service';
import { AuthService } from '../user/user-login/auth.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(public authService: AuthService, private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.getSuccess()
    .subscribe();
  }


}
