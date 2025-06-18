import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { LoginRequest } from './model/login-request.model';
import { LoginResponse } from './model/login-response.model';
import { tap, catchError, throwError, timeout, delay } from 'rxjs';
import { Router } from '@angular/router';

/**
 * Service for user-related API operations.
 */
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = environment.apiUrl; // Dynamic URL based on environment

  private httpClient = inject(HttpClient);
  private router = inject(Router);

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
            console.log(loginResponse);
            // Save login details
            sessionStorage.setItem('username', loginResponse.username);
            sessionStorage.setItem('token', loginResponse.token);
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
