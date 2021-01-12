import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { DataService } from 'src/app/service/data.service';
import { MenuResolved } from '../interface/imenu';

@Injectable({
  providedIn: 'root'
})
export class MenuResolve implements Resolve<MenuResolved>{

  constructor(private dataService: DataService){ }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) : Observable<MenuResolved>{
    const id = route.paramMap.get('id');
    if(isNaN(+id)){
      const message = `Restaurant id not found : ${id}`;
      console.error(message);
      return of({menu: null, error: message});
    }
    return this.dataService.getAllMealByResturant(+id).pipe(
      map(menu => ({menu: menu})),
      catchError(error => {
        const message = `Retrival error: ${error}`;
        console.error(message);
        return of({menu: null, error:message});
      })
    )
  }
}
