import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  constructor() { }

  options = [
    { value: 'restaurant', label: 'restaurant' },
    { value: 'delivery', label: 'delivery' },
    { value: 'user', label: 'user' },
  ];

  ngOnInit(): void {
  }

}
