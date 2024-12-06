import { Component, OnInit } from '@angular/core';
import { Goal } from '../../models/goal';
import { UserService } from '../user.service';
import { forkJoin } from 'rxjs';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-user-goal',
  templateUrl: './user-goal.component.html',
  styleUrls: ['./user-goal.component.css']
})
export class UserGoalComponent implements OnInit {
  currentMonth: number;
  currentYear: number;
  loading: boolean = true;
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
    this.loading = true;

    const goalsRequest = this.userService.getAllGoal().pipe(
      finalize(() => {
        if (!this.loading) return;
        this.loading = false;
      })
    );

    goalsRequest.subscribe({
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

    if (requests.length > 0) {
      forkJoin(requests).pipe(
        finalize(() => {
          this.loading = false;
        })
      ).subscribe({
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
    } else {
      this.loading = false;
    }
  }

  clearForm(): void {
    this.newGoal = {
      type: '',
      targetDate: '',
      description: ''
    };
  }

}
