import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MytoolsComponent } from './mytools.component';

describe('MytoolsComponent', () => {
  let component: MytoolsComponent;
  let fixture: ComponentFixture<MytoolsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MytoolsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MytoolsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
