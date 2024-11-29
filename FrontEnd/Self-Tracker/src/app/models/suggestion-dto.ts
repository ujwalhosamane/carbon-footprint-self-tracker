export class SuggestionDTO {
    suggestionId: number;
    description: string;
    creationDate: string;

    constructor(suggestionId: number, description: string, creationDate: string) {
        this.suggestionId = suggestionId;
        this.description = description;
        this.creationDate = creationDate;
      }
  }
  