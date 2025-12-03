import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Element, ElementCreate } from '../models/element.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ElementService {
  private readonly baseUrl = `${environment.apiBaseUrl}/api/employees`;

  constructor(private http: HttpClient) {}

  get(categoryId: string, elementId: string): Observable<Element> {
    return this.http.get<Element>(`${this.baseUrl}/${elementId}`).pipe(
      catchError(this.handleError)
    );
  }

  create(categoryId: string, dto: ElementCreate): Observable<Element> {
    return this.http.post<Element>(this.baseUrl, dto).pipe(
      catchError(this.handleError)
    );
  }

  update(categoryId: string, elementId: string, dto: ElementCreate): Observable<Element> {
    return this.http.put<Element>(`${this.baseUrl}/${elementId}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  delete(categoryId: string, elementId: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${elementId}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('ElementService error:', error);
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
