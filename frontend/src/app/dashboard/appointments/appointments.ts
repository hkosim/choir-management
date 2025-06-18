import {
  ChangeDetectionStrategy,
  Component,
  DestroyRef,
  inject,
  input,
  OnInit,
} from '@angular/core';
import { AppointmentService } from './service/appointment.service';
import { AppointmentPage } from './model/appointment-page.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { Appointment as AppointmentModel } from './model/appointment.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Appointment as AppointmentComponent } from './components/appointment/appointment';
import { errorContext } from 'rxjs/internal/util/errorContext';

@Component({
  selector: 'app-appointments',
  imports: [
    AppointmentComponent,
    MatCardModule,
    MatButtonModule,
    MatButtonToggleModule,
    FormsModule,
    ReactiveFormsModule,
    MatIconModule,
    MatPaginatorModule,
    MatTooltipModule,
  ],
  templateUrl: './appointments.html',
  styleUrl: './appointments.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Appointments implements OnInit {
  private formBuilder = inject(FormBuilder);
  private appointmentService = inject(AppointmentService);
  private destroyRef = inject(DestroyRef);
  private snackBar = inject(MatSnackBar);

  appointmentPage = input.required<AppointmentPage>();

  ngOnInit(): void {}

  onChangePage() {
    this.snackBar.open('Page change', 'close');
  }

  // Save the attendance in database
  saveAttendance(updatedAppointment: AppointmentModel) {
    // Model the updated appointment
    const subscription = this.appointmentService
      .updateAppointment(updatedAppointment)
      .subscribe({
        next: () => {
          // Popup
          this.snackBar.open('Appointment saved.', 'close');
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
