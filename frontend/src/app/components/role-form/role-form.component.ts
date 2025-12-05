import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RoleService } from '../../services/role.service';

@Component({
  selector: 'app-role-form',
  templateUrl: './role-form.component.html',
  styleUrls: ['./role-form.component.css']
})
export class RoleFormComponent implements OnInit {
  roleForm: FormGroup;
  isEditMode = false;
  roleId: string | null = null;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private roleService: RoleService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.roleForm = this.fb.group({
      name: ['', Validators.required],
      department: [''],
      description: ['']
    });
  }

  ngOnInit(): void {
    this.roleId = this.route.snapshot.paramMap.get('roleId');
    if (this.roleId) {
      this.isEditMode = true;
      this.loadRole();
    }
  }

  loadRole(): void {
    if (!this.roleId) return;
    this.loading = true;
    this.roleService.get(this.roleId).subscribe({
      next: (role) => {
        this.roleForm.patchValue({
          name: role.name,
          department: role.department || '',
          description: role.description || ''
        });
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.roleForm.invalid) {
      return;
    }

    const dto = this.roleForm.value;

    if (this.isEditMode && this.roleId) {
      this.roleService.update(this.roleId, dto).subscribe({
        next: () => {
          this.router.navigate(['/roles']);
        }
      });
    } else {
      this.roleService.create(dto).subscribe({
        next: () => {
          this.router.navigate(['/roles']);
        }
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/roles']);
  }
}
