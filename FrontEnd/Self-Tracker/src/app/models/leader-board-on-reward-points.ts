export class LeaderBoardOnRewardPoints {
  name: string;
  city: string;
  totalRewardPoints: number;
  isCurrentUser: boolean;

  constructor(name: string, city: string, totalRewardPoints: number, isCurrentUser: boolean) {
    this.name = name;
    this.city = city;
    this.totalRewardPoints = totalRewardPoints;
    this.isCurrentUser = isCurrentUser;
  }
}
