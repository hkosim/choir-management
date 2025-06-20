import { CommonModule } from '@angular/common';
import { Component, DestroyRef, inject } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTimepickerModule } from '@angular/material/timepicker';
import { Song } from '../song/song';
import { Router } from '@angular/router';
import {
  formatDateToIso,
  formatTimeToIso,
} from '../../../../shared/utils/utils';
import { AppointmentService } from '../../service/appointment.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSelectModule } from '@angular/material/select';
import { AppointmentType } from '../../enum/appointment-type.enum';
import { Appointment } from '../../model/appointment.model';

@Component({
  selector: 'app-add-appointment',
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,

    MatCardModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTimepickerModule,
    MatIconModule,
  ],
  templateUrl: './add-appointment.html',
  styleUrl: './add-appointment.scss',
})
export class AddAppointment {
  private router = inject(Router);
  private appointmentService = inject(AppointmentService);
  private destroyRef = inject(DestroyRef);
  private snackBar = inject(MatSnackBar);

  appointmentTypes: Array<string> = Object.values(AppointmentType);

  initDate: Date = new Date(new Date().setHours(18, 0, 0, 0));

  appointmentForm: FormGroup = new FormGroup({
    type: new FormControl<AppointmentType>(
      AppointmentType.REHEARSAL,
      Validators.required
    ),
    title: new FormControl<string>('', Validators.required),
    description: new FormControl<string>(''),
    date: new FormControl<Date>(this.initDate, Validators.required),
    time: new FormControl<Date>(this.initDate, Validators.required),
    location: new FormControl<string>('', Validators.required),
    songs: new FormControl<Array<Song>>([]),
  });

  addSong() {
    // TODO
    console.log('add song');
  }

  navigateToAppointmentList() {
    this.router.navigate(['dashboard', 'appointments', 'list']);
  }

  onSubmit(): void {
    if (this.appointmentForm.invalid) {
      return;
    }

    const newAppointment: Appointment = {
      id: 0,
      appointmentType: this.appointmentForm.value.type as AppointmentType,
      title: this.appointmentForm.value.title!,
      description: this.appointmentForm.value.description!,
      date: formatDateToIso(this.appointmentForm.value.date)!,
      time: formatTimeToIso(this.appointmentForm.value.time)!,
      location: this.appointmentForm.value.location,
      songs: [],

      createdBy: '',
      createdAt: '',
      lastModifiedBy: '',
      lastModifiedAt: '',
      deletedAt: '',
    };
    console.log(newAppointment);
    const subscription = this.appointmentService
      .saveAppointment(newAppointment)
      .subscribe({
        next: () => {
          // Go back to dashboard/appointments/list
          this.snackBar.open('Appointment saved successfully!', 'close', {
            duration: 3000,
          });
          this.navigateToAppointmentList();
        },
      });

    this.destroyRef.onDestroy(() => subscription.unsubscribe());
  }
}
