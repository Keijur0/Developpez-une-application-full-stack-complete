import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { TopicComponent } from './topic.component';

describe('TopicComponent', () => {
  let component: TopicComponent;
  let fixture: ComponentFixture<TopicComponent>;

  const mockSessionService = {
    sessionInfo: {
      id: 1
    }
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TopicComponent ],
      imports: [
        HttpClientModule,
        MatSnackBarModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
