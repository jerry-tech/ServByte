import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { IMenu } from '../menu/interface/imenu';
import { IRest } from '../resturants/interface/irest';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private _restuarant = "http://localhost:8080/api/v1/restaurants";
  private _menu = "http://localhost:8080/api/v1/getMeal?restId=";

  constructor(private http: HttpClient, private router: Router) { }

  getAllRestaurant(): Observable<IRest> {
    return this.http.get<IRest>(this._restuarant);
  }

  getAllMealByResturant(id: number): Observable<IMenu>{
    let getHeaders: HttpHeaders = new HttpHeaders({
      'content-type': 'application/json',
    });
    const url = `${this._menu}${id}`;
    return this.http.get<IMenu>(url);
  }

}
