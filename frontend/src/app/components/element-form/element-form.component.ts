import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ElementService } from '../../services/element.service';

@Component({
  selector: 'app-element-form',
  templateUrl: './element-form.component.html',
  styleUrls: ['./element-form.component.css']
})
export class ElementFormComponent implements OnInit {
  elementForm: FormGroup;
  isEditMode = false;
  categoryId: string = '';
  elementId: string | null = null;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private elementService: ElementService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.elementForm = this.fb.group({
      name: ['', Validators.required],
      surname: [''],
      salary: [null],
      phoneNumber: ['']
    });
  }

  ngOnInit(): void {
    this.categoryId = this.route.snapshot.paramMap.get('categoryId') || '';
    this.elementId = this.route.snapshot.paramMap.get('elementId');
    
    if (this.elementId) {
      this.isEditMode = true;
      this.loadElement();
    }
  }

  loadElement(): void {
    if (!this.categoryId || !this.elementId) return;
    this.loading = true;
    this.elementService.get(this.categoryId, this.elementId).subscribe({
      next: (element) => {
        this.elementForm.patchValue({
          name: element.name,
          surname: element.surname || '',
          salary: element.salary,
          phoneNumber: element.phoneNumber || ''
        });
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.elementForm.invalid) {
      return;
    }

    const formValue = this.elementForm.value;
    const dto = {
      ...formValue,
      employeeRoleId: this.categoryId
    };

    if (this.isEditMode && this.elementId) {
      this.elementService.update(this.categoryId, this.elementId, dto).subscribe({
        next: () => {
          this.router.navigate(['/categories', this.categoryId]);
        }
      });
    } else {
      this.elementService.create(this.categoryId, dto).subscribe({
        next: () => {
          this.router.navigate(['/categories', this.categoryId]);
        }
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/categories', this.categoryId]);
  }
}
