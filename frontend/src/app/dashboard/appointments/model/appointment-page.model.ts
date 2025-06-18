import { Appointment } from './appointment.model';

export interface AppointmentPage {
  content: Array<Appointment>;
  page: {
    size: number;
    number: number;
    totalElements: number;
    totalPages: number;
  };
}
