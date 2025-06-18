export interface Appointment {
  id: number;
  type: 'REHEARSAL' | 'PERFORMANCE'; // String union for stricter typing
  title: string;
  description: string;
  date: string; // ISO format e.g. "2025-06-17"
  time: string; // ISO time e.g. "14:30:00"
  location: string;
  present: boolean;
}
