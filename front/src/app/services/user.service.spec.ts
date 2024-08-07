import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { SessionInfo } from '../interfaces/sessionInfo.interface';
import { Topic } from '../interfaces/topic.interface';
import { User } from '../interfaces/user.interface';
import { UserService } from './user.service';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;
  const apiUrl = 'api/user';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });

    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  it('should return a user', () => {
    const mockUser: User = { id: 1,
      username: 'testuser',
      email: 'test@example.com'
    };

    service.getUser(1).subscribe(user => {
      expect(user).toEqual(mockUser);
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);
  });

  it('should update the user and return SessionInfo', () => {
    const mockUser: User = {
      id: 1,
      username: 'test',
      email: 'test@test.com'
    };
    const mockSessionInfo: SessionInfo = {
      id: 1,
      username: 'test',
      email: 'test@test.com',
      token: 'token'
    };

    service.update(1, mockUser).subscribe(sessionInfo => {
      expect(sessionInfo).toEqual(mockSessionInfo);
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockUser);
    req.flush(mockSessionInfo);
  });

  it('should return a user subscriptions (list of topics) and update subscriptionsSubject', () => {
    const mockTopics: Topic[] = [
      {
        id: 1,
        name: 'Topic 1',
        description: 'Topic1 description'
      },
      {
        id: 2,
        name: 'Topic 2',
        description: 'Topic2 description'
      }
    ];

    service.getSubscriptions(1).subscribe(topics => {
      expect(topics).toEqual(mockTopics);
      service.subscriptions$.subscribe(subscriptions => {
        expect(subscriptions).toEqual(mockTopics);
      });
    });

    const req = httpMock.expectOne(`${apiUrl}/1/subscriptions`);
    expect(req.request.method).toBe('GET');
    req.flush(mockTopics);
  });

  it('should subscribe a user to a topic', () => {
    service.subscribe(1, 2).subscribe();

    const req = httpMock.expectOne(`${apiUrl}/1/subscribe/2`);
    expect(req.request.method).toBe('PUT');
    req.flush({});
  });

  it('should unsubscribe a user from a topic', () => {
    service.unsubscribe(1, 2).subscribe();

    const req = httpMock.expectOne(`${apiUrl}/1/subscribe/2`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });
});
