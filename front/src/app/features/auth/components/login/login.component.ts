import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { SessionInfo } from 'src/app/interfaces/sessionInfo.interface';
import { SessionService } from 'src/app/services/session.service';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
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
    private sessionService: SessionService,
    private matSnackBar: MatSnackBar,
  ) { }

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe({
      next: (sessionInfo: SessionInfo) => {
        localStorage.setItem('token', sessionInfo.token);
        this.sessionService.logIn(sessionInfo);
        this.router.navigate(['/posts']);
      },
      error: _ => {
        this.matSnackBar.open('Nom d\'utilisateur, email ou mot de passe incorrect', 'Fermer', { duration: 3000 });
      }
    });
  }

}
