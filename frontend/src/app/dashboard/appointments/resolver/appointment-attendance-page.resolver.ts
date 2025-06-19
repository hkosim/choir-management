import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AppointmentService } from '../service/appointment.service';
import { lastValueFrom } from 'rxjs';

export async function resolveAppointmentAttendancePage(
  activatedRouteSnapshot: ActivatedRouteSnapshot,
  routerState: RouterStateSnapshot
) {
  const appointmentService = inject(AppointmentService);
  const appointmentAttendancePage$ = appointmentService.getAppointmentAttendancePage();
  return await lastValueFrom(appointmentAttendancePage$).catch(async (err: Error) => {
    console.log(err);
  });
}
