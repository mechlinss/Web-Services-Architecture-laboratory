import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Role } from '../../models/role.model';
import { RoleService } from '../../services/role.service';

@Component({
  selector: 'app-roles-list',
  templateUrl: './roles-list.component.html',
  styleUrls: ['./roles-list.component.css']
})
export class RolesListComponent implements OnInit {
  roles: Role[] = [];
  loading = false;

  constructor(
    private roleService: RoleService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadRoles();
  }

  loadRoles(): void {
    this.loading = true;
    this.roleService.list().subscribe({
      next: (data) => {
        this.roles = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  addRole(): void {
    this.router.navigate(['/roles/add']);
  }

  editRole(id: string): void {
    this.router.navigate(['/roles', id, 'edit']);
  }

  viewDetails(id: string): void {
    this.router.navigate(['/roles', id]);
  }

  deleteRole(id: string): void {
    if (confirm('Are you sure you want to delete this role?')) {
      this.roleService.delete(id).subscribe({
        next: () => {
          this.loadRoles();
        }
      });
    }
  }
}
