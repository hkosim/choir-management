import { Appointment } from "./appointment.model";
import { Song } from "./song.model";

export interface Performance extends Appointment{
    songs: Song[],
}