import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserFootprintComponent } from './user-footprint.component';

describe('UserFootprintComponent', () => {
  let component: UserFootprintComponent;
  let fixture: ComponentFixture<UserFootprintComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserFootprintComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserFootprintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
