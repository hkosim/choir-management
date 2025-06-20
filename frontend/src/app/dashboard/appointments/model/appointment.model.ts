import { Song } from '../components/song/song';
import { AppointmentType } from '../enum/appointment-type.enum';

export interface Appointment {
  id: number;
  title: string;
  description: string;
  date: string;
  time: string;
  location: string;
  appointmentType: AppointmentType;
  songs: Array<Song> | undefined;

  createdBy: string;
  createdAt: string;
  lastModifiedBy: string;
  lastModifiedAt: string;
  deletedAt: string;
}
