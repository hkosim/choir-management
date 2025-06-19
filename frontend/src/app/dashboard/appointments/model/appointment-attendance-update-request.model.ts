export interface AppointmentAttendanceUpdateRequest {
  username: string;
  id: number;
  type: string;
  present: boolean;
}
