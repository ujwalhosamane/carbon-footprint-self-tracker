import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { GlobalInsight } from 'src/app/models/global-insight.model';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-admin-insights',
  templateUrl: './admin-insights.component.html',
  styleUrls: ['./admin-insights.component.css']
})
export class AdminInsightsComponent implements OnInit {
  isAddModalOpen: boolean = false;
  isSubmitting: boolean = false;
  insightForm: FormGroup;
  insights: GlobalInsight[] = [];

  constructor(
    private fb: FormBuilder,
    private adminService: AdminService
  ) {
    this.insightForm = this.fb.group({
      description: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(500)]]
    });
  }

  ngOnInit(): void {
    this.loadInsights();
  }

  private loadInsights(): void {
    this.adminService.getAllInsights().subscribe({
      next: (insights) => {
        this.insights = insights;
        this.sortInsightsByDate();
      },
      error: (error) => {
        console.error('Error loading insights:', error);
      }
    });
  }

  private sortInsightsByDate(): void {
    this.insights.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime());
  }

  openAddModal(): void {
    this.isAddModalOpen = true;
    this.insightForm.reset();
  }

  closeAddModal(): void {
    this.isAddModalOpen = false;
    this.insightForm.reset();
  }

  onSubmit(): void {
    if (this.insightForm.valid && !this.isSubmitting) {
      this.isSubmitting = true;
      
      const newInsight = new GlobalInsight(
        0, // Backend will assign actual ID
        this.insightForm.value.description,
        new Date()
      );

      this.adminService.addInsight(newInsight).subscribe({
        next: () => {
          this.loadInsights(); // Reload all insights after adding
          this.isSubmitting = false;
          this.closeAddModal();
        },
        error: (error) => {
          console.error('Error adding insight:', error);
          this.isSubmitting = false;
        }
      });
    }
  }

  deleteInsight(insight: GlobalInsight): void {
    this.adminService.deleteInsight(insight.insightId).subscribe({
      next: () => {
        this.loadInsights(); // Reload all insights after deletion
      },
      error: (error) => {
        console.error('Error deleting insight:', error);
      }
    });
  }
}
