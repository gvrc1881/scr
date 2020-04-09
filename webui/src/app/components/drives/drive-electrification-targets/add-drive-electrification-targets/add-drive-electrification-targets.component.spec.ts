import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDriveElectrificationTargetsComponent } from './add-drive-electrification-targets.component';

describe('AddDriveElectrificationTargetsComponent', () => {
  let component: AddDriveElectrificationTargetsComponent;
  let fixture: ComponentFixture<AddDriveElectrificationTargetsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddDriveElectrificationTargetsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDriveElectrificationTargetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
