export class PredefinedGoalDTO {
    title: string;
    type: string;
    description: string;
    targetScore: number;
    rewardPoint: number;
    badgeUrl: string;
  
    constructor(
      title: string,
      type: string,
      description: string,
      targetScore: number,
      rewardPoint: number,
      badgeUrl: string
    ) {
      this.title = title;
      this.type = type;
      this.description = description;
      this.targetScore = targetScore;
      this.rewardPoint = rewardPoint;
      this.badgeUrl = badgeUrl;
    }
  }
  