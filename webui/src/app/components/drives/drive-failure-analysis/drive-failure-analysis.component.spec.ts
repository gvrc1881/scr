import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DriveFailureAnalysisComponent } from './drive-failure-analysis.component';

describe('DriveFailureAnalysisComponent', () => {
  let component: DriveFailureAnalysisComponent;
  let fixture: ComponentFixture<DriveFailureAnalysisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DriveFailureAnalysisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DriveFailureAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
