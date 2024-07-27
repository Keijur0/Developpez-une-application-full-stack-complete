import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../interfaces/requests/loginRequest.interface';
import { RegisterRequest } from '../interfaces/requests/registerRequest.interface';
import { AuthResponse } from '../interfaces/responses/authResponse.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'api/auth';

  constructor(private httpClient: HttpClient) { }

  public login(loginRequest: LoginRequest): Observable<AuthResponse> {
    return this.httpClient.post<AuthResponse>(`${this.apiUrl}/login`, loginRequest);
  }

  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrl}/register`, registerRequest);
  }

}
