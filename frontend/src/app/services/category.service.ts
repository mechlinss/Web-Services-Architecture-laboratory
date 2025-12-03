import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Category, CategoryCreate } from '../models/category.model';
import { Element } from '../models/element.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private readonly baseUrl = `${environment.apiBaseUrl}/api/employee-roles`;
  private readonly employeesUrl = `${environment.apiBaseUrl}/api/employees`;

  constructor(private http: HttpClient) {}

  list(): Observable<Category[]> {
    return this.http.get<Category[]>(this.baseUrl).pipe(
      catchError(this.handleError)
    );
  }

  get(id: string): Observable<Category> {
    return this.http.get<Category>(`${this.baseUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  create(dto: CategoryCreate): Observable<Category> {
    return this.http.post<Category>(this.baseUrl, dto).pipe(
      catchError(this.handleError)
    );
  }

  update(id: string, dto: CategoryCreate): Observable<Category> {
    return this.http.put<Category>(`${this.baseUrl}/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getElements(categoryId: string): Observable<Element[]> {
    return this.http.get<Element[]>(`${this.employeesUrl}/roles/${categoryId}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('CategoryService error:', error);
    let message = 'An error occurred';
    if (error.error instanceof ErrorEvent) {
      message = error.error.message;
    } else {
      message = `Server error: ${error.status} - ${error.message}`;
    }
    alert(message);
    return throwError(() => new Error(message));
  }
}
