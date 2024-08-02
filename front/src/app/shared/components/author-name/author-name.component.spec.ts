import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorNameComponent } from './author-name.component';

describe('AuthorNameComponent', () => {
  let component: AuthorNameComponent;
  let fixture: ComponentFixture<AuthorNameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthorNameComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthorNameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
