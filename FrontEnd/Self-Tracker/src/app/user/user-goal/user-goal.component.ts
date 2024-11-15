import { Component, OnInit } from '@angular/core';
import { Goal } from '../../models/goal';

@Component({
  selector: 'app-user-goal',
  templateUrl: './user-goal.component.html',
  styleUrls: ['./user-goal.component.css']
})
export class UserGoalComponent implements OnInit {
  currentMonth: number;
  currentYear: number;
  carbonFootprint: any = {
    transportation: 0,
    electricity: 0,
    lpg: 0,
    shipping: 0,
    airConditioner: 0
  };

  goals: Goal[] = [
    new Goal(
      1,
      150,
      2,
      false,
      new Date(),
      'Reduce Energy Consumption',
      'Energy',
      'Decrease household electricity usage by 20%',
      200,
      100,
      'assets/badges/energy-saver.png'
    ),
    new Goal(
      2,
      80,
      1,
      true,
      new Date(),
      'Lower Transportation Emissions',
      'Transport',
      'Reduce transportation related emissions by carpooling',
      100,
      150,
      'assets/badges/transport-hero.png'
    )
  ];

  // Form model
  newGoal = {
    type: '',
    targetDate: '',
    description: ''
  };

  constructor() {
    const today = new Date();
    this.currentMonth = today.getMonth() + 1;
    this.currentYear = today.getFullYear();
  }

  ngOnInit(): void {
    // Initialize carbon footprint data
    this.carbonFootprint = {
      transportation: 120,
      electricity: 80,
      lpg: 40,
      shipping: 25,
      airConditioner: 60
    };
  }

  addGoal(): void {
    // TODO: Implement goal addition logic
    console.log('Adding new goal:', this.newGoal);
  }

  updateGoal(goal: Goal): void {
    // TODO: Implement goal update logic
    console.log('Updating goal:', goal);
  }

  removeGoal(goal: Goal): void {
   
    this.goals = this.goals.filter(g => g.goalId !== goal.goalId);
  }

  clearForm(): void {
    this.newGoal = {
      type: '',
      targetDate: '',
      description: ''
    };
  }

}
