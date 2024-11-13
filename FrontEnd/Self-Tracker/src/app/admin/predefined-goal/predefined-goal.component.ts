import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-predefined-goal',
  templateUrl: './predefined-goal.component.html',
  styleUrls: ['./predefined-goal.component.css']
})
export class PredefinedGoalComponent {
  viewType: string = 'view'; // 'view' or 'add' or 'edit'
  isEditModalOpen: boolean = false; // State to handle modal visibility
  selectedGoal: any = null;
  predefinedGoalForm: FormGroup;

  // Dummy predefined goals
  predefinedGoals = [
    {
      id: 1,
      title: 'Transportation Efficiency',
      type: 'Transportation',
      description: 'Achieve 20% reduction in transportation costs.',
      targetScore: 85,
      rewardPoint: 100,
      badgeUrl: 'https://example.com/badge1.png'
    },
    {
      id: 2,
      title: 'Energy Conservation',
      type: 'Electricity',
      description: 'Reduce energy consumption by 15% in 6 months.',
      targetScore: 90,
      rewardPoint: 120,
      badgeUrl: 'https://example.com/badge2.png'
    },
    {
      id: 3,
      title: 'LPG Usage Reduction',
      type: 'Lpg',
      description: 'Reduce LPG usage by 10% in 3 months.',
      targetScore: 80,
      rewardPoint: 110,
      badgeUrl: 'https://example.com/badge3.png'
    },
    {
      id: 4,
      title: 'Shipping Cost Reduction',
      type: 'Shipping',
      description: 'Decrease shipping costs by 12% in the next quarter.',
      targetScore: 87,
      rewardPoint: 95,
      badgeUrl: 'https://example.com/badge4.png'
    },
    {
      id: 5,
      title: 'Air Conditioner Efficiency',
      type: 'AirConditioner',
      description: 'Improve air conditioner energy efficiency by 18%.',
      targetScore: 92,
      rewardPoint: 130,
      badgeUrl: 'https://example.com/badge5.png'
    }
  ];

  goalTypes: string[] = ['Transportation', 'Electricity', 'LPG', 'Shipping', 'AirConditioner'];

  constructor(private fb: FormBuilder) {
    this.predefinedGoalForm = this.fb.group({
      title: ['', Validators.required],
      type: ['', Validators.required],
      description: ['', Validators.required],
      targetScore: ['', Validators.required],
      rewardPoint: ['', Validators.required],
      badgeUrl: ['', Validators.required]
    });
  }

  // Open the Edit Modal for modifying the selected goal
  openEditGoal(goal: any) {
    this.selectedGoal = goal;
    this.isEditModalOpen = true;
    // Populate the form with the selected goal data
    this.predefinedGoalForm.patchValue({
      title: goal.title,
      type: goal.type,
      description: goal.description,
      targetScore: goal.targetScore,
      rewardPoint: goal.rewardPoint,
      badgeUrl: goal.badgeUrl
    });
  }

  // Close the Edit Modal
  closeEditModal() {
    this.isEditModalOpen = false;
    this.selectedGoal = null;
  }

  // Handle form submission (update the goal)
  onSubmit() {
    if (this.predefinedGoalForm.valid) {
      if (this.selectedGoal) {
        console.log('Updated goal:', this.predefinedGoalForm.value);
        this.closeEditModal(); // Close modal after submission
      } else {
        console.log('Added new goal:', this.predefinedGoalForm.value);
      }
    }
  }

  // Toggle between view types ('view', 'add', or 'edit')
  toggleView(view: string, goal?: any) {
    this.viewType = view;
    if (view === 'edit' && goal) {
      this.openEditGoal(goal); // Open edit modal with goal data
    } else if (view === 'add') {
      this.selectedGoal = null;
      this.predefinedGoalForm.reset();
    } else {
      this.closeEditModal(); // Close modal if switching to other views
    }
  }
}
