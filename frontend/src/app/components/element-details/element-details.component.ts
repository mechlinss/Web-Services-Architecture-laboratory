import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Element } from '../../models/element.model';
import { ElementService } from '../../services/element.service';

@Component({
  selector: 'app-element-details',
  templateUrl: './element-details.component.html',
  styleUrls: ['./element-details.component.css']
})
export class ElementDetailsComponent implements OnInit {
  element: Element | null = null;
  categoryId: string = '';
  elementId: string = '';
  loading = false;

  constructor(
    private elementService: ElementService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.categoryId = this.route.snapshot.paramMap.get('categoryId') || '';
    this.elementId = this.route.snapshot.paramMap.get('elementId') || '';
    
    if (this.categoryId && this.elementId) {
      this.loadElement();
    }
  }

  loadElement(): void {
    this.loading = true;
    this.elementService.get(this.categoryId, this.elementId).subscribe({
      next: (data) => {
        this.element = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  backToCategory(): void {
    this.router.navigate(['/categories', this.categoryId]);
  }
}
