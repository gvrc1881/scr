import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDriveProgressRecordComponent } from './add-drive-progress-record.component';

describe('AddDriveProgressRecordComponent', () => {
  let component: AddDriveProgressRecordComponent;
  let fixture: ComponentFixture<AddDriveProgressRecordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddDriveProgressRecordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDriveProgressRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
