export class LeaderBoardOnSixMonthRewardPoints {
    name: string;
    city: string;
    sixMonthRewardPoints: number;
    isCurrentUser: boolean;
  
    constructor(
      name: string,
      city: string,
      sixMonthRewardPoints: number,
      isCurrentUser: boolean = false
    ) {
      this.name = name;
      this.city = city;
      this.sixMonthRewardPoints = sixMonthRewardPoints;
      this.isCurrentUser = isCurrentUser;
    }
}