export interface UserWithRoles {
  username: string;
  authorities: string[];
}

export interface LoginResponse {
  token: string;
  userWithRoles: UserWithRoles;
}
