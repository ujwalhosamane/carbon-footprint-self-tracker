// src/app/models/predefined-goal.model.ts

export class PredefinedGoal {
    predefinedGoalId: number;
    creationDate: Date;
    title: string;
    type: string;
    description: string;
    targetScore: number;
    rewardPoint: number;
    badgeUrl: string;
  
    constructor(
      predefinedGoalId: number,
      creationDate: Date,
      title: string,
      type: string,
      description: string,
      targetScore: number,
      rewardPoint: number,
      badgeUrl: string
    ) {
      this.predefinedGoalId = predefinedGoalId;
      this.creationDate = creationDate;
      this.title = title;
      this.type = type;
      this.description = description;
      this.targetScore = targetScore;
      this.rewardPoint = rewardPoint;
      this.badgeUrl = badgeUrl;
    }
  }
  