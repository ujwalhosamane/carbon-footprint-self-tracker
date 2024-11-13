export class LeaderBoardOnFootprint {
    name: string;
    city: string;
    totalFootprint: number;
    isCurrentUser: boolean;
  
    constructor(
      name: string,
      city: string,
      totalFootprint: number,
      isCurrentUser: boolean = false
    ) {
      this.name = name;
      this.city = city;
      this.totalFootprint = totalFootprint;
      this.isCurrentUser = isCurrentUser;
    }
  }
