import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { DataService } from 'src/app/service/data.service';
import { ItemResolved } from './item';

@Injectable({
  providedIn: 'root'
})
export class MenuItemResolve implements Resolve<ItemResolved>{

  constructor(private dataService: DataService){ }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) : Observable<ItemResolved>{
    const id = route.paramMap.get('id');
    if(isNaN(+id)){
      const message = `Menu id not found : ${id}`;
      console.error(message);
      return of({item: null, error: message});
    }
    return this.dataService.getSingleMeal(+id).pipe(
      map(item => ({item: item})),
      catchError(error => {
        const message = `Retrival error: ${error}`;
        console.error(message);
        return of({item: null, error:message});
      })
    )
  }
}
