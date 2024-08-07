import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { of } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { SharedModule } from 'src/app/shared/shared.module';
import { CommentService } from '../../services/comment.service';
import { PostService } from '../../services/post.service';
import { DetailComponent } from './detail.component';

const mockPostService = {
  detail: jest.fn().mockReturnValue(of({
    id: 1,
    title: 'Test Post',
    content: 'Test Content',
    createdAt: new Date(),
    authorId: 1,
    topicId: 1
  }))
};

const mockCommentService = {
  getComments: jest.fn().mockReturnValue(of([])),
  createComment: jest.fn().mockReturnValue(of({}))
};

const mockSessionService = {
  sessionInfo: {
    id: 1
  }
};

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DetailComponent],
      imports: [
        HttpClientModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        RouterTestingModule,
        SharedModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: PostService, useValue: mockPostService },
        { provide: CommentService, useValue: mockCommentService },
        { provide: SessionService, useValue: mockSessionService },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: () => '1' } }
          }
        }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
