import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PredefinedGoal } from 'src/app/models/predefined-goal.model';

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

  predefinedGoals: PredefinedGoal[] = [
    new PredefinedGoal(
      1,
      new Date(),
      'Transportation Efficiency',
      'Transportation', 
      'Achieve 20% reduction in transportation costs.',
      85,
      100,
      'https://example.com/badge1.png'
    ),
    new PredefinedGoal(
      2,
      new Date(),
      'Energy Conservation',
      'Electricity',
      'Reduce energy consumption by 15% in 6 months.',
      90,
      120,
      'https://example.com/badge2.png'
    ),
    new PredefinedGoal(
      3, 
      new Date(),
      'LPG Usage Reduction',
      'Lpg',
      'Reduce LPG usage by 10% in 3 months.',
      80,
      110,
      'https://example.com/badge3.png'
    ),
    new PredefinedGoal(
      4,
      new Date(),
      'Shipping Cost Reduction', 
      'Shipping',
      'Decrease shipping costs by 12% in the next quarter.',
      87,
      95,
      'https://example.com/badge4.png'
    ),
    new PredefinedGoal(
      5,
      new Date(),
      'Air Conditioner Efficiency',
      'AirConditioner',
      'Improve air conditioner energy efficiency by 18%.',
      92,
      130,
      'https://example.com/badge5.png'
    )
  ];

  goalTypes: string[] = ['Transportation', 'Electricity', 'Lpg', 'Shipping', 'AirConditioner'];

  constructor(private fb: FormBuilder) {
    this.goalForm = this.fb.group({
      title: ['', Validators.required],
      type: ['', Validators.required], 
      description: ['', Validators.required],
      targetScore: ['', [Validators.required, Validators.min(1)]],
      rewardPoint: ['', [Validators.required, Validators.min(1)]]
    });

    this.editForm = this.fb.group({
      title: ['', Validators.required],
      type: ['', Validators.required],
      description: ['', Validators.required], 
      targetScore: ['', [Validators.required, Validators.min(1)]],
      rewardPoint: ['', [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.updateGoalCounts();
    this.calculateTotalPages();
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

  openEditModal(goal: any) {
    this.selectedGoal = goal;
    this.isEditModalOpen = true;
    this.isViewModalOpen = false;
    this.editForm.patchValue({
      title: goal.title,
      type: goal.type,
      description: goal.description,
      targetScore: goal.targetScore,
      rewardPoint: goal.rewardPoint
    });
  }

  closeEditModal() {
    this.isEditModalOpen = false;
    this.selectedGoal = null;
  }

  onAddSubmit() {
    if (this.goalForm.valid && !this.isSubmitting) {
      this.isSubmitting = true;
      const newGoal = new PredefinedGoal(
        this.predefinedGoals.length + 1,
        new Date(),
        this.goalForm.value.title,
        this.goalForm.value.type,
        this.goalForm.value.description,
        this.goalForm.value.targetScore,
        this.goalForm.value.rewardPoint,
        'https://example.com/badge.png'
      );
      
      this.predefinedGoals.push(newGoal);
      this.updateGoalCounts();
      this.calculateTotalPages();
      this.isSubmitting = false;
      this.closeAddModal();
    }
  }

  onEditSubmit() {
    if (this.editForm.valid && !this.isSubmitting && this.selectedGoal) {
      this.isSubmitting = true;
      
      const index = this.predefinedGoals.findIndex(goal => goal.predefinedGoalId === this.selectedGoal.predefinedGoalId);
      if (index !== -1) {
        this.predefinedGoals[index] = {
          ...this.selectedGoal,
          ...this.editForm.value
        };
      }

      this.updateGoalCounts();
      this.isSubmitting = false;
      this.closeEditModal();
    }
  }

  deleteGoal(goal: PredefinedGoal) {
    const index = this.predefinedGoals.findIndex(g => g.predefinedGoalId === goal.predefinedGoalId);
    if (index !== -1) {
      this.predefinedGoals.splice(index, 1);
      this.updateGoalCounts();
      this.calculateTotalPages();
      this.closeEditModal();
      this.closeViewModal();
    }
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
