import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { Comment } from '../interfaces/comment.interface';
import { CommentService } from './comment.service';

describe('CommentService', () => {
  let service: CommentService;
  let httpMock: HttpTestingController;
  const apiUrl = 'api/comment';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CommentService]
    });

    service = TestBed.inject(CommentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all comments from a post', () => {
    const mockComments: Comment[] = [
      { id: 1,
        postId: 1,
        message: 'Comment 1',
        userId: 1
      },
      { id: 2,
        postId: 1,
        message: 'Comment 2',
        userId: 2
      }
    ];

    service.getComments(1).subscribe(comments => {
      expect(comments).toEqual(mockComments);
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockComments);
  });

  it('should create a new comment', () => {
    const newComment: Comment = {
      id: 1,
      postId: 1,
      message: 'New Comment',
      userId: 1
    };

    service.createComment(newComment).subscribe(comment => {
      expect(comment).toEqual(newComment);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newComment);
    req.flush(newComment);
  });
});
