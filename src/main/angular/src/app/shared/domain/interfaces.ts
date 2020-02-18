export class Authority {
  name: string
  constructor(name: string) {this.name = name}
}

export interface UserWithRoles {
  username: string,
  authorities: Authority[]
}

export interface LoginResponse {
  token: string,
  userWithRoles: UserWithRoles
}
