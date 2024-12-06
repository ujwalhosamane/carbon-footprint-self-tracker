import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import { SuggestionDTO } from '../../models/suggestion-dto';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'typewriter'
})
export class TypewriterPipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return '';
    return value;
  }
}

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {
  currentMonth: number;
  currentYear: number;
  currentSlide: number = 0;
  slideInterval: any;
  insights: string[] = [];
  goals: any[] = [];
  latestSuggestion: SuggestionDTO | null = null;
  regeneratedSuggestion: string = '';
  showRegeneratedSuggestion: boolean = false;
  isLoading: boolean = false;
  typingSpeed: number = 15; 
  currentTypingIndex: number = 0;
  typingInterval: any;

  constructor(
    private router: Router,
    private userService: UserService
  ) {
    const today = new Date();
    this.currentMonth = today.getMonth() + 1;
    this.currentYear = today.getFullYear();
  }

  ngOnInit(): void {
    this.startSlideShow();
    
    this.userService.getNInsightsForUser(3).subscribe({
      next: (response) => {
        if (response) {
          this.insights = Object.entries(response).map(([title, description]) => 
            `${title}: ${description}`
          );
        } else {
          this.insights = [];
        }
      },
      error: (error) => {
        console.error('Error fetching insights:', error);
        this.insights = [];
      }
    });

    this.userService.getAllGoal().subscribe({
      next: (goals) => {
        this.goals = goals;
      },
      error: (error) => {
        console.error('Error fetching goals:', error);
      }
    });

    this.userService.getLatestSuggestion().subscribe({
      next: (suggestion) => {
        if (suggestion) {
          this.latestSuggestion = suggestion;
          this.startTypewriter(suggestion.description);
        }
      },
      error: (error) => {
        console.error('Error fetching latest suggestion:', error);
        this.latestSuggestion = null;
      }
    });
  }

  ngOnDestroy(): void {
    if (this.slideInterval) {
      clearInterval(this.slideInterval);
    }
    if (this.typingInterval) {
      clearInterval(this.typingInterval);
    }
  }

  startTypewriter(text: string) {
    if (this.typingInterval) {
      clearInterval(this.typingInterval);
    }
    this.currentTypingIndex = 0;
    
    this.typingInterval = setInterval(() => {
      if (this.currentTypingIndex < text.length) {
        this.currentTypingIndex++;
      } else {
        clearInterval(this.typingInterval);
      }
    }, this.typingSpeed);
  }

  goToSlide(index: number): void {
    this.currentSlide = index;
    this.startSlideShow();
  }

  private startSlideShow(): void {
    if (this.slideInterval) {
      clearInterval(this.slideInterval);
    }
    
    this.slideInterval = setInterval(() => {
      this.currentSlide = (this.currentSlide + 1) % 3;
    }, 5000);
  }

  regenrateSuggestion(): void {
    if (!this.latestSuggestion) return;

    this.isLoading = true;
    this.userService.getRegeneratedSuggestion(this.latestSuggestion.suggestionId).subscribe({
      next: (response) => {
        const suggestionString = response.suggestion;
        this.regeneratedSuggestion = suggestionString;
        this.showRegeneratedSuggestion = true;
        this.isLoading = false;
        this.startTypewriter(suggestionString);
      },
      error: (error) => {
        console.error('Error regenerating suggestion:', error);
        this.isLoading = false;
      }
    });
  }

  selectSuggestion(isRegeneratedSelected: boolean): void {
    if (!this.latestSuggestion) return;

    this.isLoading = true;
    const selectedDescription = isRegeneratedSelected ? 
      this.regeneratedSuggestion : 
      this.latestSuggestion.description;

    const updatedSuggestion = new SuggestionDTO(
      this.latestSuggestion.suggestionId,
      selectedDescription,
      new Date().toISOString()
    );

    this.userService.updateRegeneratedSuggestion(updatedSuggestion).subscribe({
      next: (suggestion: SuggestionDTO) => {
        this.latestSuggestion = suggestion;
        this.showRegeneratedSuggestion = false;
        this.regeneratedSuggestion = '';
        this.isLoading = false;
        this.startTypewriter(suggestion.description);
      },
      error: (error) => {
        console.error('Error updating suggestion:', error);
        this.isLoading = false;
      }
    });
  }
}
