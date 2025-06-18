import { Component, DestroyRef, inject, input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AppointmentPage } from '../../model/appointment-page.model';
import { Appointment as AppointmentModel } from '../../model/appointment.model';
import { AppointmentService } from '../../service/appointment.service';
import { MatPaginatorModule } from '@angular/material/paginator';
import { Appointment } from '../appointment/appointment';

@Component({
  selector: 'app-appointments-list',
  imports: [MatPaginatorModule, Appointment],
  templateUrl: './appointments-list.html',
  styleUrl: './appointments-list.scss',
})
export class AppointmentsList implements OnInit {
  private appointmentService = inject(AppointmentService);
  private destroyRef = inject(DestroyRef);
  private snackBar = inject(MatSnackBar);

  appointmentPage = input.required<AppointmentPage>();

  ngOnInit(): void {}

  onChangePage() {
    this.snackBar.open('Page change', 'close', {
      duration: 3000,
    });
  }

  // Save the attendance in database
  saveAttendance(updatedAttendance: AppointmentModel) {
    // Model the updated appointment
    const subscription = this.appointmentService
      .updateAttendance(updatedAttendance)
      .subscribe({
        next: () => {
          // Popup
          this.snackBar.open('Appointment saved.', 'close', {
            duration: 3000,
          });
        },
        error: (error: Error) => {
          console.log(error);
        },
      });

    this.destroyRef.onDestroy(() => subscription.unsubscribe());
  }

  submit() {
    console.log('submit');
  }
}
