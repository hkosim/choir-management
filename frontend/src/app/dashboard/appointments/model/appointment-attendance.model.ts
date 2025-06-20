import { AttendanceStatus } from '../enum/attendance-status.enum';
import { Appointment } from './appointment.model';

export interface AppointmentAttendance {
  appointment: Appointment;
  attendanceStatus: AttendanceStatus;
}
