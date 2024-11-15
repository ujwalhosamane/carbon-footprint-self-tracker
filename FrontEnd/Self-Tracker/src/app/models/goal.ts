export class Goal {
    goalId: number;
    currentScore: number;
    count: number;
    isAchieved: boolean;
  
    creationDate: Date;
    title: string;
    type: string;
    description: string;
    targetScore: number;
    rewardPoint: number;
    badgeUrl: string;
  
    constructor(
      goalId: number,
      currentScore: number,
      count: number,
      isAchieved: boolean,
      creationDate: Date,
      title: string,
      type: string,
      description: string,
      targetScore: number,
      rewardPoint: number,
      badgeUrl: string
    ) {
      this.goalId = goalId;
      this.currentScore = currentScore;
      this.count = count;
      this.isAchieved = isAchieved;
      this.creationDate = creationDate;
      this.title = title;
      this.type = type;
      this.description = description;
      this.targetScore = targetScore;
      this.rewardPoint = rewardPoint;
      this.badgeUrl = badgeUrl;
    }
  }
  