import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CbFailComponent } from './cb-fail.component';

describe('CbFailComponent', () => {
  let component: CbFailComponent;
  let fixture: ComponentFixture<CbFailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CbFailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CbFailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
