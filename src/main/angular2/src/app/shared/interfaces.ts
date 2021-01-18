import { ArticleType } from './constants';

export interface UserWithRoles {
    id: string;
    authorities: Array<string>;
}

export interface LoginResponse {
    token: string;
}

export interface Article {
    id: string;
    title: string;
    type: ArticleType;
    introduction: string;
    content: string;
    createdDate: string;
}

export interface AccountModel {
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    studentStatus: boolean;
    institution: string;
    faculty: string;
    collegian: boolean;
    roomNumber: number;
    subscriptions: Array<string>;
}
