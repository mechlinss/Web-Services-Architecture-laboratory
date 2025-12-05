import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Role } from '../../models/role.model';
import { Employee } from '../../models/employee.model';
import { RoleService } from '../../services/role.service';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-role-details',
  templateUrl: './role-details.component.html',
  styleUrls: ['./role-details.component.css']
})
export class RoleDetailsComponent implements OnInit {
  role: Role | null = null;
  employees: Employee[] = [];
  roleId: string = '';
  loading = false;

  constructor(
    private roleService: RoleService,
    private employeeService: EmployeeService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.roleId = this.route.snapshot.paramMap.get('roleId') || '';
    if (this.roleId) {
      this.loadRole();
      this.loadEmployees();
    }
  }

  loadRole(): void {
    this.loading = true;
    this.roleService.get(this.roleId).subscribe({
      next: (data) => {
        this.role = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  loadEmployees(): void {
    this.roleService.getEmployees(this.roleId).subscribe({
      next: (data) => {
        this.employees = data;
      }
    });
  }

  backToList(): void {
    this.router.navigate(['/roles']);
  }

  addEmployee(): void {
    this.router.navigate(['/roles', this.roleId, 'employees', 'add']);
  }

  editEmployee(employeeId: string): void {
    this.router.navigate(['/roles', this.roleId, 'employees', employeeId, 'edit']);
  }

  viewEmployeeDetails(employeeId: string): void {
    this.router.navigate(['/roles', this.roleId, 'employees', employeeId]);
  }

  editRole(): void {
    this.router.navigate(['/roles', this.roleId, 'edit']);
  }

  deleteEmployee(employeeId: string): void {
    if (confirm('Are you sure you want to delete this employee?')) {
      this.employeeService.delete(this.roleId, employeeId).subscribe({
        next: () => {
          this.loadEmployees();
        }
      });
    }
  }
}
