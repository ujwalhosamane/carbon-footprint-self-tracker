import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-admin-setting',
  templateUrl: './admin-setting.component.html',
  styleUrls: ['./admin-setting.component.css']
})
export class AdminSettingComponent implements OnInit {
  emissionFactorId: number = 0;
  transportation: number = 0;
  electricity: number = 0;
  lpg: number = 0;
  shipping: number = 0;
  airConditioner: number = 0;
  dataRetentionPeriod: number = 0;
  retentionDate: string = '';
  retentionOptions: number[] = [7, 9, 12, 18];
  showToast: boolean = false;
  toastMessage: string = '';
  readonly TOAST_DURATION: number = 1500; // 1.5 seconds in milliseconds
  emissionFactorsChanged: boolean = false;
  retentionPeriodChanged: boolean = false;

  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.getEmissionFactors();
    this.getDataRetentionDetails();
  }

  showToastMessage(message: string) {
    this.toastMessage = message;
    this.showToast = true;
    setTimeout(() => {
      this.showToast = false;
    }, this.TOAST_DURATION);
  }

  getEmissionFactors() {
    this.adminService.getEmissionFactor().subscribe({
      next: (response) => {
        this.emissionFactorId = response.emissionFactorId;
        this.transportation = response.transportation;
        this.electricity = response.electricity;
        this.lpg = response.lpg;
        this.shipping = response.shipping;
        this.airConditioner = response.airConditioner;
        this.emissionFactorsChanged = false;
      },
      error: (error) => {
        console.error('Error getting emission factors:', error);
        this.showToastMessage('Error loading emission factors');
      }
    });
  }

  updateEmissionFactors() {
    const data = {
      emissionFactorId: this.emissionFactorId,
      transportation: this.transportation,
      electricity: this.electricity,
      lpg: this.lpg,
      shipping: this.shipping,
      airConditioner: this.airConditioner
    };

    this.adminService.updateEmissionFactor(data).subscribe({
      next: (response) => {
        this.showToastMessage('Emission factors updated successfully');
        this.emissionFactorsChanged = false;
      },
      error: (error) => {
        console.error('Error updating emission factors:', error);
        this.showToastMessage('Error updating emission factors');
      }
    });
  }

  getDataRetentionDetails() {
    this.adminService.getDataRetentionPeriod().subscribe({
      next: (response) => {
        this.dataRetentionPeriod = response.count;
        // Add the current retention period to options if not already present
        if (!this.retentionOptions.includes(this.dataRetentionPeriod)) {
          this.retentionOptions.push(this.dataRetentionPeriod);
          this.retentionOptions.sort((a, b) => a - b);
        }
        this.getRetentionDate();
        this.retentionPeriodChanged = false;
      },
      error: (error) => {
        console.error('Error getting data retention period:', error);
        this.showToastMessage('Error loading data retention period');
      }
    });
  }

  getRetentionDate() {
    this.adminService.getRetentionDate(this.dataRetentionPeriod).subscribe({
      next: (response) => {
        this.retentionDate = response.retentionDate;
      },
      error: (error) => {
        console.error('Error getting retention date:', error);
        this.showToastMessage('Error loading retention date');
      }
    });
  }

  updateDataRetentionPeriod() {
    const data = {
      retentionPeriodMonths: this.dataRetentionPeriod
    };

    this.adminService.updateDataRetentionPeriod(data.retentionPeriodMonths).subscribe({
      next: (response) => {
        if (response.message === "Parameter updated successfully!") {
          this.showToastMessage('Data retention period updated successfully');
          this.getRetentionDate();
          this.retentionPeriodChanged = false;
        }
      },
      error: (error) => {
        console.error('Error updating data retention period:', error);
        this.showToastMessage('Error updating data retention period');
      }
    });
  }

  onRetentionPeriodChange() {
    this.getRetentionDate();
  }
}
