import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoriesListComponent } from './components/categories-list/categories-list.component';
import { CategoryFormComponent } from './components/category-form/category-form.component';
import { CategoryDetailsComponent } from './components/category-details/category-details.component';
import { ElementFormComponent } from './components/element-form/element-form.component';
import { ElementDetailsComponent } from './components/element-details/element-details.component';

const routes: Routes = [
  { path: '', redirectTo: '/categories', pathMatch: 'full' },
  { path: 'categories', component: CategoriesListComponent },
  { path: 'categories/add', component: CategoryFormComponent },
  { path: 'categories/:categoryId', component: CategoryDetailsComponent },
  { path: 'categories/:categoryId/edit', component: CategoryFormComponent },
  { path: 'categories/:categoryId/elements/add', component: ElementFormComponent },
  { path: 'categories/:categoryId/elements/:elementId', component: ElementDetailsComponent },
  { path: 'categories/:categoryId/elements/:elementId/edit', component: ElementFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
