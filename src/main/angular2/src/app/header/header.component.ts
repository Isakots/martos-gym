import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { AccountService } from '../core/services/account.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
    isNavbarCollapsed = true;

    constructor(private readonly translate: TranslateService,
                private readonly accountService: AccountService) {
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

    logout(): void {
        this.accountService.logout();
    }
}
