import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAshComponent } from './add-ash.component';

describe('AddAshComponent', () => {
  let component: AddAshComponent;
  let fixture: ComponentFixture<AddAshComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddAshComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAshComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
