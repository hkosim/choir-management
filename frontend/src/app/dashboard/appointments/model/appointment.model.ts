import { AppointmentType } from "./appointment-type.model";

export interface Appointment {
  id: number;
  type: AppointmentType
  title: string;
  description: string;
  date: string; // ISO format e.g. "2025-06-17"
  time: string; // ISO time e.g. "14:30:00"
  location: string;
}
