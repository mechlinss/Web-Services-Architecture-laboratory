import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.css']
})
export class CategoryFormComponent implements OnInit {
  categoryForm: FormGroup;
  isEditMode = false;
  categoryId: string | null = null;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.categoryForm = this.fb.group({
      name: ['', Validators.required],
      department: [''],
      description: ['']
    });
  }

  ngOnInit(): void {
    this.categoryId = this.route.snapshot.paramMap.get('categoryId');
    if (this.categoryId) {
      this.isEditMode = true;
      this.loadCategory();
    }
  }

  loadCategory(): void {
    if (!this.categoryId) return;
    this.loading = true;
    this.categoryService.get(this.categoryId).subscribe({
      next: (category) => {
        this.categoryForm.patchValue({
          name: category.name,
          department: category.department || '',
          description: category.description || ''
        });
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.categoryForm.invalid) {
      return;
    }

    const dto = this.categoryForm.value;

    if (this.isEditMode && this.categoryId) {
      this.categoryService.update(this.categoryId, dto).subscribe({
        next: () => {
          this.router.navigate(['/categories']);
        }
      });
    } else {
      this.categoryService.create(dto).subscribe({
        next: () => {
          this.router.navigate(['/categories']);
        }
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/categories']);
  }
}
