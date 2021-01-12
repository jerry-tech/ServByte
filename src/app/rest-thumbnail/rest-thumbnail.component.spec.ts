import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestThumbnailComponent } from './rest-thumbnail.component';

describe('RestThumbnailComponent', () => {
  let component: RestThumbnailComponent;
  let fixture: ComponentFixture<RestThumbnailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RestThumbnailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RestThumbnailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
