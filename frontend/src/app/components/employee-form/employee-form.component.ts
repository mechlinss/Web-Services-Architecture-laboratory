import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-employee-form',
  templateUrl: './employee-form.component.html',
  styleUrls: ['./employee-form.component.css']
})
export class EmployeeFormComponent implements OnInit {
  employeeForm: FormGroup;
  isEditMode = false;
  roleId: string = '';
  employeeId: string | null = null;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.employeeForm = this.fb.group({
      name: ['', Validators.required],
      surname: [''],
      salary: [0],
      phoneNumber: ['']
    });
  }

  ngOnInit(): void {
    this.roleId = this.route.snapshot.paramMap.get('roleId') || '';
    this.employeeId = this.route.snapshot.paramMap.get('employeeId');
    
    if (this.employeeId) {
      this.isEditMode = true;
      this.loadEmployee();
    }
  }

  loadEmployee(): void {
    if (!this.roleId || !this.employeeId) return;
    this.loading = true;
    this.employeeService.get(this.roleId, this.employeeId).subscribe({
      next: (employee) => {
        this.employeeForm.patchValue({
          name: employee.name,
          surname: employee.surname || '',
          salary: employee.salary,
          phoneNumber: employee.phoneNumber || ''
        });
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.employeeForm.invalid) {
      return;
    }

    const formValue = this.employeeForm.value;
    const dto = {
      ...formValue,
      employeeRoleId: this.roleId
    };

    if (this.isEditMode && this.employeeId) {
      this.employeeService.update(this.roleId, this.employeeId, dto).subscribe({
        next: () => {
          this.router.navigate(['/roles', this.roleId]);
        }
      });
    } else {
      this.employeeService.create(this.roleId, dto).subscribe({
        next: () => {
          this.router.navigate(['/roles', this.roleId]);
        }
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/roles', this.roleId]);
  }
}
