import { BrowserModule } from '@angular/platform-browser';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { HttpClientModule,HTTP_INTERCEPTORS, HttpRequest } from '@angular/common/http';
import { AppComponent } from './app.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { AppRoutingModule } from './app-routing.module';
import { RegisterComponent } from './register/register.component';
import { MealComponent } from './meal/meal.component';
import { MenuComponent } from './menu/menu.component';
import { MenuItemComponent } from './menu-item/menu-item.component';
import { CartComponent } from './cart/cart.component';
import { TokenInterceptorService } from './interceptors/token-interceptor.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ResturantsComponent } from './resturants/resturants.component';
import { RestThumbnailComponent } from './rest-thumbnail/rest-thumbnail.component';
import { UserLoginComponent } from './user/user-login/user-login.component';

@NgModule({
  
  declarations: [
    AppComponent,
    NavBarComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    MealComponent,
    MenuComponent,
    MenuItemComponent,
    CartComponent,
    ResturantsComponent,
    RestThumbnailComponent,
    UserLoginComponent
  ],

  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MDBBootstrapModule.forRoot()
  ],

  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }
  ],
  schemas : [NO_ERRORS_SCHEMA],
  bootstrap: [AppComponent]
})
export class AppModule { }
