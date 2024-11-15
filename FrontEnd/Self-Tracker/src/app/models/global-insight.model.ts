export class GlobalInsight {
    insightId: number;
    description: string;
    date: Date;
  
    constructor(insightId: number, description: string, date: Date) {
      this.insightId = insightId;
      this.description = description;
      this.date = date;
    }
  }
  