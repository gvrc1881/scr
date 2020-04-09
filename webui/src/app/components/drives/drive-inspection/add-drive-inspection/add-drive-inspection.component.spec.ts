import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDriveInspectionComponent } from './add-drive-inspection.component';

describe('AddDriveInspectionComponent', () => {
  let component: AddDriveInspectionComponent;
  let fixture: ComponentFixture<AddDriveInspectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddDriveInspectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDriveInspectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
