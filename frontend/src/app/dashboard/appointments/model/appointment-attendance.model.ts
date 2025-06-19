import { Appointment } from './appointment.model';

export interface AppointmentAttendance extends Appointment {
  present: boolean;
}
