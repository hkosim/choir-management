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
import { Performance } from '../model/performance.model';
import { Rehearsal } from '../model/rehearsal.model';
import { AppointmentAttendance } from '../model/appointment-attendance.model';
import { AppointmentType } from '../model/appointment-type.model';

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
      id: updatedAttendance.id,
      type: updatedAttendance.type,
      present: updatedAttendance.present,
    };

    return this.httpClient
      .post<Appointment>(this.apiUrl + '/save', attendanceUpdateRequest, {
        headers: new HttpHeaders().set('Authorization', 'Basic ' + token),
      })
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
   * @params type of the appointment
   * @params id of the appointment
   * @returns An observable of the Appointment.
   */
  public getAppointment(
    type: AppointmentType,
    id: string
  ): Observable<Rehearsal | Performance> {
    // TODO: Interceptor
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', 'Basic ' + token!);

    const url = `${this.apiUrl}/appointment/${type.toLowerCase()}/${id}`;

    return this.httpClient
      .get<Rehearsal | Performance>(url, { headers })
      .pipe(
        catchError((error: HttpErrorResponse) => throwError(() => error.error))
      );
  }

  /**
   * Update an appointment.
   * @params appointment the updated appointment.
   * @returns An observable of the new Appointment.
   */
  public saveAppointment(
    appointment: Rehearsal | Performance
  ): Observable<Rehearsal | Performance> {
    // TODO: Interceptor
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', 'Basic ' + token!);

    const { type, ...appointmentRequest } = appointment;
    const url = `${this.apiUrl}/${type.toLowerCase()}/save`;

    return this.httpClient
      .post<Rehearsal | Performance>(url, appointmentRequest, { headers })
      .pipe(
        tap((appointment: Rehearsal | Performance) => {
          console.log(appointment);
        }),
        catchError((error: HttpErrorResponse) => throwError(() => error.error))
      );
  }
}
