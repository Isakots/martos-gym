import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountModel, UserWithRoles } from '../../shared/interfaces';
import { EnvironmentService } from './environment.service';

@Injectable({
    providedIn: 'root'
})
export class ProfileService {
    readonly REGISTER = '/register';

    constructor(private readonly http: HttpClient,
                private readonly environmentService: EnvironmentService) {
    }

    registration(userData: any): Observable<any> {
        return this.http.post<HttpResponse<any>>(this.environmentService.apiUrl + this.REGISTER, userData);
    }

    fetch(): Observable<HttpResponse<UserWithRoles>> {
        return this.http.get<UserWithRoles>(`${this.environmentService.apiUrl}/identity`, {observe: 'response'});
    }

    get(): Observable<HttpResponse<AccountModel>> {
        return this.http.get<AccountModel>(`${this.environmentService.apiUrl}/profile`, {observe: 'response'});
    }

    updateProfile(param: any): Observable<HttpResponse<UserWithRoles>> {
        return this.http.put<UserWithRoles>(`${this.environmentService.apiUrl}/profile`, param, {observe: 'response'});
    }

    changePassword(param: any): Observable<any> {
        return this.http.post(`${this.environmentService.apiUrl}/profile/change-password`, param, {observe: 'response'});
    }
}
