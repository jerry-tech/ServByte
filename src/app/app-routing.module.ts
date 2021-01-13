import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { MealComponent } from './meal/meal.component';
import { MenuItemResolve } from './menu-item/item-resolve';
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

            { path: 'meal', component: MealComponent },

            { path: 'register', component: RegisterComponent },
            { path: 'restaurant', component: ResturantsComponent },

            {
                path: 'details/:id', component: MenuItemComponent, resolve: {
                    resolvedData: MenuItemResolve
                }, 
            },
            {
                path: 'menu/:id', component: MenuComponent, resolve: {
                    resolvedData: MenuResolve
                }
            },
            { path: 'login', component: UserLoginComponent },
            { path: '', redirectTo: 'Home', pathMatch: 'full' },
            { path: '**', redirectTo: 'Home', pathMatch: 'full' }

        ])
    ],
    exports: [RouterModule]
})

export class AppRoutingModule {

}