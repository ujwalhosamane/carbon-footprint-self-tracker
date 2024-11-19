import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css']
})
export class AnalyticsComponent implements OnInit {
  thisMonthUsers: number = 0;
  lastMonthUsers: number = 0;
  totalUsers: number = 0;
  latestUser: any;
  latestLogin: any;

  totalRewardPoints: number = 0;
  totalFootprint: number = 0;
  thisMonthFootprint: number = 0;
  lastMonthFootprint: number = 0;
  topPerformers: any[] = [];

  goalsByType: any[] = [];
  recentGoals: any[] = [];
  totalCompletedGoals: number = 0;

  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.loadAllMetrics();
  }

  private loadAllMetrics() {
    this.loadUserMetrics();
    this.loadPerformanceMetrics();
    this.loadGoalMetrics();
  }

  private loadUserMetrics() {
    this.adminService.getThisMonthUserCount().subscribe({
      next: (response) => this.thisMonthUsers = response.count,
      error: (error) => console.error('Error loading this month users:', error)
    });

    this.adminService.getLastMonthUserCount().subscribe({
      next: (response) => this.lastMonthUsers = response.count,
      error: (error) => console.error('Error loading last month users:', error)
    });

    this.adminService.getTotalUsersCount().subscribe({
      next: (response) => this.totalUsers = response.count,
      error: (error) => console.error('Error loading total users:', error)
    });

    this.adminService.getLatestUserAccount().subscribe({
      next: (users) => {
        this.latestUser = users.length > 0 ? {
          name: users[0].Name,
          lastActivityTime: users[0].LastActivityTime
        } : null;
      },
      error: (error) => console.error('Error loading latest user:', error)
    });

    this.adminService.getLatestUserLogin().subscribe({
      next: (activities) => {
        this.latestLogin = activities.length > 0 ? {
          name: activities[0].Name,
          lastActivityTime: activities[0].LastActivityTime
        } : null;
      },
      error: (error) => console.error('Error loading latest login:', error)
    });
  }

  private loadPerformanceMetrics() {
    this.adminService.getTotalRewrdPointsAndFootprint().subscribe({
      next: (data) => {
        this.totalRewardPoints = data.TotalRewardPoints;
        this.totalFootprint = data.TotalFootprint;
      },
      error: (error) => console.error('Error loading rewards and footprint:', error)
    });

    this.adminService.getTotalFootprintThisAndLastMonth().subscribe({
      next: (data: any) => {
        if (!data || Object.keys(data).length === 0) {
          this.thisMonthFootprint = 0;
          this.lastMonthFootprint = 0;
          return;
        }

        if (!Array.isArray(data)) {
          console.error('Expected array but received:', typeof data);
          return;
        }

        this.thisMonthFootprint = data[0]?.totalFootprint || 0;
        this.lastMonthFootprint = data[1]?.totalFootprint || 0;
      },
      error: (error) => console.error('Error loading footprint comparison:', error)
    });

    this.adminService.getTopTwoPerformersByTotalRewaedPoints().subscribe({
      next: (performers) => this.topPerformers = performers,
      error: (error) => console.error('Error loading top performers:', error)
    });
  }

  private loadGoalMetrics() {
    this.adminService.getPredefinedGoalAndItsCompletedCountByType().subscribe({
      next: (goals: Array<{
        type: string,
        title: string, 
        description: string,
        totalCount: number
      }>) => this.goalsByType = goals,
      error: (error) => console.error('Error loading goals by type:', error)
    });

    this.adminService.getRecentlyCreatedPredefinedGoal().subscribe({
      next: (goals: Array<{
        title: string,
        type: string,
        createdDate: string
      }>) => this.recentGoals = goals,
      error: (error) => console.error('Error loading recent goals:', error)
    });

    this.adminService.getTotalCompletedPredefinedGoal().subscribe({
      next: (response) => this.totalCompletedGoals = response.totalCount,
      error: (error) => console.error('Error loading total completed goals:', error)
    });
  }

  calculatePercentageChange(current: number | any, previous: number | any): any {
    const currentValue = typeof current === 'object' ? current?.count || 0 : current;
    const previousValue = typeof previous === 'object' ? previous?.count || 0 : previous;
    
    if (previousValue === 0.0) return currentValue * 100;
    return ((currentValue - previousValue) / previousValue * 100).toFixed(1);
  }
}
