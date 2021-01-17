import { ArticleType } from './constants';

export interface AccountModel {
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
