import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from '../../models/category.model';
import { Element } from '../../models/element.model';
import { CategoryService } from '../../services/category.service';
import { ElementService } from '../../services/element.service';

@Component({
  selector: 'app-category-details',
  templateUrl: './category-details.component.html',
  styleUrls: ['./category-details.component.css']
})
export class CategoryDetailsComponent implements OnInit {
  category: Category | null = null;
  elements: Element[] = [];
  categoryId: string = '';
  loading = false;

  constructor(
    private categoryService: CategoryService,
    private elementService: ElementService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.categoryId = this.route.snapshot.paramMap.get('categoryId') || '';
    if (this.categoryId) {
      this.loadCategory();
      this.loadElements();
    }
  }

  loadCategory(): void {
    this.loading = true;
    this.categoryService.get(this.categoryId).subscribe({
      next: (data) => {
        this.category = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  loadElements(): void {
    this.categoryService.getElements(this.categoryId).subscribe({
      next: (data) => {
        this.elements = data;
      }
    });
  }

  backToList(): void {
    this.router.navigate(['/categories']);
  }

  addElement(): void {
    this.router.navigate(['/categories', this.categoryId, 'elements', 'add']);
  }

  editElement(elementId: string): void {
    this.router.navigate(['/categories', this.categoryId, 'elements', elementId, 'edit']);
  }

  viewElementDetails(elementId: string): void {
    this.router.navigate(['/categories', this.categoryId, 'elements', elementId]);
  }

  deleteElement(elementId: string): void {
    if (confirm('Are you sure you want to delete this element?')) {
      this.elementService.delete(this.categoryId, elementId).subscribe({
        next: () => {
          this.loadElements();
        }
      });
    }
  }
}
