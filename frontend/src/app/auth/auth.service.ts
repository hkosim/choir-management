import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { LoginRequest } from './model/login-request.model';
import { LoginResponse } from './model/login-response.model';
import { tap, catchError, throwError, timeout, delay, Observable } from 'rxjs';
import { Router } from '@angular/router';

/**
 * Service for user-related API operations.
 */
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = environment.apiUrl; // Dynamic URL based on environment

  roles: String[] = [];

  private httpClient = inject(HttpClient);
  private router = inject(Router);

  public getUsername():string {
    return sessionStorage.getItem('username')!;
  }

  /**
   * Send a login request.
   * @param loginRequest <LoginRequest> (username and password)
   * @returns An observable of the login result.
   */
  public login(loginRequest: LoginRequest) {
    // TODO: Check if user logged in with INTERCEPTOR

    return this.httpClient
      .post<LoginResponse>(this.apiUrl + '/api/members/login', {
        username: loginRequest.username,
        password: loginRequest.password,
      })
      .pipe(
        delay(1000),
        timeout(2000 + 1000),
        tap({
          next: (loginResponse: LoginResponse) => {
            // Save login details
            sessionStorage.setItem('username', loginResponse.username);
            sessionStorage.setItem('token', loginResponse.token);
            this.roles = loginResponse.roles;
          },
        }),
        catchError((httpErrorResponse: HttpErrorResponse) => {
          return throwError(() => httpErrorResponse.error);
        })
      );
  }

  /**
   * Fetches the roles of given username.
   * @param username
   * @returns An observable of the roles.
   */
  public getRoles(username: string): Observable<String[]> {
    // TODO: Check if user logged in with guards
    const token = sessionStorage.getItem('token');

    return this.httpClient
      .get<String[]>(this.apiUrl + '/api/members/roles/' + username, {
        headers: new HttpHeaders().set('Authorization', 'Basic ' + token!),
      })
      .pipe(
        delay(1000),
        timeout(2000 + 1000),
        tap({
          next: (roles: String[]) => {
            console.log(roles);
          },
        }),
        catchError((httpErrorResponse: HttpErrorResponse) => {
          return throwError(() => httpErrorResponse.error);
        })
      );
  }

  /**
   * Send a logout request.
   */
  public logout() {
    sessionStorage.clear();
    this.router.navigate(['auth', 'login']);
  }
}
