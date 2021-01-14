export class Role {
  id: string;
  name: string;
}

export class User {
  id: string;
  username: string;
  firstName: string;
  lastName?: string;
  email: string;
  roles: string[];
  token?: string;
  activated?: boolean;
  imageUrl?: string;
}
