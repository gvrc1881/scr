import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DriveChecklistComponent } from './drive-checklist.component';

describe('DriveChecklistComponent', () => {
  let component: DriveChecklistComponent;
  let fixture: ComponentFixture<DriveChecklistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DriveChecklistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DriveChecklistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
