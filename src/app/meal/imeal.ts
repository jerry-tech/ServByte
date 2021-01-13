import { IRest } from "../resturants/interface/irest";

export interface IMeal {
    meal_id: number,
    description: string,
    name: string,
    picture: any,
    price: any,
    time_taken: string,
    upload_dir: string,
    restaurant: IRest
}
