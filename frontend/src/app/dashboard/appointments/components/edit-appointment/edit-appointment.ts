import {
  Component,
  DestroyRef,
  inject,
  input,
  OnInit,
  Signal,
} from '@angular/core';
import { Rehearsal } from '../../model/rehearsal.model';
import { Performance } from '../../model/performance.model';
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
import { Song } from '../../model/song.model';
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
import { AppointmentType } from '../../model/appointment-type.model';

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
  appointment: Signal<Rehearsal | Performance> = input.required<
    Rehearsal | Performance
  >();

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
      type: [this.appointment().type],
      title: [this.appointment().title, Validators.required],
      description: [this.appointment().description],
      date: [this.appointment().date, Validators.required],
      time: [initialTime, Validators.required],
      location: [this.appointment().location],
    });

    // Add songs if it is an performance
    if (this.appointment().type === AppointmentType.PERFORMANCE) {
      // Creates the form group inside the songs
      this.appointmentForm.addControl('songs', new FormArray([]));

      const performance: Performance = this.appointment() as Performance;

      performance.songs?.forEach((song: Song) => {
        this.songs.push(
          this.formBuilder.group({
            title: [song.title, Validators.required],
            composer: [song.composer],
            musicalKey: [song.musicalKey],
            voiceArrangement: [song.voiceArrangement],
            source: [song.source],
          })
        );
      });
    }
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

    let updatedAppointment: Rehearsal | Performance = {
      id: this.appointment().id,
      type: this.appointment().type,
      title: this.appointmentForm.value.title!,
      description: this.appointmentForm.value.description!,
      date: formatDateToIso(this.appointmentForm.value.date)!,
      time: formatTimeToIso(this.appointmentForm.value.time)!,
      location: this.appointmentForm.value.location,
    };
    console.log(updatedAppointment);

    // Submit the appointment to the
    if (this.appointment().type === AppointmentType.PERFORMANCE) {
      updatedAppointment = {
        ...updatedAppointment,
        songs: this.appointmentForm.value.songs,
      };
    }

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
