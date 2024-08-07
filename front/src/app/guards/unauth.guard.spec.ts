import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { UnauthGuard } from './unauth.guard';

describe('UnauthGuard', () => {
  let guard: UnauthGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(UnauthGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
