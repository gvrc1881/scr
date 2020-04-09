import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDriveChecklistComponent } from './add-drive-checklist.component';

describe('AddDriveChecklistComponent', () => {
  let component: AddDriveChecklistComponent;
  let fixture: ComponentFixture<AddDriveChecklistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddDriveChecklistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDriveChecklistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
