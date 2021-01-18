import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import jwtDecode from 'jwt-decode';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ACCESS_TOKEN_KEY } from '../../shared/constants';
import { UserWithRoles, LoginResponse } from '../../shared/interfaces';
import { EnvironmentService } from './environment.service';

@Injectable({providedIn: 'root'})
export class AccountService {
    private readonly authenticationState = new BehaviorSubject<string | undefined>(undefined);

    constructor(private readonly http: HttpClient,
                private readonly router: Router,
                private readonly environmentService: EnvironmentService) {
    }

    getAuthenticationState(): string {
        return this.authenticationState.getValue() as string;
    }

    getAuthenticationStateObs(): Observable<string | undefined> {
        return this.authenticationState.asObservable();
    }

    login(loginDto: { username: string; password: string }): Observable<any> {
        // eslint-disable-next-line id-blacklist
        return this.http.post<LoginResponse>(`${this.environmentService.apiUrl}/login`, loginDto)
            .pipe(
                tap(response => {
                    const token = response.token;
                    localStorage.setItem(ACCESS_TOKEN_KEY, JSON.stringify(response));
                    this.authenticationState.next(token);
                })
            );
    }

    logout(): void {
        this.authenticationState.next(undefined);
        localStorage.clear();
        this.router.navigate(['/']);
    }

    autoLogin(): void {
        const tokenJSON = localStorage.getItem(ACCESS_TOKEN_KEY);
        const token = !!tokenJSON ? JSON.parse(tokenJSON).token : undefined;
        this.authenticationState.next(token);
    }

    // todo autologout (on expired jwt token)

    isAuthenticated(): boolean {
        return !!this.authenticationState.getValue();
    }

    hasRole(role: string): boolean {
        if (!this.authenticationState.getValue()) {
            return false;
        } else {
            const decodedAccount = this.getDecodedAccount();
            return decodedAccount.authorities.includes(role);
        }
    }

    signup(signupForm: any): Observable<any> {
        return this.http.post(`${this.environmentService.apiUrl}/register`, signupForm);
    }

    private getDecodedAccount(): UserWithRoles {
        return jwtDecode(this.authenticationState.getValue() as string);
    }

}
