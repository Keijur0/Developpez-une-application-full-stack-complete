import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionInfo } from 'src/app/interfaces/sessionInfo.interface';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'api/auth';

  constructor(private httpClient: HttpClient) { }

  public login(loginRequest: LoginRequest): Observable<SessionInfo> {
    return this.httpClient.post<SessionInfo>(`${this.apiUrl}/login`, loginRequest);
  }

  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrl}/register`, registerRequest);
  }

}
