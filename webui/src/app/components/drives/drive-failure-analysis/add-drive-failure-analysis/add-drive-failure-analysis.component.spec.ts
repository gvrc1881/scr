import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDriveFailureAnalysisComponent } from './add-drive-failure-analysis.component';

describe('AddDriveFailureAnalysisComponent', () => {
  let component: AddDriveFailureAnalysisComponent;
  let fixture: ComponentFixture<AddDriveFailureAnalysisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddDriveFailureAnalysisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDriveFailureAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
