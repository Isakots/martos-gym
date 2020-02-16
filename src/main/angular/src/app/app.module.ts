import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthInterceptor} from "./core/interceptor/auth.interceptor";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {NavbarComonent} from "./layout/navbar/navbar.component";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {library} from '@fortawesome/fontawesome-svg-core'
import {
  faBars,
  faDrumstickBite,
  faDumbbell,
  faFutbol,
  faLock,
  faSignInAlt,
  faSignOutAlt,
  faUser,
  faUserPlus,
  faUsers,
  faWrench
} from "@fortawesome/free-solid-svg-icons";
import {HomeComponent} from './layout/home/home.component';
import {AboutUsComponent} from './layout/about-us/about-us.component';
import {NutritionComponent} from './layout/nutrition/nutrition.component';
import {GymInfoComponent} from './layout/gym-info/gym-info.component';
import {AccountModule} from "./account/account.module";

// FaIcon imports
library.add(faBars);
library.add(faUsers);
library.add(faDumbbell);
library.add(faLock);
library.add(faWrench);
library.add(faFutbol);
library.add(faUser);
library.add(faUserPlus);
library.add(faDrumstickBite);
library.add(faSignInAlt);
library.add(faSignOutAlt);

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComonent,
    HomeComponent,
    AboutUsComponent,
    NutritionComponent,
    GymInfoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    FontAwesomeModule,
    AccountModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
