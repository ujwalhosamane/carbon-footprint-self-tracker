import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { LeaderBoardOnSixMonthRewardPoints } from '../../models/leader-board-on-six-month-reward-points';
import { LeaderBoardOnRewardPoints } from '../../models/leader-board-on-reward-points';
import { LeaderBoardOnFootprint } from '../../models/leader-board-on-footprint';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leader-board.component.html',
  styleUrls: ['./leader-board.component.css']
})
export class LeaderBoardComponent implements OnInit {
  
  tabs = [
    { title: 'Reward Points (6 Months)' },
    { title: 'Total Reward Points' },
    { title: 'Total Carbon Footprint' }
  ];

 
  sixMonthsLeaderboard: LeaderBoardOnSixMonthRewardPoints[] = [];
  totalRewardPointsLeaderboard: LeaderBoardOnRewardPoints[] = [];
  totalCarbonFootprintLeaderboard: LeaderBoardOnFootprint[] = [];

  selectedTab = 0;
  currentPage = 1;
  totalPages = 1;
  readonly pageSize = 10;

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.loadLeaderboardData();
  }

  loadLeaderboardData() {
    forkJoin({
      sixMonths: this.userService.getLeaderBoardOnSixMonthRewardPoints(),
      totalRewards: this.userService.getLeaderBoardOnRewardPoints(),
      carbonFootprint: this.userService.getLeaderBoardOnFootprint()
    }).subscribe(({ sixMonths, totalRewards, carbonFootprint }) => {
      this.sixMonthsLeaderboard = sixMonths;
      this.totalRewardPointsLeaderboard = totalRewards;
      this.totalCarbonFootprintLeaderboard = carbonFootprint;
      
     
      const currentData = this.selectedTab === 0 ? sixMonths : 
                         this.selectedTab === 1 ? totalRewards : 
                         carbonFootprint;
      this.calculateTotalPages(currentData.length);
    });
  }

  calculateTotalPages(totalItems: number) {
    this.totalPages = Math.ceil(totalItems / this.pageSize);
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

}
