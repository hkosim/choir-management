import {
  Component,
  DestroyRef,
  inject,
  input,
  OnInit,
  Signal,
} from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { AppointmentService } from '../../service/appointment.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTimepickerModule } from '@angular/material/timepicker';
import {
  formatDateToIso,
  formatTimeToIso,
} from '../../../../shared/utils/utils';
import { Appointment } from '../../model/appointment.model';

@Component({
  selector: 'app-edit-appointment',
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,

    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTimepickerModule,
    MatIconModule,
  ],
  templateUrl: './edit-appointment.html',
  styleUrl: './edit-appointment.scss',
})
export class EditAppointment implements OnInit {
  appointment: Signal<Appointment> = input.required<Appointment>();

  private router = inject(Router);
  private formBuilder = inject(FormBuilder);
  private appointmentService = inject(AppointmentService);
  private destroyRef = inject(DestroyRef);

  private snackBar = inject(MatSnackBar);

  appointmentForm!: FormGroup;

  ngOnInit(): void {
    const timeArray: number[] = this.appointment()
      .time.split(':')
      .map((s) => parseInt(s));

    const initialTime = new Date();
    initialTime.setHours(timeArray[0], timeArray[1], timeArray[2]);

    this.appointmentForm = this.formBuilder.group({
      id: [this.appointment().id],
      appointmentType: [this.appointment().appointmentType],
      title: [this.appointment().title, Validators.required],
      description: [this.appointment().description],
      date: [this.appointment().date, Validators.required],
      time: [initialTime, Validators.required],
      location: [this.appointment().location],
      songs: [this.appointment().songs],
    });
  }

  get songs(): FormArray {
    return this.appointmentForm.get('songs') as FormArray;
  }

  addSong(): void {
    //TODO: Add songs
    console.log('add');
  }

  removeSong(index: number): void {
    //TODO: remove songs
    console.log(index);
  }

  onSubmit(): void {
    if (this.appointmentForm.invalid) {
      return;
    }

    let updatedAppointment: Appointment = { 
      ...this.appointment(),
      id: this.appointment().id,
      appointmentType: this.appointmentForm.value.appointmentType!,
      title: this.appointmentForm.value.title!,
      description: this.appointmentForm.value.description!,
      date: formatDateToIso(this.appointmentForm.value.date)!,
      time: formatTimeToIso(this.appointmentForm.value.time)!,
      location: this.appointmentForm.value.location,
      songs: this.appointmentForm.value.songs,
    };

    const subscription = this.appointmentService
      .saveAppointment(updatedAppointment)
      .subscribe({
        next: () => {
          // Go back to dashboard/appointments/list
          this.snackBar.open('Appointment saved successfully!', 'close', {
            duration: 3000,
          });
          this.router.navigate(['dashboard', 'appointments', 'list']);
        },
      });

    this.destroyRef.onDestroy(() => subscription.unsubscribe());
  }

  navigateTo(route: string) {
    this.router.navigate(['dashboard', route, 'list']);
  }
}
