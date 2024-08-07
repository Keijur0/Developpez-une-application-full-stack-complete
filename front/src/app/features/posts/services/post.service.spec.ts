import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { Post } from '../interfaces/post.interface';
import { PostService } from './post.service';

describe('PostService', () => {
  let service: PostService;
  let httpMock: HttpTestingController;
  const apiUrl = 'api/post';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PostService]
    });

    service = TestBed.inject(PostService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return a post', () => {
    const mockPost: Post = {
      id: 1,
      topicId: 1,
      title: 'Test Post',
      authorId: 1,
      content: 'Test Content',
      createdAt: new Date(),
    };

    service.detail(1).subscribe(post => {
      expect(post).toEqual(mockPost);
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPost);
  });

  it('should return a post list from topics user has subscribed', () => {
    const mockPosts: Post[] = [
      {
        id: 1,
        topicId: 1,
        title: 'Post 1',
        authorId: 1,
        content: 'Post 1 content',
        createdAt: new Date()
      },
      {
        id: 1,
        topicId: 1,
        title: 'Post 2',
        authorId: 1,
        content: 'Post 2 content',
        createdAt: new Date()
      }
    ];

    service.subscribedPosts(1).subscribe(posts => {
      expect(posts).toEqual(mockPosts);
    });

    const req = httpMock.expectOne(`${apiUrl}/subscribed/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts);
  });

  it('should create a new post', () => {
    const newPost: Post = {
      id: 1,
      topicId: 1,
      title: 'New Post',
      authorId: 1,
      content: 'New Content',
      createdAt: new Date
    };

    service.create(newPost).subscribe(post => {
      expect(post).toEqual(newPost);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newPost);
    req.flush(newPost);
  });

  it('should sort posts in ascending order', () => {
    const mockPosts: Post[] = [
      {
        id: 1,
        topicId: 1,
        title: 'Post 1',
        authorId: 1,
        content: 'Post 1 content',
        createdAt: new Date('2024-08-06')
      },
      {
        id: 1,
        topicId: 1,
        title: 'Post 2',
        authorId: 1,
        content: 'Post 2 content',
        createdAt: new Date('2024-08-07')
      }
    ];

    service.getSortedPosts(1, 'asc').subscribe(posts => {
      expect(posts).toEqual([mockPosts[0], mockPosts[1]]);
    });

    const req = httpMock.expectOne(`${apiUrl}/subscribed/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts);
  });

  it('should sort posts in descending order', () => {
    const mockPosts: Post[] = [
      {
        id: 1,
        topicId: 1,
        title: 'Post 1',
        authorId: 1,
        content: 'Post 1 content',
        createdAt: new Date('2024-08-07')
      },
      {
        id: 1,
        topicId: 1,
        title: 'Post 2',
        authorId: 1,
        content: 'Post 2 content',
        createdAt: new Date('2024-08-06')
      }
    ];

    service.getSortedPosts(1, 'desc').subscribe(posts => {
      expect(posts).toEqual([mockPosts[1], mockPosts[0]]);
    });

    const req = httpMock.expectOne(`${apiUrl}/subscribed/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts);
  });
});
