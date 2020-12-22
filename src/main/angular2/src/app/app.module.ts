import { registerLocaleData } from '@angular/common';
import localeEnglish from '@angular/common/locales/en';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import localeHungarian from '@angular/common/locales/hu';
import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CultureService } from './core/services/culture.service';

registerLocaleData(localeHungarian, 'hu-HU');
registerLocaleData(localeEnglish, 'en-US');

export const getLocaleId: (cultureService: CultureService) => string =
    (cultureService: CultureService) => cultureService.getLocale();

export const createTranslateLoader: (http: HttpClient) => TranslateHttpLoader = (http: HttpClient) =>
    new TranslateHttpLoader(http, './assets/i18n/', '.json');

@NgModule({
    declarations: [
        AppComponent
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
        HttpClientModule
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
}
