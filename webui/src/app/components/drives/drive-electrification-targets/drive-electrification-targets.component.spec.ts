import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DriveElectrificationTargetsComponent } from './drive-electrification-targets.component';

describe('DriveElectrificationTargetsComponent', () => {
  let component: DriveElectrificationTargetsComponent;
  let fixture: ComponentFixture<DriveElectrificationTargetsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DriveElectrificationTargetsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DriveElectrificationTargetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
