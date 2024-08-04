import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { passwordValidator } from 'src/app/validators/password.validator';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  public hidePassword = true;

  public registerForm = this.formBuilder.group({
    username: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, passwordValidator()]]
  });

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router,
    private matSnackBar: MatSnackBar
  ) { }

  public submit(): void {
    const registerRequest = this.registerForm.value as RegisterRequest;
    this.authService.register(registerRequest).subscribe({
      next: _ => {
        this.router.navigate(['/auth/login'])
        this.matSnackBar.open('Inscription réussie', 'Fermer', { duration: 3000 });
      },
      error: _ => {
        this.matSnackBar.open('Le nom d\'utilisateur ou l\'adresse email existe déjà', 'Fermer', { duration: 3000 });
      }
    });
  }


}
