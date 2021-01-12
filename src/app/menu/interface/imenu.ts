import { DecimalPipe } from "@angular/common";
import { IRest } from "src/app/resturants/interface/irest";

export interface IMenu {
    data: any;
    mealId: number;
    description: string;
    name: string;
    picture: string;
    price: any;
    timeTaken: string;
    restaurant: IRest
}
export interface MenuResolved {
    menu: IMenu;
    error?: any;
}
