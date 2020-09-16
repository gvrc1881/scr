import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AshDisplayComponent } from './ash-display.component';

describe('AshDisplayComponent', () => {
  let component: AshDisplayComponent;
  let fixture: ComponentFixture<AshDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AshDisplayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AshDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
