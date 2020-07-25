import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RcFailComponent } from './rc-fail.component';

describe('RcFailComponent', () => {
  let component: RcFailComponent;
  let fixture: ComponentFixture<RcFailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RcFailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RcFailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
