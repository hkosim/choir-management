import { AttendanceStatus } from '../enum/attendance-status.enum';

export interface AppointmentAttendanceUpdateRequest {
  username: string;
  id: number;
  attendanceStatus: AttendanceStatus;
}
