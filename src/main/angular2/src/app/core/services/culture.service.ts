import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
    providedIn: 'root'
})
export class CultureService {

    constructor(private readonly translate: TranslateService) {
        this.setLanguage(); // todo lang selector
    }

    setLanguage(): void {
        const defaultLang = 'hu-HU';
        this.translate.setDefaultLang(defaultLang);
        this.translate.use(defaultLang);
    }

    getLocale(): string {
        return this.translate.defaultLang;
    }

}
