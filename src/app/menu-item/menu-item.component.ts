import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { IMenu, MenuResolved } from '../menu/interface/imenu';
import { DataService } from '../service/data.service';
import { ItemResolved, ITEMS } from './item';

@Component({
  selector: 'app-menu-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['./menu-item.component.scss']
})
export class MenuItemComponent implements OnInit {
  delivery: any;
  deliveryNames;
  errorMessage: any;
  restId: number;
  selectedMenu: any;
  bike: boolean;
  boat: boolean;
  car: boolean;

  orderForm: any;
  total: any;
  deliveryId: any;
  constructor(private fb: FormBuilder, private route: ActivatedRoute, private router: Router, private dataservice: DataService) { }

  onChange(value: string) {
    if (value != null)
      this.delivery = this.dataservice.getDeliveryByName(value.slice(0))
        .subscribe(res => {

          this.deliveryId = res.data.deliveryId;
          this.bike = res.data.bike;
          this.boat = res.data.boat;
          this.car = res.data.car;

        });
  }

  ngOnInit(): void {

    this.bike = false;
    this.boat = false;
    this.car = false;

    //reative form validations 
    this.orderForm = this.fb.group({
      amount: ['', Validators.required],//any time more than one validation is written be sure to put it in an array unless it will throw [ validator to return Promise or Observable]
      // boat: ['', Validators.required],
      // car: ['', Validators.required],
      deliveryFee: ['', Validators.required],
      mealId: [''],
      total: [''],
      root: [''],
    })

    const resolvedData: ItemResolved = this.route.snapshot.data['resolvedData'];
    this.errorMessage = resolvedData.error;

    this.restId = parseInt(this.route.snapshot.params['id']);

    this.dataservice.getSingleMeal(this.restId).subscribe(
      (res: ITEMS) => {
        console.log(res.data)
        this.selectedMenu = res.data
        this.orderForm.get('amount').setValue(this.selectedMenu.price);
        this.orderForm.get('deliveryFee').setValue('500');
        this.orderForm.get('mealId').setValue(this.selectedMenu.mealId);
      },
      (err: any) => { console.log(err.data) }
    )

    //getting all delivery
    this.dataservice.getAllDelivery().subscribe(
      res => {
        this.delivery = res.data;

        this.deliveryNames = res.data;

        console.log(this.deliveryNames);

      },
      err => console.log(err)
    )


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
  payNow() {
    this.dataservice.makePayment(5000, this.bike, this.boat, this.car,
      50000, this.deliveryId, 1)
      .subscribe(
        res => console.log(this.orderForm),
        err => console.log(err)
      );

  }




}
