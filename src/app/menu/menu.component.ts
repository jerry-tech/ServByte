import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService } from '../service/data.service';
import { IMenu, MenuResolved } from './interface/imenu';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  errorMessage: string;
  restId: number;
  selectedMenu: any;
  show: boolean;
  name:any;

  constructor(private route: ActivatedRoute, private router: Router, private dataservice: DataService) { }

  ngOnInit(): void {

    this.show = false;

    const resolvedData: MenuResolved = this.route.snapshot.data['resolvedData'];
    this.errorMessage = resolvedData.error;
  
    this.restId = parseInt(this.route.snapshot.params['id']);
    
    this.dataservice.getAllMealByResturant(this.restId).subscribe(
      (res: IMenu) => {
        console.log(res.data)
        this.selectedMenu = res.data
      },
      (err: any) => {}
    )
  }

  showDialogIf(){
    if(localStorage.getItem('userToken') != null){
      this.router.navigate(['/details']);
    }else{
      this.show = true;
    }
  }

}
