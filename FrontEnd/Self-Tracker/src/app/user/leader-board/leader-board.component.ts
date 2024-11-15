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
  
  
}
