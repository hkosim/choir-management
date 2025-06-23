import { Component, DestroyRef, inject, input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AppointmentAttendancePage } from '../../model/appointment-attendance-page.model';
import { AppointmentService } from '../../service/appointment.service';
import { MatPaginatorModule } from '@angular/material/paginator';
import { Appointment } from '../appointment/appointment';
import { AppointmentAttendance } from '../../model/appointment-attendance.model';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../../../auth/auth.service';
import { UserRole } from '../../../../auth/enums/user-role.enum';

@Component({
  selector: 'app-appointments-list',
  imports: [
    MatPaginatorModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    Appointment,
  ],
  templateUrl: './appointments-list.html',
  styleUrl: './appointments-list.scss',
})
export class AppointmentsList implements OnInit {
  private appointmentService = inject(AppointmentService);
  private authService = inject(AuthService);
  private destroyRef = inject(DestroyRef);
  private snackBar = inject(MatSnackBar);
  private router = inject(Router);

  appointmentAttendancePage = input.required<AppointmentAttendancePage>();
  UserRole = UserRole;
  userRole: UserRole = UserRole.USER;

  ngOnInit(): void {
    const role: String[] = this.authService.roles;
    if (role.includes('ROLE_ADMIN')) {
      this.userRole = UserRole.ADMIN;
    }
  }

  onChangePage() {
    this.snackBar.open('Page change', 'close', {
      duration: 3000,
    });
  }

  // Save the attendance in database
  saveAttendance(updatedAppointmentAttendance: AppointmentAttendance) {
    // Model the updated member attendance
    const subscription = this.appointmentService
      .updateAppointmentAttendance(updatedAppointmentAttendance)
      .subscribe({
        next: () => {
          // Popup
          this.snackBar.open('Attendance saved.', 'close', {
            duration: 3000,
          });
        },
        error: (error: Error) => {
          console.log(error);
        },
      });

    this.destroyRef.onDestroy(() => subscription.unsubscribe());
  }

  // Navigation
  navigateTo(route: string) {
    this.router.navigate(['dashboard', 'appointments', route]);
  }
}
