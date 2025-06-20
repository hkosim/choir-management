import {
  ChangeDetectionStrategy,
  Component,
  inject,
  input,
  OnInit,
  output,
} from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import {
  MatButtonToggleChange,
  MatButtonToggleModule,
} from '@angular/material/button-toggle';
import { FormsModule } from '@angular/forms';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Router } from '@angular/router';
import { AuthService } from '../../../../auth/auth.service';
import { AppointmentAttendance } from '../../model/appointment-attendance.model';
import { AppointmentType } from '../../model/appointment-type.model';

@Component({
  selector: 'app-appointment',
  imports: [
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatButtonToggleModule,
    FormsModule,
    MatTooltipModule,
    
  ],
  templateUrl: './appointment.html',
  styleUrl: './appointment.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Appointment implements OnInit {
  appointmentAttendance = input.required<AppointmentAttendance>();
  attendance!: boolean;

  AppointmentType = AppointmentType;

  private authService = inject(AuthService);
  private router = inject(Router);

  userRole: 'user' | 'admin' = 'user';

  attendanceChanged = output<AppointmentAttendance>();

  ngOnInit(): void {
    this.attendance = this.appointmentAttendance().present;
    this.userRole = this.authService.roles.includes('ROLE_ADMIN')
      ? 'admin'
      : 'user';
  }

  // Emits the value of new attendance
  onAttendanceChange(event: MatButtonToggleChange) {
    const updatedAppointment: AppointmentAttendance = {
      ...this.appointmentAttendance(),
      present: event.value,
    };
    this.attendanceChanged.emit(updatedAppointment);
  }

  navigateTo(route: string, type: AppointmentType, id: number) {
    this.router.navigate([
      'dashboard',
      'appointments',
      route,
      type.toString().toLowerCase(),
      id,
    ]);
  }
}
