import { DestroyRef, inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot,
} from '@angular/router';

import { lastValueFrom } from 'rxjs';
import { AuthService } from '../auth.service';

export async function AuthGuard(
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) {
  const router = inject(Router);
  const authService = inject(AuthService);

  const token: string | null = sessionStorage.getItem('token');
  const username: string | null = sessionStorage.getItem('username');

  // Make sure that the user is authenticated
  if (token != null && username != null) {
    if (authService.roles.length == 0) {
      const $roles = authService.getRoles(username);
      const roles = await lastValueFrom($roles).catch((err: Error) => {
        authService.logout();
        // Not logged in so redirect to login page with the return url
        router.navigate(['auth', 'login'], {
          replaceUrl: true,
        });
      });
      authService.roles = roles!;
    }
    
    return true;
  }
  authService.logout();
  // Not logged in so redirect to login page with the return url
  router.navigate(['auth', 'login'], {
    replaceUrl: true,
  });
  return false;
}
