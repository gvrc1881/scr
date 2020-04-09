import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDriveTargetComponent } from './add-drive-target.component';

describe('AddDriveTargetComponent', () => {
  let component: AddDriveTargetComponent;
  let fixture: ComponentFixture<AddDriveTargetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddDriveTargetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDriveTargetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
