import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DataService } from '../service/data.service';
import { UserModel } from './user-model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  user: boolean;
  rest: boolean;
  delivery: boolean;
  registerForm: FormGroup;
  previewImage: string | ArrayBuffer;
  selectedImage: File;
  bike: boolean;
  boat: boolean;
  car: boolean;

  constructor(private fb: FormBuilder, private dataService: DataService, private router: Router) { }

  model = new UserModel('', '', '', '', '');

  options = [
    { value: 'user', label: 'user' },
    { value: 'restaurant', label: 'restaurant' },
    { value: 'delivery', label: 'delivery' },
  ];

  onChange(value) {
    console.log(value)
    if (value == '0: user') {
      this.rest = false;
      this.delivery = false;
    } else if (value == '1: restaurant') {
      this.rest = true;
      this.delivery = false;
    } else if (value = '2: delivery') {
      this.rest = true;
      this.delivery = true;
    }
  }

  ngOnInit(): void {
    this.user = false;
    this.rest = false;
    this.delivery = false;

    //reative form validations 
    this.registerForm = this.fb.group({
      accountType: ['', [Validators.required, Validators.maxLength(50)]],//any time more than one validation is written be sure to put it in an array unless it will throw [ validator to return Promise or Observable]
      name: ['', [Validators.required, Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$')]],
      phone: ['', Validators.required],
      password: ['', Validators.required],
      city: [''],
      logo: [''],
      car: [''],
      bike: [''],
      boat: [''],
      timeCar: [''],
      timeBike: [''],
      timeBoat: [''],
    })
  }


  search(value: string, event) {

    if (event.checked) {
      if (value == 'boat') {
        this.boat = true;
      } else if (value == 'bike') {
        this.bike = true;
      } else if (value == 'car') {
        this.car = true;
      }
    } else {
      if (value == 'boat') {
        this.boat = false;
      } else if (value == 'bike') {
        this.bike = false;
      } else if (value == 'car') {
        this.car = false;
      }
    }

    console.log(value, event);
  }

  save() {
    console.log(this.registerForm.value);

    if (this.registerForm.value.accountType == 'user') {
      this.dataService.saveUser(this.registerForm.value.phoneNumber,this.registerForm.value.email, this.registerForm.value.name, this.registerForm.value.password)
    .subscribe(res => {
        this.router.navigate(['/login']);
      },
        err => console.log(err)
      )

    } else if (this.registerForm.value.accountType == 'restaurant') {

      this.dataService.saveRestaurant(this.registerForm.value.phoneNumber, this.registerForm.value.city, this.registerForm.value.emailAddress, this.selectedImage, this.registerForm.value.name, this.registerForm.value.password)
        .subscribe(res => this.router.navigate(['/login']),
          err => console.log(err))

    } else if (this.registerForm.value.accountType == 'delivery') {


      this.dataService.saveDelivery(this.registerForm.value.timeBike, this.registerForm.value.timeBoat, this.registerForm.value.timeCar, this.bike, this.boat, this.car, this.registerForm.value.phoneNumber, this.registerForm.value.city, this.registerForm.value.emailAddress, this.selectedImage, this.registerForm.value.name, this.registerForm.value.password)
        .subscribe(res => this.router.navigate(['/login']),
          err => console.log(err))

    }

  }
 

  // //image preview
  onImagePicked(event: Event) {
    const file = <File>(event.target as HTMLInputElement).files[0];
    this.registerForm.patchValue({ logo: file });
    this.registerForm.get('logo').updateValueAndValidity();

    const reader = new FileReader();
    reader.onload = () => {
      this.previewImage = reader.result;
    };
    reader.readAsDataURL(file);
    this.selectedImage = file;
  }
}
