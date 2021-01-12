import { Component, OnInit } from '@angular/core';
import { DataService } from '../service/data.service';
import { IRest } from './interface/irest';

@Component({
  selector: 'app-resturants',
  templateUrl: './resturants.component.html',
  styleUrls: ['./resturants.component.scss']
})
export class ResturantsComponent implements OnInit {
 allRest: any;
 _listFilter: string;
  filteredFeeds: any;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.getAllRestaurant().subscribe(
      res => {
        this.allRest = res.data;
        this.filteredFeeds = this.allRest;
      }, err => {

      }

    )
  }

  get listFilter(): string {
    return this._listFilter;
  }
  set listFilter(value: string) {
    this._listFilter = value;
    this.filteredFeeds = this.listFilter ? this.performFilter(this.listFilter) : this.allRest;
  }
  performFilter(filterBy: string): IRest[] {
    filterBy = filterBy.toLowerCase();
    return this.allRest.filter((resturant: IRest) => resturant.city.toLocaleLowerCase().indexOf(filterBy) !== -1);
  }



}
