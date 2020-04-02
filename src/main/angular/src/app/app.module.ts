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
  faArrowLeft,
  faBan,
  faBars,
  faDrumstickBite,
  faDumbbell,
  faEdit,
  faFutbol,
  faHandPointUp,
  faLock,
  faPencilAlt,
  faPlus,
  faSave,
  faSignInAlt,
  faSignOutAlt,
  faTimes,
  faTrashAlt,
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
import {SignUpComponent} from './sign-up/sign-up.component';
import {ReactiveFormsModule} from "@angular/forms";
import {AuthorizationGuard} from "./core/guard/authorization-guard.service";
import {ErrorComponent} from './error/error.component';
import {ArticleModule} from "./article/article.module";
import {TimelineComponent} from "./layout/home/timeline/timeline.component";
import {CKEditorModule} from "@ckeditor/ckeditor5-angular";
import {SharedModule} from "./shared/shared.module";
import {ToolModule} from "./tool/tool.module";

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
library.add(faArrowLeft);
library.add(faPlus);
library.add(faPencilAlt);
library.add(faTimes);
library.add(faBan);
library.add(faSave);
library.add(faHandPointUp);
library.add(faEdit);
library.add(faTrashAlt);

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComonent,
    HomeComponent,
    AboutUsComponent,
    NutritionComponent,
    GymInfoComponent,
    SignUpComponent,
    ErrorComponent,
    TimelineComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    FontAwesomeModule,
    AccountModule,
    ReactiveFormsModule,
    ArticleModule,
    CKEditorModule,
    SharedModule,
    ToolModule
  ],
  providers: [
    AuthorizationGuard,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
