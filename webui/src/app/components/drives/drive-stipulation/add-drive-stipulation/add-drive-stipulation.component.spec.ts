import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDriveStipulationComponent } from './add-drive-stipulation.component';

describe('AddDriveStipulationComponent', () => {
  let component: AddDriveStipulationComponent;
  let fixture: ComponentFixture<AddDriveStipulationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddDriveStipulationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDriveStipulationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
