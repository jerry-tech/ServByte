import { IRest } from "../resturants/interface/irest";

export interface ITEMS {

    data: any;
    mealId: number;
    description: string;
    name: string;
    picture: string;
    price: any;
    timeTaken: string;
    restaurant: IRest
}
export interface ItemResolved {
    item: ITEMS;
    error?: any;
}

