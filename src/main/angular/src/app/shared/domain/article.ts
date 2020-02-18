import {ArticleType} from "../enums/article-type.enum";

export class Article {
  id: string;
  title: string;
  type: ArticleType;
  introduction: string;
  content: string;
  createdDate: string;
  constructor() {}
}
