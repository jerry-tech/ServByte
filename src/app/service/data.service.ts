import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { IMeal } from '../meal/imeal';
import { ITEMS } from '../menu-item/item';
import { IMenu } from '../menu/interface/imenu';
import { IUser } from '../register/iuser';
import { UserModel } from '../register/user-model';
import { IRest } from '../resturants/interface/irest';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private _restuarant = "http://localhost:8080/api/v1/restaurants";
  private _menu = "http://localhost:8080/api/v1/getMeal?restId=";
  private _meal = "http://localhost:8080/api/v1/meal";
  private _getMeal = "http://localhost:8080/api/v1/getMeal";
  private _delivery = "http://localhost:8080/api/v1/account/getDelivery";

  private _regDelivery = "http://localhost:8080/api/v1/account/delivery";
  private _regRest = "http://localhost:8080/api/v1/account/restaurant";
  private _regUser = "http://localhost:8080/api/v1/account/users";

  private _deliveryName = "http://localhost:8080/api/v1/delivery";

  private _order = "http://localhost:8080/api/v1/order";
  private _verifyTrans = "http://localhost:8080/api/v1/order/verifyTransaction"

  model = new UserModel('', '', '', '', '');

  constructor(private http: HttpClient, private router: Router) { }

  getAllRestaurant(): Observable<IRest> {
    return this.http.get<IRest>(this._restuarant);
  }

  getAllMealByResturant(id: number): Observable<IMenu> {
    let getHeaders: HttpHeaders = new HttpHeaders({
      'content-type': 'application/json',
    });
    const url = `${this._menu}${id}`;
    return this.http.get<IMenu>(url);
  }
  getSingleMeal(id: number): Observable<ITEMS> {
    let getHeaders: HttpHeaders = new HttpHeaders({
      'content-type': 'application/json',
    });
    const url = `${this._getMeal}/${id}`;
    return this.http.get<ITEMS>(url);
  }

  getAllDelivery(): Observable<any> {
    let getHeaders: HttpHeaders = new HttpHeaders({
      'content-type': 'application/json',
    });
    return this.http.get<any>(this._delivery);
  }

  getSuccess(): Observable<any> {
    let getHeaders: HttpHeaders = new HttpHeaders({
      'content-type': 'application/json',
    });
    return this.http.get<any>(this._verifyTrans);
  }

  getDeliveryByName(name: string): Observable<any> {
    let getHeaders: HttpHeaders = new HttpHeaders({
      'content-type': 'application/json',
    });
    const url = `${this._deliveryName}/${name}`;
    return this.http.get<any>(url);
  }

  makePayment(value: any) {

    return this.http.post<any>(this._order, value).pipe(
      tap(data => {
        console.log(JSON.stringify(data))
      }),
      catchError(this.handleError)
    )
  }

  //method used to save a meal
  save(description: string, name: string, picture: any, price: any, timeTaken: string) {
    let fd = new FormData();//using form data

    fd.append('description', description);
    fd.append('name', name);
    fd.append('picture', picture);
    fd.append('price', price);
    fd.append('timeTaken', timeTaken);

    return this.http.post<IMeal>(this._meal, fd).pipe(
      tap(data => {
        console.log(JSON.stringify(data))
      }),
      catchError(this.handleError)
    )
  }

  //method used to save a user
  saveUser(phoneNumber: string, emailAddress: string, name: string, password: string) {
    let fd = new FormData();//using form data

    fd.append('emailAddress', emailAddress);
    fd.append('name', name);
    fd.append('password', password);
    fd.append('phoneNumber', phoneNumber);

    return this.http.post<any>(this._regUser, fd).pipe(
      tap(data => {
        console.log(JSON.stringify(data))
      }),
      catchError(this.handleError)
    )
  }

  //method used to save a user
  saveRestaurant(phoneNumber: string, city: string, emailAddress: string, logo: any, name: string, password: string) {

    let fd = new FormData();//using form data

    fd.append('city', city);
    fd.append('emailAddress', emailAddress);
    fd.append('logo', logo);
    fd.append('name', name);
    fd.append('password', password);
    fd.append('phoneNumber', phoneNumber);

    return this.http.post<IUser>(this._regRest, fd).pipe(
      tap(data => {
        console.log(JSON.stringify(data))
      }),
      catchError(this.handleError)
    )
  }

  //method used to save delivry account
  saveDelivery(timeBike: string, timeBoat: string, timeCar: string, bike: any, boat: any, car: any, phoneNumber: string, city: string, emailAddress: string, logo: any, name: string, password: string) {

    let fd = new FormData();//using form data

    fd.append('bike', bike);
    fd.append('boat', boat);
    fd.append('car', car);
    fd.append('city', city);
    fd.append('emailAddress', emailAddress);
    fd.append('logo', logo);
    fd.append('name', name);
    fd.append('password', password);
    fd.append('phoneNumber', phoneNumber);
    fd.append('timeBike', timeBike);
    fd.append('timeBoat', timeBoat);
    fd.append('timeCar', timeCar);

    return this.http.post<IUser>(this._regDelivery, fd).pipe(
      tap(data => {
        console.log(JSON.stringify(data))
      }),
      catchError(this.handleError)
    )
  }

  //used to handle error
  private handleError(err) {
    let errorMessage: string;
    if (err.error instanceof ErrorEvent) {
      errorMessage = `An error occurred: ${err.error.message}`;
    } else {
      errorMessage = `Backend returned code ${err.status}: ${err.body.error}`;
    }
    console.error(err);
    return throwError(errorMessage);
  }


}
