import { Appointment } from './appointment.model';

export interface Attendance extends Appointment {
  present: boolean;
}
