import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AshEntryComponent } from './ash-entry.component';

describe('AshEntryComponent', () => {
  let component: AshEntryComponent;
  let fixture: ComponentFixture<AshEntryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AshEntryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AshEntryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
