export interface Role {
  id: string;
  name: string;
  department?: string;
  description?: string;
}

export interface RoleCreate {
  name: string;
  department?: string;
  description?: string;
}
