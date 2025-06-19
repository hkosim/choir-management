import { AppointmentAttendance } from './appointment-attendance.model';

export interface AppointmentAttendancePage {
  content: Array<AppointmentAttendance>;
  page: {
    size: number;
    number: number;
    totalElements: number;
    totalPages: number;
  };
}
