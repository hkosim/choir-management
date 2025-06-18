import {
  Component,
  DestroyRef,
  inject,
  signal,
  WritableSignal,
} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormField } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { merge } from 'rxjs';
import { AuthService } from '../auth.service';
import { LoginRequest } from '../model/login-request.model';
import { LoginResponse } from '../model/login-response.model';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    MatFormField,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  private authService = inject(AuthService);
  private router = inject(Router);
  private destroyRef = inject(DestroyRef);

  readonly username = new FormControl('', [Validators.required]);
  readonly password = new FormControl('', [
    Validators.required,
    Validators.minLength(4),
  ]);

  readonly loginForm = new FormGroup([this.username, this.password]);

  readonly errorMessage = signal('');
  readonly usernameErrorMessage = signal('');
  readonly passwordErrorMessage = signal('');

  isLoggingIn: Boolean = false;

  constructor() {
    this.subscribeToControlErrors(this.username, this.usernameErrorMessage);

    this.subscribeToControlErrors(this.password, this.passwordErrorMessage);
  }

  private subscribeToControlErrors(
    control: FormControl,
    errorSignal: WritableSignal<string>
  ) {
    merge(control.statusChanges, control.valueChanges)
      .pipe(takeUntilDestroyed())
      .subscribe(() => {
        this.updateErrorMessage(control, errorSignal);
      });
  }

  updateErrorMessage(field: FormControl, errorSignal: WritableSignal<string>) {
    if (field.hasError('required')) {
      errorSignal.set('You must enter a value.');
    } else if (field.hasError('minlength')) {
      errorSignal.set('You must enter at minimum 6 characters.');
    } else {
      errorSignal.set('');
    }
  }

  get showUsernameError(): boolean {
    return (
      this.username.invalid || this.username.dirty || this.username.touched
    );
  }
  get showPasswordError(): boolean {
    return (
      this.password.invalid || this.password.dirty || this.password.touched
    );
  }

  async login() {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsDirty;

      // Show all errors
      this.updateErrorMessage(this.username, this.usernameErrorMessage);
      this.updateErrorMessage(this.password, this.passwordErrorMessage);
      return;
    }
    this.isLoggingIn = true;
    const loginRequest: LoginRequest = {
      username: this.username.value!,
      password: this.password.value!,
    };

    const subscription = this.authService.login(loginRequest).subscribe({
      next: (loginResponse: LoginResponse) => {
        this.isLoggingIn = false;
        this.router.navigate(['dashboard', 'appointments']);
      },
      error: () => {
        this.errorMessage.set('Invalid credentials.');
        this.isLoggingIn = false;
      },
      complete: () => {},
    });

    this.destroyRef.onDestroy(() => subscription.unsubscribe());
  }
}
