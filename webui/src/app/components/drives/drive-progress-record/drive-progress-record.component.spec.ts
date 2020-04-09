import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DriveProgressRecordComponent } from './drive-progress-record.component';

describe('DriveProgressRecordComponent', () => {
  let component: DriveProgressRecordComponent;
  let fixture: ComponentFixture<DriveProgressRecordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DriveProgressRecordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DriveProgressRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
