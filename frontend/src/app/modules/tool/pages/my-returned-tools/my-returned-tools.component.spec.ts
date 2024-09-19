import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyReturnedToolsComponent } from './my-returned-tools.component';

describe('MyReturnedToolsComponent', () => {
  let component: MyReturnedToolsComponent;
  let fixture: ComponentFixture<MyReturnedToolsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MyReturnedToolsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyReturnedToolsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
