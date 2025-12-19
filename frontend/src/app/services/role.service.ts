import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Role, RoleCreate } from '../models/role.model';
import { Employee } from '../models/employee.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private readonly baseUrl = `${environment.apiBaseUrl}/employee-roles`;
  private readonly employeesUrl = `${environment.apiBaseUrl}/employees`;

  constructor(private http: HttpClient) {}

  list(): Observable<Role[]> {
    return this.http.get<Role[]>(this.baseUrl).pipe(
      catchError(this.handleError)
    );
  }

  get(id: string): Observable<Role> {
    return this.http.get<Role>(`${this.baseUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  create(dto: RoleCreate): Observable<Role> {
    return this.http.post<Role>(this.baseUrl, dto).pipe(
      catchError(this.handleError)
    );
  }

  update(id: string, dto: RoleCreate): Observable<Role> {
    return this.http.put<Role>(`${this.baseUrl}/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getEmployees(roleId: string): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.employeesUrl}/roles/${roleId}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('RoleService error:', error);
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
