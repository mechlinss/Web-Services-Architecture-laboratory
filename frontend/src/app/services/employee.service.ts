import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Employee, EmployeeCreate } from '../models/employee.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private readonly baseUrl = 'http://localhost:8080/api/employees';

  constructor(private http: HttpClient) {}

  get(roleId: string, employeeId: string): Observable<Employee> {
    return this.http.get<Employee>(`${this.baseUrl}/${employeeId}`).pipe(
      catchError(this.handleError)
    );
  }

  create(roleId: string, dto: EmployeeCreate): Observable<Employee> {
    return this.http.post<Employee>(this.baseUrl, dto).pipe(
      catchError(this.handleError)
    );
  }

  update(roleId: string, employeeId: string, dto: EmployeeCreate): Observable<Employee> {
    return this.http.put<Employee>(`${this.baseUrl}/${employeeId}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  delete(roleId: string, employeeId: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${employeeId}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('EmployeeService error:', error);
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
