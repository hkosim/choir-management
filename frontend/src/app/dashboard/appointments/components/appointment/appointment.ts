import {
  ChangeDetectionStrategy,
  Component,
  input,
  OnInit,
  output,
  Signal,
  WritableSignal,
} from '@angular/core';
import { Appointment as AppointmentModel } from '../../model/appointment.model';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import {
  MatButtonToggleChange,
  MatButtonToggleModule,
} from '@angular/material/button-toggle';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-appointment',
  imports: [
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatButtonToggleModule,
    FormsModule,
  ],
  templateUrl: './appointment.html',
  styleUrl: './appointment.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Appointment implements OnInit {
  appointment = input.required<AppointmentModel>();
  attendance!: boolean;

  attendanceChanged = output<AppointmentModel>();

  ngOnInit(): void {
    this.attendance = this.appointment().present;
  }

  // Emits the value of new attendance
  onAttendanceChange(event: MatButtonToggleChange) {
    const updatedAppointment: AppointmentModel = {
      ...this.appointment(),
      present: event.value
    }
    this.attendanceChanged.emit(updatedAppointment);
  }
}
