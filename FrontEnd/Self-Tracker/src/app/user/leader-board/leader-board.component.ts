import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service'; // Ensure correct path to your service

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leader-board.component.html',
  styleUrls: ['./leader-board.component.css']
})
export class LeaderBoardComponent implements OnInit {
  selectedTab: number = 0;  // Default to the first tab (0 = Reward Points)
  currentPage: number = 1;
  itemsPerPage: number = 10;

  tabs = [
    { title: 'Reward Points (6 Months)' },
    { title: 'Total Reward Points' },
    { title: 'Total Carbon Footprint' }
  ];

  // Data for different leaderboards
  sixMonthsLeaderboard: any[] = [];
  totalRewardPointsLeaderboard: any[] = [];
  totalCarbonFootprintLeaderboard: any[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    // Fetch the leaderboard data on component initialization

    // Fetch the six months leaderboard
    this.userService.getLeaderBoardOnSixMonthRewardPoints().subscribe(data => {
      this.sixMonthsLeaderboard = data;
    });

    // Fetch the total reward points leaderboard
    this.userService.getLeaderBoardOnRewardPoints().subscribe(data => {
      this.totalRewardPointsLeaderboard = data;
    });

    // Fetch the total carbon footprint leaderboard
    this.userService.getLeaderBoardOnFootprint().subscribe(data => {
      this.totalCarbonFootprintLeaderboard = data;
    });
  }

  selectTab(tabIndex: number): void {
    this.selectedTab = tabIndex;
    this.currentPage = 1;  // Reset to page 1 when switching tabs
  }

  currentPageData(leaderboard: any[]): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return leaderboard.slice(startIndex, endIndex);
  }

  getTotalPages(leaderboard: any[]): number {
    return Math.ceil(leaderboard.length / this.itemsPerPage);
  }

  prevPage(leaderboardType: string): void {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  nextPage(leaderboardType: string): void {
    const totalPages = this.getTotalPages(this.getLeaderboardData(leaderboardType));
    if (this.currentPage < totalPages) {
      this.currentPage++;
    }
  }

  getLeaderboardData(leaderboardType: string): any[] {
    if (leaderboardType === 'sixMonthsLeaderboard') {
      return this.sixMonthsLeaderboard;
    } else if (leaderboardType === 'totalRewardPointsLeaderboard') {
      return this.totalRewardPointsLeaderboard;
    } else if (leaderboardType === 'totalCarbonFootprintLeaderboard') {
      return this.totalCarbonFootprintLeaderboard;
    }
    return [];
  }
}
