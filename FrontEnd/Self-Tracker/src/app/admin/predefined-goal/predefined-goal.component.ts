import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PredefinedGoal } from 'src/app/models/predefined-goal.model';
import { PredefinedGoalDTO } from 'src/app/models/predefined-goal-dto.model';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-predefined-goal',
  templateUrl: './predefined-goal.component.html',
  styleUrls: ['./predefined-goal.component.css']
})
export class PredefinedGoalComponent implements OnInit {
  viewType: string = 'view'; 
  isAddModalOpen: boolean = false;
  isEditModalOpen: boolean = false;
  isSubmitting: boolean = false;
  isViewModalOpen: boolean = false;
  selectedGoal: any = null;
  goalForm: FormGroup;
  editForm: FormGroup;

  // Toast related properties
  toasts: Array<{message: string, type: 'success' | 'error'}> = [];

  searchTerm: string = '';
  selectedType: string = '';
  originalGoals: PredefinedGoal[] = [];
  filteredGoals: PredefinedGoal[] = [];

  currentPage: number = 1;
  pageSize: number = 8;
  totalPages: number = 0; // Initialize to 0 instead of 1

  // Goal count variables
  totalGoals: number = 0;
  transportationGoals: number = 0;
  electricityGoals: number = 0;
  lpgGoals: number = 0;
  shippingGoals: number = 0;
  airConditionerGoals: number = 0;

  predefinedGoals: PredefinedGoal[] = [];
  isLoading: boolean = false;
  sortField: string = 'title';
  sortDirection: 'asc' | 'desc' = 'asc';

  goalTypes: string[] = ['Transportation', 'Electricity', 'Lpg', 'Shipping', 'AirConditioner'];

  constructor(
    private fb: FormBuilder,
    private adminService: AdminService
  ) {
    this.goalForm = this.fb.group({
      title: ['', [Validators.required, Validators.maxLength(255)]],
      type: ['', Validators.required], 
      description: ['', [Validators.required, Validators.maxLength(255)]],
      targetScore: ['', [Validators.required, Validators.min(1)]],
      rewardPoint: ['', [Validators.required, Validators.min(1)]],
      badgeUrl: ['', [Validators.required, Validators.maxLength(255), Validators.pattern('https?://.+')]]
    });

    this.editForm = this.fb.group({
      title: ['', [Validators.required, Validators.maxLength(255)]],
      type: ['', Validators.required],
      description: ['', [Validators.required, Validators.maxLength(255)]], 
      targetScore: ['', [Validators.required, Validators.min(1)]],
      rewardPoint: ['', [Validators.required, Validators.min(1)]],
      badgeUrl: ['', [Validators.required, Validators.maxLength(255), Validators.pattern('https?://.+')]]
    });

    this.editForm.get('type')?.valueChanges.subscribe(value => {
      if (this.selectedGoal) {
        this.selectedGoal.type = value;
      }
    });
  }

  ngOnInit(): void {
    this.loadPredefinedGoals();
  }

  showToast(message: string, type: 'success' | 'error') {
    const toast = { message, type };
    this.toasts.push(toast);
    setTimeout(() => {
      const index = this.toasts.indexOf(toast);
      if (index > -1) {
        this.toasts.splice(index, 1);
      }
    }, 3000);
  }

  private loadPredefinedGoals(): void {
    this.isLoading = true;
    this.adminService.getAllPredefinedGoal().subscribe({
      next: (goals) => {
        this.predefinedGoals = goals;
        this.originalGoals = [...goals];
        this.filteredGoals = [...goals];
        this.updateGoalCounts();
        this.calculateTotalPages();
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading predefined goals:', error);
        this.showToast('Error loading goals', 'error');
        this.isLoading = false;
      }
    });
  }

  sortGoals(field: string) {
    if (this.sortField === field) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = field;
      this.sortDirection = 'asc';
    }

    this.filteredGoals.sort((a: any, b: any) => {
      const multiplier = this.sortDirection === 'asc' ? 1 : -1;
      return a[field] > b[field] ? multiplier : -multiplier;
    });
    
    this.predefinedGoals = [...this.filteredGoals];
    this.calculateTotalPages();
  }

  private calculateTotalPages(): void {
    this.totalPages = Math.ceil(this.predefinedGoals.length / this.pageSize) || 1;
  }

  get canGoToNextPage(): boolean {
    return this.currentPage < this.totalPages && this.predefinedGoals.length > 0;
  }

  get canGoToPreviousPage(): boolean {
    return this.currentPage > 1 && this.predefinedGoals.length > 0;
  }

  get paginatedGoals(): PredefinedGoal[] {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    return this.predefinedGoals.slice(startIndex, endIndex);
  }

  nextPage(): void {
    if (this.canGoToNextPage) {
      this.currentPage++;
    }
  }

  previousPage(): void {
    if (this.canGoToPreviousPage) {
      this.currentPage--;
    }
  }

  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages && this.predefinedGoals.length > 0) {
      this.currentPage = page;
    }
  }

  private updateGoalCounts(): void {
    this.totalGoals = this.predefinedGoals.length;
    this.transportationGoals = this.predefinedGoals.filter(goal => goal.type === 'Transportation').length;
    this.electricityGoals = this.predefinedGoals.filter(goal => goal.type === 'Electricity').length;
    this.lpgGoals = this.predefinedGoals.filter(goal => goal.type === 'Lpg').length;
    this.shippingGoals = this.predefinedGoals.filter(goal => goal.type === 'Shipping').length;
    this.airConditionerGoals = this.predefinedGoals.filter(goal => goal.type === 'AirConditioner').length;
  }

  openAddModal() {
    this.isAddModalOpen = true;
    this.goalForm.reset();
  }

  closeAddModal() {
    this.isAddModalOpen = false;
  }

  openViewModal(goal: PredefinedGoal) {
    this.selectedGoal = goal;
    this.isViewModalOpen = true;
    this.isEditModalOpen = false;
  }

  closeViewModal() {
    this.isViewModalOpen = false;
    this.selectedGoal = null;
  }

  openEditModal(goal: PredefinedGoal) {
    this.selectedGoal = {...goal};
    this.isEditModalOpen = true;
    this.isViewModalOpen = false;
    this.editForm.patchValue({
      title: goal.title,
      type: goal.type,
      description: goal.description,
      targetScore: goal.targetScore,
      rewardPoint: goal.rewardPoint,
      badgeUrl: goal.badgeUrl
    });
  }

  closeEditModal() {
    this.isEditModalOpen = false;
    this.selectedGoal = null;
  }

  onAddSubmit() {
    if (this.goalForm.valid && !this.isSubmitting) {
      this.isSubmitting = true;
      
      const newGoalDto = new PredefinedGoalDTO(
        this.goalForm.value.title,
        this.goalForm.value.type,
        this.goalForm.value.description,
        this.goalForm.value.targetScore,
        this.goalForm.value.rewardPoint,
        this.goalForm.value.badgeUrl
      );
      
      this.adminService.addPredefinedGoal(newGoalDto).subscribe({
        next: (response) => {
          this.predefinedGoals.push(response);
          this.originalGoals.push(response);
          this.filteredGoals.push(response);
          this.updateGoalCounts();
          this.calculateTotalPages();
          this.isSubmitting = false;
          this.closeAddModal();
          this.showToast('Goal added successfully', 'success');
        },
        error: (error) => {
          console.error('Error adding predefined goal:', error);
          this.isSubmitting = false;
          this.showToast('Error adding goal', 'error');
        }
      });
    } else {
      this.showToast('Please fill all required fields correctly', 'error');
    }
  }

  onEditSubmit() {
    if (this.editForm.valid && !this.isSubmitting && this.selectedGoal) {
      this.isSubmitting = true;
      const updatedGoalDto = new PredefinedGoalDTO(
        this.editForm.value.title,
        this.editForm.value.type,
        this.editForm.value.description,
        this.editForm.value.targetScore,
        this.editForm.value.rewardPoint,
        this.editForm.value.badgeUrl
      );

      this.adminService.updatePredefinedGoal(updatedGoalDto, this.selectedGoal.predefinedGoalId).subscribe({
        next: () => {
          const index = this.predefinedGoals.findIndex(g => g.predefinedGoalId === this.selectedGoal.predefinedGoalId);
          if (index !== -1) {
            const updatedGoal = {
              ...this.predefinedGoals[index],
              ...updatedGoalDto
            };
            this.predefinedGoals[index] = updatedGoal;
            
            // Update in original and filtered arrays
            const originalIndex = this.originalGoals.findIndex(g => g.predefinedGoalId === this.selectedGoal.predefinedGoalId);
            if (originalIndex !== -1) {
              this.originalGoals[originalIndex] = updatedGoal;
            }
            
            const filteredIndex = this.filteredGoals.findIndex(g => g.predefinedGoalId === this.selectedGoal.predefinedGoalId);
            if (filteredIndex !== -1) {
              this.filteredGoals[filteredIndex] = updatedGoal;
            }
            
            this.updateGoalCounts();
          }
          this.isSubmitting = false;
          this.closeEditModal();
          this.showToast('Goal updated successfully', 'success');
        },
        error: (error) => {
          console.error('Error updating predefined goal:', error);
          this.isSubmitting = false;
          this.showToast('Error updating goal', 'error');
        }
      });
    } else {
      this.showToast('Please fill all required fields correctly', 'error');
    }
  }

  deleteGoal(goal: PredefinedGoal) {
    if (confirm('Are you sure you want to delete this goal?')) {
      this.adminService.deletePredefinedGoal(goal.predefinedGoalId).subscribe({
        next: () => {
          this.predefinedGoals = this.predefinedGoals.filter(g => g.predefinedGoalId !== goal.predefinedGoalId);
          this.originalGoals = this.originalGoals.filter(g => g.predefinedGoalId !== goal.predefinedGoalId);
          this.filteredGoals = this.filteredGoals.filter(g => g.predefinedGoalId !== goal.predefinedGoalId);
          this.updateGoalCounts();
          this.calculateTotalPages();
          this.closeEditModal();
          this.closeViewModal();
          this.showToast('Goal deleted successfully', 'success');
        },
        error: (error) => {
          console.error('Error deleting predefined goal:', error);
          this.showToast('Error deleting goal', 'error');
        }
      });
    }
  }

  onSearch(event: any) {
    this.searchTerm = event.target.value;
    this.filterGoals();
  }

  onFilterChange(event: any) {
    this.selectedType = event;
    this.filterGoals();
  }

  clearFilters() {
    this.searchTerm = '';
    this.selectedType = '';
    this.predefinedGoals = [...this.originalGoals];
    this.filteredGoals = [...this.originalGoals];
    this.calculateTotalPages();
    this.currentPage = 1;
    this.updateGoalCounts();
    this.showToast('Filters cleared', 'success');
  }

  private filterGoals() {
    this.filteredGoals = [...this.originalGoals];
    
    if (this.searchTerm) {
      this.filteredGoals = this.filteredGoals.filter(goal => 
        goal.title.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        goal.description.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }

    if (this.selectedType) {
      this.filteredGoals = this.filteredGoals.filter(goal => goal.type === this.selectedType);
    }

    this.predefinedGoals = [...this.filteredGoals];
    this.calculateTotalPages();
    this.currentPage = 1;
    this.updateGoalCounts();
    
    if (this.searchTerm || this.selectedType) {
      this.showToast(`Found ${this.filteredGoals.length} matching goals`, 'success');
    }
  }
}
