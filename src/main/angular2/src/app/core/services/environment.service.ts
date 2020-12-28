import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class EnvironmentService {

    readonly contextRoot: string;
    readonly apiUrl: string;

    constructor() {
        this.contextRoot = document.baseURI;
        this.apiUrl = `${this.contextRoot}api`;
    }

}
