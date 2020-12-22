import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
    isNavbarCollapsed = true;

    constructor(private readonly translate: TranslateService) {
    }

    collapseNavbar(): void {
        this.isNavbarCollapsed = true;
    }

    toggleNavbar(): void {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    setLanguage(lang: string): void {
        this.translate.use(lang);
    }
}
