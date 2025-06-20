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
  private destroyRef = inject(DestroyRef);
  private snackBar = inject(MatSnackBar);
  private router = inject(Router);

  appointmentAttendancePage = input.required<AppointmentAttendancePage>();

  ngOnInit(): void {}

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

  submit() {
    console.log('submit');
  }
}
