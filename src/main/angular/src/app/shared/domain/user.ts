import {Authority} from "./interfaces";

export interface User {
  id: number,
  email: string,
  firstname: string,
  lastname: string,
  authorities: Authority[]
}
