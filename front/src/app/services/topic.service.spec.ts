import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { Topic } from '../interfaces/topic.interface';
import { TopicService } from './topic.service';

describe('TopicService', () => {
  let service: TopicService;
  let httpMock: HttpTestingController;
  const apiUrl = 'api/topic';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TopicService]
    });

    service = TestBed.inject(TopicService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return a list of topics', () => {
    const mockTopics: Topic[] = [
      {
        id: 1,
        name: 'Topic 1',
        description: 'Description 1'
      },
      {
        id: 2,
        name: 'Topic 2',
        description: 'Description 2'
      }
    ];

    service.getTopics().subscribe(topics => {
      expect(topics).toEqual(mockTopics);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockTopics);
  });

  it('should return a topic', () => {
    const mockTopic: Topic = {
      id: 1,
      name: 'Topic 1',
      description: 'Description 1'
    };

    service.getTopic(1).subscribe(topic => {
      expect(topic).toEqual(mockTopic);
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockTopic);
  });
});
