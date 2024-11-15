import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-overview',
  templateUrl: './user-overview.component.html',
  styleUrls: ['./user-overview.component.css']
})
export class UserOverviewComponent implements OnInit {
  currentMonth: number;
  currentYear: number;

  rewardPoints: number = 0;
  carbonFootprint: number = 0;
  completedGoals: number = 0;

  activeGoals: {name: string, status: string}[] = [
    {name: 'Reduce Energy Consumption', status: 'In Progress'}
  ];

  constructor() {
    const today = new Date();
    this.currentMonth = today.getMonth() + 1;
    this.currentYear = today.getFullYear();
  }

  ngOnInit() {
    this.loadUserStats();
  }

  private loadUserStats() {
    this.rewardPoints = 0;
    this.carbonFootprint = 0;
    this.completedGoals = 0;
  }
}