import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowedToolListComponent } from './borrowed-tool-list.component';

describe('BorrowedToolListComponent', () => {
  let component: BorrowedToolListComponent;
  let fixture: ComponentFixture<BorrowedToolListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BorrowedToolListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BorrowedToolListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
