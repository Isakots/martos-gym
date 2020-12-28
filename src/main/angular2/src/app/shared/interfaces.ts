export interface AccountModel {
    id: string;
    authorities: Array<string>;
}

export interface LoginResponse {
    token: string;
}
