import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DriveTargetComponent } from './drive-target.component';

describe('DriveTargetComponent', () => {
  let component: DriveTargetComponent;
  let fixture: ComponentFixture<DriveTargetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DriveTargetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DriveTargetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
