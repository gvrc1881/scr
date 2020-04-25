import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDriveCategoryComponent } from './add-drive-category.component';

describe('AddDriveCategoryComponent', () => {
  let component: AddDriveCategoryComponent;
  let fixture: ComponentFixture<AddDriveCategoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddDriveCategoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDriveCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
