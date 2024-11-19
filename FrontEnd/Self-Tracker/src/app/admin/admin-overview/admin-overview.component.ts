import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';

interface Activity {
  type: 'registration' | 'login';
  description: string;
  time: string;
}

@Component({
  selector: 'app-admin-overview',
  templateUrl: './admin-overview.component.html',
  styleUrls: ['./admin-overview.component.css']
})
export class AdminOverviewComponent implements OnInit {
  totalUsers: number = 0;
  activeUsers: number = 0;
  newRegistrations: number = 0;
  recentActivities: Activity[] = [];
  growthPercentage: number | null = null;

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.loadDashboardData();
  }

  private loadDashboardData(): void {
    this.adminService.getTotalUsersCount().subscribe({
      next: (response) => {
        this.totalUsers = response.count;
      },
      error: (error) => {
        console.error('Error fetching total users:', error);
      }
    });

    this.adminService.getThisMonthUserCount().subscribe({
      next: (thisMonthResponse) => {
        this.newRegistrations = thisMonthResponse.count;
        
        this.adminService.getLastMonthUserCount().subscribe({
          next: (lastMonthResponse) => {
            const lastMonthCount = lastMonthResponse.count;
            if (lastMonthCount > 0) {
              this.growthPercentage = Math.round(((this.newRegistrations - lastMonthCount) / lastMonthCount) * 100);
            }
          },
          error: (error) => {
            console.error('Error fetching last month count:', error);
          }
        });
      },
      error: (error) => {
        console.error('Error fetching new registrations:', error);
      }
    });

    this.adminService.getLatestUserAccount().subscribe({
      next: (data) => {
        const registrationActivities = Array.isArray(data) ? data
          .filter((user: any) => user?.Name && user?.LastActivityTime)
          .map((user: any) => ({
            type: 'registration' as const,
            description: `New user registered: ${user.Name}`,
            time: user.LastActivityTime
          })) : [];
        
        this.adminService.getLatestUserLogin().subscribe({
          next: (loginData) => {
            const loginActivities = Array.isArray(loginData) ? loginData
              .filter((login: any) => login?.Name && login?.LastActivityTime)
              .map((login: any) => ({
                type: 'login' as const,
                description: `User login: ${login.Name}`,
                time: login.LastActivityTime
              })) : [];

            this.recentActivities = [...registrationActivities, ...loginActivities]
              .sort((a, b) => new Date(b.time).getTime() - new Date(a.time).getTime())
              .slice(0, 10);

            this.adminService.getCurrentLoggedInUserCount().subscribe({
              next: (response) => {
                this.activeUsers = response.count;
              },
              error: (error) => {
                console.error('Error fetching active users:', error);
              }
            });
          },
          error: (error) => {
            console.error('Error fetching login data:', error);
          }
        });
      },
      error: (error) => {
        console.error('Error fetching registration data:', error);
      }
    });
  }
}
