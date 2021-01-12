import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CartComponent } from './cart/cart.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { MealComponent } from './meal/meal.component';
import { MenuItemComponent } from './menu-item/menu-item.component';
import { MenuComponent } from './menu/menu.component';
import { MenuResolve } from './menu/resolver/menu-resolve';
import { RegisterComponent } from './register/register.component';
import { ResturantsComponent } from './resturants/resturants.component';
import { UserLoginComponent } from './user/user-login/user-login.component';



@NgModule({
    imports: [
        RouterModule.forRoot([

            { path: 'Home', component: HomeComponent },

            { path: 'Meal', component: MealComponent },
            { path: 'details', component: MenuItemComponent },
            { path: 'Cart', component: CartComponent },
            { path: 'Register', component: RegisterComponent },
            { path: 'restaurant', component: ResturantsComponent },


            {
                path: 'menu/:id', component: MenuComponent, resolve: {
                    resolvedData: MenuResolve
                }
            },
            { path: 'user', component: UserLoginComponent },
            { path: 'Login', component: LoginComponent },
            { path: '', redirectTo: 'Home', pathMatch: 'full' },
            { path: '**', redirectTo: 'Home', pathMatch: 'full' }

        ])
    ],
    exports: [RouterModule]
})

export class AppRoutingModule {

}