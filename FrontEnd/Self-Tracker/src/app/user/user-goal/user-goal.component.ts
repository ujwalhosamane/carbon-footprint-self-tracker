import { Component, OnInit } from '@angular/core';
import { Goal } from '../../models/goal';
import { UserService } from '../user.service';
import { forkJoin } from 'rxjs';

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

  goals: Goal[] = [];

  newGoal = {
    type: '',
    targetDate: '',
    description: ''
  };

  constructor(private userService: UserService) {
    const today = new Date();
    this.currentMonth = today.getMonth() + 1;
    this.currentYear = today.getFullYear();
  }

  ngOnInit(): void {
    this.userService.getAllGoal().subscribe({
      next: (goals) => {
        this.goals = goals;
      },
      error: (error) => {
        console.error('Error fetching goals:', error);
      }
    });

    const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    let startMonth = this.currentMonth >= 7 ? 6 : 0;
    let requests = [];

    for(let i = startMonth; i < this.currentMonth; i++) {
      requests.push(this.userService.getCarbonFootprintForMonthAndYear(months[i], this.currentYear));
    }

    forkJoin(requests).subscribe({
      next: (responses) => {
        this.carbonFootprint = responses.reduce((acc, curr) => {
          return {
            transportation: acc.transportation + (curr.transportation || 0),
            electricity: acc.electricity + (curr.electricity || 0),
            lpg: acc.lpg + (curr.lpg || 0),
            shipping: acc.shipping + (curr.shipping || 0),
            airConditioner: acc.airConditioner + (curr.airConditioner || 0)
          };
        }, {
          transportation: 0,
          electricity: 0,
          lpg: 0,
          shipping: 0,
          airConditioner: 0
        });
      },
      error: (error) => {
        console.error('Error fetching carbon footprint data:', error);
      }
    });
  }

  clearForm(): void {
    this.newGoal = {
      type: '',
      targetDate: '',
      description: ''
    };
  }

}
