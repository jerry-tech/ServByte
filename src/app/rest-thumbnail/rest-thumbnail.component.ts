import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-rest-thumbnail',
  templateUrl: './rest-thumbnail.component.html',
  styleUrls: ['./rest-thumbnail.component.scss']
})
export class RestThumbnailComponent implements OnInit {

  @Input() rest: any; //using @Inject to pass in the data
  constructor() { }

  ngOnInit(): void {
  }

}
