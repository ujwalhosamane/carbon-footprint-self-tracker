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
  viewType: string = 'view'; // 'view' or 'add' or 'edit'
  isAddModalOpen: boolean = false;
  isEditModalOpen: boolean = false;
  isSubmitting: boolean = false;
  isViewModalOpen: boolean = false;
  selectedGoal: any = null;
  goalForm: FormGroup;
  editForm: FormGroup;

  // Search and filter
  searchTerm: string = '';
  selectedType: string = '';

  // Pagination
  currentPage: number = 1;
  pageSize: number = 8;
  totalPages: number = 1;

  // Goal count variables
  totalGoals: number = 0;
  transportationGoals: number = 0;
  electricityGoals: number = 0;
  lpgGoals: number = 0;
  shippingGoals: number = 0;
  airConditionerGoals: number = 0;

  predefinedGoals: PredefinedGoal[] = [];

  goalTypes: string[] = ['Transportation', 'Electricity', 'Lpg', 'Shipping', 'AirConditioner'];

  constructor(
    private fb: FormBuilder,
    private adminService: AdminService
  ) {
    this.goalForm = this.fb.group({
      title: ['', Validators.required],
      type: ['', Validators.required], 
      description: ['', Validators.required],
      targetScore: ['', [Validators.required, Validators.min(1)]],
      rewardPoint: ['', [Validators.required, Validators.min(1)]],
      badgeUrl: ['', Validators.required]
    });

    this.editForm = this.fb.group({
      title: ['', Validators.required],
      type: ['', Validators.required],
      description: ['', Validators.required], 
      targetScore: ['', [Validators.required, Validators.min(1)]],
      rewardPoint: ['', [Validators.required, Validators.min(1)]],
      badgeUrl: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadPredefinedGoals();
  }

  private loadPredefinedGoals(): void {
    this.adminService.getAllPredefinedGoal().subscribe({
      next: (goals) => {
        this.predefinedGoals = goals;
        this.updateGoalCounts();
        this.calculateTotalPages();
      },
      error: (error) => {
        console.error('Error loading predefined goals:', error);
      }
    });
  }

  private calculateTotalPages(): void {
    this.totalPages = Math.ceil(this.predefinedGoals.length / this.pageSize);
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
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
    this.selectedGoal = goal;
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
        next: () => {
          this.loadPredefinedGoals();
          this.isSubmitting = false;
          this.closeAddModal();
        },
        error: (error) => {
          console.error('Error adding predefined goal:', error);
          this.isSubmitting = false;
        }
      });
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
          this.loadPredefinedGoals();
          this.isSubmitting = false;
          this.closeEditModal();
        },
        error: (error) => {
          console.error('Error updating predefined goal:', error);
          this.isSubmitting = false;
        }
      });
    }
  }

  deleteGoal(goal: PredefinedGoal) {
    this.adminService.deletePredefinedGoal(goal.predefinedGoalId).subscribe({
      next: () => {
        this.loadPredefinedGoals();
        this.closeEditModal();
        this.closeViewModal();
      },
      error: (error) => {
        console.error('Error deleting predefined goal:', error);
      }
    });
  }

  onSearch(event: any) {
    this.searchTerm = event.target.value;
    this.filterGoals();
  }

  onTypeFilter(event: any) {
    this.selectedType = event.target.value;
    this.filterGoals();
  }

  private filterGoals() {
    let filteredGoals = [...this.predefinedGoals];
    
    if (this.searchTerm) {
      filteredGoals = filteredGoals.filter(goal => 
        goal.title.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        goal.description.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }

    if (this.selectedType) {
      filteredGoals = filteredGoals.filter(goal => goal.type === this.selectedType);
    }

    this.predefinedGoals = filteredGoals;
    this.calculateTotalPages();
    this.currentPage = 1;
  }
}
