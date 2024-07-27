import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { LoginRequest } from '../../interfaces/requests/loginRequest.interface';
import { AuthResponse } from '../../interfaces/responses/authResponse.interface';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  public hidePassword = true;
  public onError = false;

  public form = this.formBuilder.group({
    usernameOrEmail: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router,
    private sessionService: SessionService
  ) { }

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe({
      next: (authResponse: AuthResponse) => {
        this.sessionService.logIn(authResponse);
        this.router.navigate(['/posts']);
      },
      error: error => this.onError = true
    });
  }

}
