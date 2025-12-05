import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RolesListComponent } from './components/roles-list/roles-list.component';
import { RoleFormComponent } from './components/role-form/role-form.component';
import { RoleDetailsComponent } from './components/role-details/role-details.component';
import { EmployeeFormComponent } from './components/employee-form/employee-form.component';
import { EmployeeDetailsComponent } from './components/employee-details/employee-details.component';

const routes: Routes = [
  { path: '', redirectTo: '/roles', pathMatch: 'full' },
  { path: 'roles', component: RolesListComponent },
  { path: 'roles/add', component: RoleFormComponent },
  { path: 'roles/:roleId', component: RoleDetailsComponent },
  { path: 'roles/:roleId/edit', component: RoleFormComponent },
  { path: 'roles/:roleId/employees/add', component: EmployeeFormComponent },
  { path: 'roles/:roleId/employees/:employeeId', component: EmployeeDetailsComponent },
  { path: 'roles/:roleId/employees/:employeeId/edit', component: EmployeeFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
