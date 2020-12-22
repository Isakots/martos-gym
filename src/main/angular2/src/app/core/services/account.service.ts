import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import jwtDecode from 'jwt-decode';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AccountModel } from '../../shared/interfaces';
import { EnvironmentService } from './environment.service';

@Injectable({providedIn: 'root'})
export class AccountService {
    private readonly ACCESS_TOKEN_KEY = 'accessToken';
    private readonly authenticationState = new BehaviorSubject<string | undefined>(undefined);

    constructor(private readonly http: HttpClient,
                private readonly router: Router,
                private readonly environmentService: EnvironmentService) {
    }

    getAuthenticationState(): Observable<string> {
        return this.authenticationState.asObservable();
    }

    login(loginDto: { username: string; password: string }): Observable<any> {
        // eslint-disable-next-line id-blacklist
        return this.http.post<{ string: string }>(`${this.environmentService.apiUrl}/login`, loginDto)
            .pipe(
                tap(response => {
                    const token = response.accessToken;
                    localStorage.setItem(this.ACCESS_TOKEN_KEY, JSON.stringify(response));
                    this.authenticationState.next(token);
                })
            );
    }

    logout(): void {
        this.authenticationState.next(undefined);
        localStorage.clear();
        this.router.navigate(['/login']);
    }

    autoLogin(): void {
        const tokenJSON = localStorage.getItem(this.ACCESS_TOKEN_KEY);
        const token = !!tokenJSON ? JSON.parse(tokenJSON).accessToken : undefined;
        this.authenticationState.next(token);
    }

    // todo autologout (on expired jwt token)

    isAuthenticated(): boolean {
        return undefined !== this.authenticationState.getValue();
    }

    hasRole(role: string): boolean {
        if (!this.authenticationState.getValue()) {
            return false;
        } else {
            const decodedAccount = this.getDecodedAccount();
            return decodedAccount.roles.includes(role);
        }
    }

    private getDecodedAccount(): AccountModel {
        return jwtDecode(this.authenticationState.getValue() as string);
    }

}
