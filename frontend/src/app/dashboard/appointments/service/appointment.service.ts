import { inject, Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { tap, catchError, throwError, timeout, delay } from 'rxjs';
import { AppointmentPage } from '../model/appointment-page.model';
import { environment } from '../../../../environments/environment';
import { Appointment } from '../model/appointment.model';
import { AttendanceUpdateRequest } from '../model/attendance-update-request.model';

/**
 * Service for user-related API operations.
 */
@Injectable({
  providedIn: 'root',
})
export class AppointmentService {
  private apiUrl = environment.apiUrl; // Dynamic URL based on environment

  private httpClient = inject(HttpClient);

  /**
   * Fetch a page of appointments.
   * @returns An observable of the appointments page.
   */
  public getAppointments() {
    // TODO: Check if user logged in with INTERCEPTOR
    const token = sessionStorage.getItem('token');
    const username = sessionStorage.getItem('username');
    return this.httpClient
      .get<AppointmentPage>(this.apiUrl + '/api/appointments', {
        headers: new HttpHeaders().set('Authorization', 'Basic ' + token!),
        params: {
          username: username!,
        },
      })
      .pipe(
        timeout(2000),
        tap({
          next: (appointmentPage: AppointmentPage) => {},
        }),
        catchError((httpErrorResponse: HttpErrorResponse) => {
          return throwError(() => httpErrorResponse.error);
        })
      );
  }

  /**
   * Update an changed
   * @param updatedAppointment an object of updated appointment.
   * @returns An observable of the saved appointment.
   */
  public updateAppointment(updatedAppointment: Appointment) {
    // TODO: Check if user logged in with INTERCEPTOR
    const token = sessionStorage.getItem('token');
    const username = sessionStorage.getItem('username');

    const attendanceUpdateRequest: AttendanceUpdateRequest = {
      username: username!,
      id: updatedAppointment.id,
      type: updatedAppointment.type,
      present: updatedAppointment.present,
    };

    return this.httpClient
      .post<Appointment>(
        this.apiUrl + '/api/appointments/save',
        attendanceUpdateRequest,
        {
          headers: new HttpHeaders().set('Authorization', 'Basic ' + token),
        }
      )
      .pipe(
        tap({
          next: (appointmentPage: Appointment) => {},
        }),
        catchError((httpErrorResponse: HttpErrorResponse) => {
          return throwError(() => httpErrorResponse.error);
        })
      );
  }
}
