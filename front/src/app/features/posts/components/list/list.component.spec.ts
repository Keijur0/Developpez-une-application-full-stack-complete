import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { of } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { SharedModule } from 'src/app/shared/shared.module';
import { PostService } from '../../services/post.service';
import { ListComponent } from './list.component';

const mockPostService = {
  getSortedPosts: jest.fn().mockReturnValue(of([
    {
      id: 1,
      title: 'Test Post 1',
      content: 'Content 1',
      createdAt: new Date(),
      authorId: 1,
    },
    {
      id: 2,
      title: 'Test Post 2',
      content: 'Content 2',
      createdAt: new Date(),
      authorId: 2,
    }
  ]))
};

const mockSessionService = {
  sessionInfo: {
    id: 1
  }
};

describe('ListComponent', () => {
  let component: ListComponent;
  let fixture: ComponentFixture<ListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListComponent],
      imports: [
        HttpClientModule,
        MatIconModule,
        MatButtonModule,
        RouterTestingModule,
        SharedModule
      ],
      providers: [
        { provide: PostService, useValue: mockPostService },
        { provide: SessionService, useValue: mockSessionService }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
