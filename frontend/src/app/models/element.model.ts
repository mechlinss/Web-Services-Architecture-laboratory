export interface Element {
  id: string;
  name: string;
  surname?: string;
  roleName?: string;
  salary?: number;
  phoneNumber?: string;
}

export interface ElementCreate {
  name: string;
  surname?: string;
  salary?: number;
  phoneNumber?: string;
  employeeRoleId: string;
}
