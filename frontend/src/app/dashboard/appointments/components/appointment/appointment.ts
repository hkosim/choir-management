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
import { Appointment as AppointmentModel } from '../../model/appointment.model';
import { AppointmentType } from '../../enum/appointment-type.enum';
import { AttendanceStatus } from '../../enum/attendance-status.enum';
import { MatDialog } from '@angular/material/dialog';
import { AppointmentService } from '../../service/appointment.service';
import { ModalContent } from '../../../../shared/models/modal-content.model';
import { ModalTemplate } from '../../../../shared/components/modals/delete-modal/modal-template';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserRole } from '../../../../auth/enums/user-role.enum';

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
  appointmentAttendance = input.required<AttendanceStatus>();
  appointment = input.required<AppointmentModel>();

  attendance!: AttendanceStatus;

  AppointmentType = AppointmentType;
  AttendanceStatus = AttendanceStatus;

  private authService = inject(AuthService);
  private appointmentService = inject(AppointmentService);
  private router = inject(Router);
  private dialog = inject(MatDialog);
  private snackBar = inject(MatSnackBar);

  UserRole = UserRole;
  userRole: UserRole = UserRole.USER;

  attendanceChanged = output<AppointmentAttendance>();

  ngOnInit(): void {
    this.attendance = this.appointmentAttendance();
    if (this.authService.roles.includes('ROLE_ADMIN')) {
      this.userRole = UserRole.ADMIN;
    }
  }

  // Emits the value of new attendance
  onAttendanceChange(event: MatButtonToggleChange) {
    const updatedAppointment: AppointmentAttendance = {
      appointment: this.appointment(),
      attendanceStatus: event.value,
    };
    this.attendanceChanged.emit(updatedAppointment);
  }

  navigateTo(route: string, id: number) {
    this.router.navigate(['dashboard', 'appointments', route, id]);
  }

  removeAppointment(id: number) {
    const modalContent: ModalContent = {
      title: 'Delete Modal',
      content: `Are you sure you want to delete ${id}?`,
      actions: [
        {
          label: 'Cancel',
          value: false,
        },
        {
          label: 'Delete',
          value: true,
        },
      ],
    };
    // TODO: open confirm modal
    const dialogRef = this.dialog.open(ModalTemplate, {
      data: modalContent,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        // TODO: CALL SERVICE; DELETE MODAL
        const subscription = this.appointmentService
          .deleteAppointment(id)
          .subscribe({
            next: () => {
              this.snackBar.open('Appointment cancelled!', 'close', {
                duration: 2000,
              });
            },
            complete: () => {
              this.router.navigate(['dashboard', 'appointments', 'list'], {
                onSameUrlNavigation: 'reload',
              });
            },
          });
      }
    });
  }

  restoreAppointment(id: number) {
    // Open confirm modal
    const modalContent: ModalContent = {
      title: 'Restore Appointment',
      content: `Are you sure you want to restore appointment ${id}?`,
      actions: [
        {
          label: 'Cancel',
          value: false,
        },
        {
          label: 'Restore',
          value: true,
        },
      ],
    };

    const dialogRef = this.dialog.open(ModalTemplate, {
      data: modalContent,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        // Save modal

        this.appointmentService.restoreAppointment(id).subscribe({
          next: () => {
            this.snackBar.open('Appointment restored!', 'close', {
              duration: 2000,
            });
          },
          complete: () => {
            this.router.navigate(['dashboard', 'appointments', 'list'], {
              onSameUrlNavigation: 'reload',
            });
          },
        });
      }
    });
  }
}
