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
  showSuccessToast: boolean = false;
  showErrorToast: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private adminService: AdminService
  ) {
    this.insightForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      description: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(255)]]
    });
  }

  ngOnInit(): void {
    this.loadInsights();
  }

  private showToast(isError: boolean, message: string) {
    if (isError) {
      this.showErrorToast = true;
      this.errorMessage = message;
    } else {
      this.showSuccessToast = true;
      this.successMessage = message;
    }

    setTimeout(() => {
      this.showErrorToast = false;
      this.showSuccessToast = false;
    }, 1500);
  }

  private loadInsights(): void {
    this.adminService.getAllInsights().subscribe({
      next: (insights) => {
        this.insights = insights || [];
        if (this.insights.length > 0) {
          this.sortInsightsByDate();
        }
      },
      error: (error) => {
        console.error('Error loading insights:', error);
        this.showToast(true, 'Failed to load insights');
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
        this.insightForm.value.title,
        this.insightForm.value.description,
        new Date()
      );

      this.adminService.addInsight(newInsight).subscribe({
        next: () => {
          this.loadInsights(); // Reload all insights after adding
          this.isSubmitting = false;
          this.closeAddModal();
          this.showToast(false, 'Insight added successfully');
        },
        error: (error) => {
          console.error('Error adding insight:', error);
          this.isSubmitting = false;
          this.showToast(true, 'Failed to add insight');
        }
      });
    }
  }

  deleteInsight(insight: GlobalInsight): void {
    this.adminService.deleteInsight(insight.insightId).subscribe({
      next: (response) => {
        if (response && response.message === "Successfully deleted") {
          this.loadInsights(); // Reload insights from backend after successful deletion
          this.showToast(false, 'Insight deleted successfully');
        }
      },
      error: (error) => {
        console.error('Error deleting insight:', error);
        this.showToast(true, 'Failed to delete insight');
      }
    });
  }

  getValidationMessage(): string {
    const titleControl = this.insightForm.get('title');
    const descControl = this.insightForm.get('description');
    
    if (titleControl?.hasError('required')) {
      return 'Title is required';
    }
    if (titleControl?.hasError('minlength')) {
      return 'Title must be at least 3 characters';
    }
    if (titleControl?.hasError('maxlength')) {
      return 'Title cannot exceed 100 characters';
    }
    if (descControl?.hasError('required')) {
      return 'Description is required';
    }
    if (descControl?.hasError('minlength')) {
      return 'Description must be at least 10 characters';
    }
    if (descControl?.hasError('maxlength')) {
      return 'Description cannot exceed 500 characters';
    }
    return '';
  }
}
