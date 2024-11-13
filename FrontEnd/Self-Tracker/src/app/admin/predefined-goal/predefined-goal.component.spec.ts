import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PredefinedGoalComponent } from './predefined-goal.component';

describe('PredefinedGoalComponent', () => {
  let component: PredefinedGoalComponent;
  let fixture: ComponentFixture<PredefinedGoalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PredefinedGoalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PredefinedGoalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
