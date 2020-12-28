import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class StateStorageService {
    private readonly PREVIOUS_URL_KEY = 'previousUrl';

    storeUrl(url: string): void {
        sessionStorage.setItem(this.PREVIOUS_URL_KEY, url);
    }

    removeUrl(): void {
        sessionStorage.removeItem(this.PREVIOUS_URL_KEY);
    }

    getUrl(): string {
        return sessionStorage.getItem(this.PREVIOUS_URL_KEY) as string;
    }
}
