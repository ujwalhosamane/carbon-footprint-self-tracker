export class UserAfterLogin {
    name: string;
    email: string;
    city: string;
    creationDate: Date;
    totalFootprint: number;
    sixMonthRewardPoints: number;
    totalRewardPoints: number;
  
    constructor(
      name: string,
      email: string,
      city: string,
      creationDate: Date,
      totalFootprint: number,
      sixMonthRewardPoints: number,
      totalRewardPoints: number
    ) {
      this.name = name;
      this.email = email;
      this.city = city;
      this.creationDate = creationDate;
      this.totalFootprint = totalFootprint;
      this.sixMonthRewardPoints = sixMonthRewardPoints;
      this.totalRewardPoints = totalRewardPoints;
    }
  }