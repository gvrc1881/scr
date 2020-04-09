import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DriveInspectionComponent } from './drive-inspection.component';

describe('DriveInspectionComponent', () => {
  let component: DriveInspectionComponent;
  let fixture: ComponentFixture<DriveInspectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DriveInspectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DriveInspectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
