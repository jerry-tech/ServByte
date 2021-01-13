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

          this.orderForm.get('deliveryId').setValue(this.deliveryId);
        });
  }

  ngOnInit(): void {

    this.bike = true;
    this.boat = true;
    this.car = true;

    //reative form validations 
    this.orderForm = this.fb.group({
      amount: ['', Validators.required],//any time more than one validation is written be sure to put it in an array unless it will throw [ validator to return Promise or Observable]
      deliveryFee: ['', Validators.required],
      deliveryId: [''],
      deliveryMeans: [''],
      mealId: [''],
      type: ['', Validators.required]
    })

    const resolvedData: ItemResolved = this.route.snapshot.data['resolvedData'];
    this.errorMessage = resolvedData.error;

    this.restId = parseInt(this.route.snapshot.params['id']);

    this.dataservice.getSingleMeal(this.restId).subscribe(
      (res: ITEMS) => {
        console.log(res.data)
        this.selectedMenu = res.data
        this.orderForm.get('deliveryId').setValue(this.deliveryId);
        this.orderForm.get('amount').setValue(this.selectedMenu.price + "00");
        this.orderForm.get('deliveryFee').setValue('50000');
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

  payNow() {
    console.log(this.orderForm.value);
    let fd = new FormData();//using form data

    fd.append('deliveryMeans', this.orderForm.value.deliveryMeans);
    fd.append('amount', this.orderForm.value.amount);
    fd.append('deliveryId', this.orderForm.value.deliveryId);
    fd.append('deliveryFee', this.orderForm.value.deliveryFee);
    fd.append('mealId', this.orderForm.value.mealId);

    this.dataservice.makePayment(fd)
      .subscribe(
        res => {
          window.location.href = res.data.authorization_url;
        },
        err => console.log(err)
      );

  }




}
