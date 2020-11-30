export class User {
  id: string;
  username: string;
  firstName: string;
  lastName?: string;
  email: string;
  imageUrl: string;
  token?: string;
  activated?: boolean;
}
