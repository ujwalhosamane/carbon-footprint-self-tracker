export class GlobalInsight {
    insightId: number;
    title: string;
    description: string;
    date: Date;
  
    constructor(insightId: number, title: string, description: string, date: Date) {
      this.insightId = insightId;
      this.title = title;
      this.description = description;
      this.date = date;
    }
  }
  