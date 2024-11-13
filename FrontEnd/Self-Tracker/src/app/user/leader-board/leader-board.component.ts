import { Component, OnInit } from '@angular/core';
import { LeaderboardService } from '../services/leaderboard.service';


@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.css']
})
export class LeaderboardComponent implements OnInit {
  // Leaderboard data from backend
  selectedTab: number = 0;  // Default to the first tab (0 = Reward Points)
  currentPage: number = 1;
  itemsPerPage: number = 10;

  tabs = [
    { title: 'Reward Points (6 Months)' },
    { title: 'Total Reward Points' },
    { title: 'Total Carbon Footprint' }
  ];

  sixMonthsLeaderboard = [];

  totalRewardPointsLeaderboard = [];

  totalCarbonFootprintLeaderboard = [];

  constructor(private leaderboardService: LeaderboardService) {}

  ngOnInit(): void {
    // Fetch your leaderboard data here from the backend if needed
    // Fetch the six months leaderboard
  this.leaderboardService.getSixMonthsLeaderboard().subscribe(data => {
    this.sixMonthsLeaderboard = data;
  });

  // Fetch the total reward points leaderboard
  this.leaderboardService.getTotalRewardPointsLeaderboard().subscribe(data => {
    this.totalRewardPointsLeaderboard = data;
  });

  // Fetch the total carbon footprint leaderboard
  this.leaderboardService.getTotalCarbonFootprintLeaderboard().subscribe(data => {
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
