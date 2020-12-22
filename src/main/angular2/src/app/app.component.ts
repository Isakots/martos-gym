import { Component, OnInit } from '@angular/core';
import { AccountService } from './core/services/account.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
    constructor(private readonly accountService: AccountService) {
    }

    ngOnInit(): void {
        this.accountService.autoLogin();
    }
}
