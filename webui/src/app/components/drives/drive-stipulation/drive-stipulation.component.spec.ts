import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DriveStipulationComponent } from './drive-stipulation.component';

describe('DriveStipulationComponent', () => {
  let component: DriveStipulationComponent;
  let fixture: ComponentFixture<DriveStipulationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DriveStipulationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DriveStipulationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
