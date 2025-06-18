import {
  ChangeDetectionStrategy,
  Component,
  inject,
  input,
  OnInit,
  output,
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
import { MatTooltipModule } from '@angular/material/tooltip';
import { Router } from '@angular/router';
import { AppointmentService } from '../../service/appointment.service';
import { AuthService } from '../../../../auth/auth.service';

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
  appointment = input.required<AppointmentModel>();
  attendance!: boolean;

  private authService = inject(AuthService);
  private router = inject(Router);

  userRole: 'user' | 'admin' = 'user';

  attendanceChanged = output<AppointmentModel>();

  ngOnInit(): void {
    this.attendance = this.appointment().present;
    this.userRole = this.authService.roles.includes('ROLE_ADMIN')
      ? 'admin'
      : 'user';
  }

  // Emits the value of new attendance
  onAttendanceChange(event: MatButtonToggleChange) {
    const updatedAppointment: AppointmentModel = {
      ...this.appointment(),
      present: event.value,
    };
    this.attendanceChanged.emit(updatedAppointment);
  }

  navigateTo(route: string, type: 'rehearsal' | 'performance', id: number) {
    this.router.navigate(['dashboard', 'appointments', route, type, id]);
  }
}
