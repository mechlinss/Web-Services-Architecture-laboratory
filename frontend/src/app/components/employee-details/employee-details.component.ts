import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from '../../models/employee.model';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.css']
})
export class EmployeeDetailsComponent implements OnInit {
  employee: Employee | null = null;
  roleId: string = '';
  employeeId: string = '';
  loading = false;

  constructor(
    private employeeService: EmployeeService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.roleId = this.route.snapshot.paramMap.get('roleId') || '';
    this.employeeId = this.route.snapshot.paramMap.get('employeeId') || '';
    
    if (this.roleId && this.employeeId) {
      this.loadEmployee();
    }
  }

  loadEmployee(): void {
    this.loading = true;
    this.employeeService.get(this.roleId, this.employeeId).subscribe({
      next: (data) => {
        this.employee = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  backToRole(): void {
    this.router.navigate(['/roles', this.roleId]);
  }

  editEmployee(): void {
    this.router.navigate(['/roles', this.roleId, 'employees', this.employeeId, 'edit']);
  }
}
