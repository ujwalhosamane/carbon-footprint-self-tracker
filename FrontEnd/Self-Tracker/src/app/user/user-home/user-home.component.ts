import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

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
  }

  ngOnDestroy(): void {
    if (this.slideInterval) {
      clearInterval(this.slideInterval);
    }
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
}
