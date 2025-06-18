import { Appointment } from './appointment.model';

export interface AttendanceUpdateRequest {
  username: string;
  id: number;
  type: string;
  present: boolean;
}
