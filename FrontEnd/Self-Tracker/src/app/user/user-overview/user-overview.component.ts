import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { UserAfterLogin } from 'src/app/models/user-after-login';
import { Goal } from 'src/app/models/goal';

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
  latestActivity: {
    creationDate: Date;
    updatedDate: Date;
    footprintMonth: string;
    footprintYear: number;
  } | null = null;

  activeGoals: Goal[] = [];

  constructor(private userService: UserService) {
    const today = new Date();
    this.currentMonth = today.getMonth() + 1;
    this.currentYear = today.getFullYear();
  }

  ngOnInit() {
    this.loadUserStats();
    this.loadActiveGoals();
  }

  private loadUserStats() {
    this.userService.getUserDetailsAfterLogin().subscribe({
      next: (userDetails: UserAfterLogin) => {
        this.rewardPoints = userDetails.totalRewardPoints;
        this.carbonFootprint = userDetails.totalFootprint;
      },
      error: (error) => {
        console.error('Error fetching user details:', error);
      }
    });

    this.userService.getUserGoalCount().subscribe({
      next: (response: any) => {
        this.completedGoals = response.totalCount;
      },
      error: (error) => {
        console.error('Error fetching goal count:', error);
      }
    });

    this.userService.getUserLatestActivity().subscribe({
      next: (activity: any) => {
        if (activity) {
          this.latestActivity = {
            creationDate: new Date(activity.creationDate),
            updatedDate: new Date(activity.updatedDate), 
            footprintMonth: activity.footprintMonth,
            footprintYear: activity.footprintYear
          };
        } else {
          this.latestActivity = null;
        }
      },
      error: (error) => {
        console.error('Error fetching latest activity:', error);
      }
    });
  }

  private loadActiveGoals() {
    this.userService.getAllGoal().subscribe({
      next: (goals: any[]) => {
        this.activeGoals = goals;
      },
      error: (error) => {
        console.error('Error fetching goals:', error);
      }
    });
  }
}