import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { SessionInfo } from '../interfaces/sessionInfo.interface';
import { SessionService } from './session.service';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SessionService]
    });

    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should initially have isLoggedIn as false and sessionInfo as undefined', () => {
    expect(service.isLoggedIn).toBe(false);
    expect(service.sessionInfo).toBeUndefined();
  });

  it('should set isLoggedIn to true and update sessionInfo when logIn is called', () => {
    const mockSessionInfo: SessionInfo = {
      id: 1,
      username: 'test',
      email: 'test@test.com',
      token: 'token'
    };

    service.logIn(mockSessionInfo);

    expect(service.isLoggedIn).toBe(true);
    expect(service.sessionInfo).toEqual(mockSessionInfo);

    service.isLoggedIn$().subscribe(isLoggedIn => {
      expect(isLoggedIn).toBe(true);
    });
  });

  it('should set isLoggedIn to false and clear sessionInfo when logOut is called', () => {
    service.logOut();

    expect(service.isLoggedIn).toBe(false);
    expect(service.sessionInfo).toBeUndefined();

    service.isLoggedIn$().subscribe(isLoggedIn => {
      expect(isLoggedIn).toBe(false);
    });
  });

  it('should set isLoggedIn to false and clear token in localStorage when logOut is called', () => {
    const removeItemSpy = jest.spyOn(Storage.prototype, 'removeItem');

    service.logOut();

    expect(service.isLoggedIn).toBe(false);
    expect(service.sessionInfo).toBeUndefined();

    service.isLoggedIn$().subscribe(isLoggedIn => {
      expect(isLoggedIn).toBe(false);
    });

    expect(removeItemSpy).toHaveBeenCalledWith('token');
  });
});
