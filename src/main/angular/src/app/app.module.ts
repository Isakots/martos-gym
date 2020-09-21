import {BrowserModule} from '@angular/platform-browser';
import {LOCALE_ID, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthInterceptor} from './core/interceptor/auth.interceptor';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {NavbarComponent} from './layout/navbar/navbar.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {library} from '@fortawesome/fontawesome-svg-core';
import {
  faArrowLeft,
  faBan,
  faBars,
  faCalendar,
  faChevronDown,
  faChevronUp,
  faClipboardList,
  faDrumstickBite,
  faDumbbell,
  faEdit,
  faEnvelope,
  faFutbol,
  faHandPointDown,
  faHandPointUp,
  faLock,
  faNewspaper,
  faPencilAlt,
  faPlus,
  faRunning,
  faSave,
  faSignInAlt,
  faSignOutAlt,
  faTimes,
  faTrashAlt,
  faUser,
  faUserPlus,
  faUsers,
  faWrench,
  faBookOpen
} from '@fortawesome/free-solid-svg-icons';
import {HomeComponent} from './layout/home/home.component';
import {AboutUsComponent} from './layout/about-us/about-us.component';
import {NutritionComponent} from './layout/nutrition/nutrition.component';
import {GymInfoComponent} from './layout/gym-info/gym-info.component';
import {AccountModule} from './account/account.module';
import {SignUpComponent} from './sign-up/sign-up.component';
import {ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AdminModule} from './admin/admin.module';
import {TrainingModule} from './training/training.module';
import localeHu from '@angular/common/locales/hu';
import {registerLocaleData} from '@angular/common';
import {AuthorizationGuard} from './core/guard/authorization-guard.service';
import {ArticleModule} from './article/article.module';
import {TimelineComponent} from './layout/home/timeline/timeline.component';
import {CKEditorModule} from '@ckeditor/ckeditor5-angular';
import {SharedModule} from './shared/shared.module';
import {ToolModule} from './tool/tool.module';
import {ReservationModule} from './reservation/reservation.module';
import {RulesComponent} from "./layout/rules/rules.component";
import {HttpXsrfInterceptor} from "./core/interceptor/http-xsrf.interceptor";

registerLocaleData(localeHu);

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
library.add(faHandPointDown);
library.add(faEdit);
library.add(faTrashAlt);
library.add(faCalendar);
library.add(faClipboardList);
library.add(faChevronDown);
library.add(faChevronUp);
library.add(faRunning);
library.add(faNewspaper);
library.add(faEnvelope);
library.add(faBookOpen);

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    HomeComponent,
    AboutUsComponent,
    NutritionComponent,
    GymInfoComponent,
    SignUpComponent,
    TimelineComponent,
    RulesComponent
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
    ToolModule,
    ReservationModule,
    BrowserAnimationsModule,
    AdminModule,
    TrainingModule
  ],
  providers: [
    AuthorizationGuard,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: HttpXsrfInterceptor, multi: true},
    {provide: LOCALE_ID, useValue: 'hu-HU'},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
