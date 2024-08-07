import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { SessionInfo } from 'src/app/interfaces/sessionInfo.interface';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;
  const apiUrl = 'api/auth';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should login a user and return SessionInfo', () => {
    const loginRequest: LoginRequest = {
      usernameOrEmail: 'test',
      password: 'Test!1234'
    };
    const mockSessionInfo: SessionInfo = {
      id: 1,
      username: 'test',
      email: 'test@test.com',
      token: 'token'
    };

    service.login(loginRequest).subscribe(sessionInfo => {
      expect(sessionInfo).toEqual(mockSessionInfo);
    });

    const req = httpMock.expectOne(`${apiUrl}/login`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(loginRequest);
    req.flush(mockSessionInfo);
  });

  it('should register a user', () => {
    const registerRequest: RegisterRequest = {
      username: 'test',
      email: 'test@test.com',
      password: 'Test!1234'
    };

    service.register(registerRequest).subscribe(response => {
      expect(response).toBeUndefined();
    });

    const req = httpMock.expectOne(`${apiUrl}/register`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(registerRequest);
    req.flush(null); // No content for void response
  });

  it('should get the current user and return SessionInfo', () => {
    const mockSessionInfo: SessionInfo = {
      id: 1,
      username: 'test',
      email: 'test@test.com',
      token: 'token'
    };

    service.me().subscribe(sessionInfo => {
      expect(sessionInfo).toEqual(mockSessionInfo);
    });

    const req = httpMock.expectOne(`${apiUrl}/me`);
    expect(req.request.method).toBe('GET');
    req.flush(mockSessionInfo);
  });
});
