import { registerLocaleData } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import localeEnglish from '@angular/common/locales/en';
import localeHungarian from '@angular/common/locales/hu';
import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FaIconLibrary, FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
    faBookOpen,
    faDrumstickBite,
    faDumbbell,
    faListUl,
    faPlus,
    faRunning,
    faUsers,
    faNewspaper,
    faEnvelope,
    faUserPlus,
    faSignInAlt,
    faSignOutAlt,
    faUser,
    faWrench,
    faLock,
    faFlag
} from '@fortawesome/free-solid-svg-icons';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CultureService } from './core/services/culture.service';
import { HeaderComponent } from './header/header.component';
import { SharedModule } from './shared/shared.module';

registerLocaleData(localeHungarian, 'hu-HU');
registerLocaleData(localeEnglish, 'en-US');

export const getLocaleId: (cultureService: CultureService) => string =
    (cultureService: CultureService) => cultureService.getLocale();

export const createTranslateLoader: (http: HttpClient) => TranslateHttpLoader = (http: HttpClient) =>
    new TranslateHttpLoader(http, './assets/i18n/', '.json');

@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: createTranslateLoader,
                deps: [HttpClient]
            }
        }),
        HttpClientModule,
        SharedModule,
        FontAwesomeModule
    ],
    providers: [
        {
            provide: LOCALE_ID,
            deps: [CultureService],
            useFactory: getLocaleId
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
    constructor(library: FaIconLibrary) {
        // Add an icon to the library for convenient access in other components
        library.addIcons(
            faUsers,
            faFlag,
            faLock,
            faWrench,
            faSignOutAlt,
            faSignInAlt,
            faUserPlus,
            faEnvelope,
            faDrumstickBite,
            faDumbbell,
            faBookOpen,
            faListUl,
            faPlus,
            faRunning,
            faUser,
            faNewspaper
        );
    }
}
