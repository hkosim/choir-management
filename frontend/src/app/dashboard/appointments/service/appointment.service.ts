import { inject, Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { tap, catchError, throwError, timeout, Observable } from 'rxjs';
import { AppointmentAttendancePage } from '../model/appointment-attendance-page.model';
import { environment } from '../../../../environments/environment';
import { Appointment } from '../model/appointment.model';
import { AppointmentAttendanceUpdateRequest } from '../model/appointment-attendance-update-request.model';
import { AppointmentAttendance } from '../model/appointment-attendance.model';

/**
 * Service for user-related API operations.
 */
@Injectable({
  providedIn: 'root',
})
export class AppointmentService {
  private apiUrl = environment.apiUrl + '/api/appointments'; // Dynamic URL based on environment

  private httpClient = inject(HttpClient);

  /**
   * Fetch a page of appointments with member attendances.
   * @returns An observable of the appointments page.
   */
  public getAppointmentAttendancePage() {
    // TODO: Check if user logged in with INTERCEPTOR
    const token = sessionStorage.getItem('token');
    const username = sessionStorage.getItem('username');

    const url: string = `${this.apiUrl}/attendances`;

    return this.httpClient
      .get<AppointmentAttendancePage>(url, {
        headers: new HttpHeaders().set('Authorization', 'Basic ' + token!),
        params: {
          username: username!,
        },
      })
      .pipe(
        timeout(2000),
        tap({
          next: () => {},
        }),
        catchError((httpErrorResponse: HttpErrorResponse) => {
          return throwError(() => httpErrorResponse.error);
        })
      );
  }

  /**
   * Update an changed attendance for an appointment
   * @param updatedAttendance an object of updated appointment.
   * @returns An observable of the saved appointment.
   */
  public updateAppointmentAttendance(updatedAttendance: AppointmentAttendance) {
    // TODO: Check if user logged in with INTERCEPTOR
    const token = sessionStorage.getItem('token');
    const username = sessionStorage.getItem('username');

    const attendanceUpdateRequest: AppointmentAttendanceUpdateRequest = {
      username: username!,
      id: updatedAttendance.appointment.id,
      attendanceStatus: updatedAttendance.attendanceStatus,
    };
    console.log(attendanceUpdateRequest);
    return this.httpClient
      .post<Appointment>(
        this.apiUrl + '/attendance/save',
        attendanceUpdateRequest,
        {
          headers: new HttpHeaders().set('Authorization', 'Basic ' + token),
        }
      )
      .pipe(
        tap({
          next: () => {},
        }),
        catchError((httpErrorResponse: HttpErrorResponse) => {
          return throwError(() => httpErrorResponse.error);
        })
      );
  }

  /**
   * Fetch an appointment
   * @params id of the appointment
   * @returns An observable of the Appointment.
   */
  public getAppointment(id: string): Observable<Appointment> {
    // TODO: Interceptor
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', 'Basic ' + token!);

    const url = `${this.apiUrl}/appointment/${id}`;

    return this.httpClient
      .get<Appointment>(url, { headers })
      .pipe(
        catchError((error: HttpErrorResponse) => throwError(() => error.error))
      );
  }

  /**
   * Update an appointment.
   * @params appointment the updated appointment.
   * @returns An observable of the new Appointment.
   */
  public saveAppointment(appointment: Appointment): Observable<Appointment> {
    // TODO: Interceptor
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', 'Basic ' + token!);

    const url = `${this.apiUrl}/save`;

    return this.httpClient
      .post<Appointment>(url, appointment, { headers })
      .pipe(
        tap((appointment: Appointment) => {
          console.log(appointment);
        }),
        catchError((error: HttpErrorResponse) => throwError(() => error.error))
      );
  }
}
