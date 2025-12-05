export interface Employee {
  id: string;
  name: string;
  surname?: string;
  roleName?: string;
  salary?: number;
  phoneNumber?: string;
}

export interface EmployeeCreate {
  name: string;
  surname?: string;
  salary?: number;
  phoneNumber?: string;
  employeeRoleId: string;
}
