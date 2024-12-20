import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../user.service';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-user-footprint',
  templateUrl: './user-footprint.component.html',
  styleUrls: ['./user-footprint.component.css']
})
export class UserFootprintComponent implements OnInit {
  @ViewChild('month') month!: ElementRef;
  @ViewChild('year') year!: ElementRef;
  @ViewChild('transportation') transportation!: ElementRef;
  @ViewChild('electricity') electricity!: ElementRef;
  @ViewChild('lpg') lpg!: ElementRef;
  @ViewChild('shipping') shipping!: ElementRef;
  @ViewChild('ac') ac!: ElementRef;

  carbonFootprints: any[] = [];
  displayValues: any[] = [];
  availableMonths: any[] = [];
  availableYears: number[] = [];
  monthsForSelectedYear: string[] = [];
  emissionFactors: any;
  chart: any;
  selectedFootprint: any = null;
  showUpdateForm: boolean = false;
  showToast: boolean = false;
  toastMessage: string = '';
  toastType: 'success' | 'error' = 'success';
  loading: boolean = false;

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.getEmissionFactors();
    this.loadCarbonFootprints();
  }

  showToastMessage(message: string, type: 'success' | 'error') {
    this.toastMessage = message;
    this.toastType = type;
    this.showToast = true;
    setTimeout(() => {
      this.showToast = false;
    }, 3000);
  }

  getEmissionFactors() {
    this.loading = true;
    this.userService.getEmissionFactor().subscribe({
      next: (data) => {
        this.emissionFactors = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error fetching emission factors:', error);
        this.showToastMessage('Error fetching emission factors', 'error');
        this.loading = false;
      }
    });
  }

  calculateFootprint(input: any): any {
    if (!this.emissionFactors) return input;
    
    return {
      transportation: Number((input.transportation * this.emissionFactors.transportation).toFixed(2)),
      electricity: Number((input.electricity * this.emissionFactors.electricity).toFixed(2)),
      lpg: Number((input.lpg * this.emissionFactors.lpg).toFixed(2)),
      shipping: Number((input.shipping * this.emissionFactors.shipping).toFixed(2)),
      airConditioner: Number((input.airConditioner * this.emissionFactors.airConditioner).toFixed(2))
    };
  }

  convertBackToOriginal(input: any): any {
    if (!this.emissionFactors) return input;
    
    return {
      ...input,
      transportation: Number((input.transportation / this.emissionFactors.transportation).toFixed(2)),
      electricity: Number((input.electricity / this.emissionFactors.electricity).toFixed(2)), 
      lpg: Number((input.lpg / this.emissionFactors.lpg).toFixed(2)),
      shipping: Number((input.shipping / this.emissionFactors.shipping).toFixed(2)),
      airConditioner: Number((input.airConditioner / this.emissionFactors.airConditioner).toFixed(2))
    };
  }

  addFootprint() {
    if (!this.month.nativeElement.value || !this.year.nativeElement.value) {
      this.showToastMessage('Month and year are required', 'error');
      return;
    }

    this.loading = true;
    const rawData = {
      footprintMonth: this.month.nativeElement.value,
      footprintYear: parseInt(this.year.nativeElement.value),
      transportation: parseFloat(this.transportation.nativeElement.value) || 0,
      electricity: parseFloat(this.electricity.nativeElement.value) || 0,
      lpg: parseFloat(this.lpg.nativeElement.value) || 0,
      shipping: parseFloat(this.shipping.nativeElement.value) || 0,
      airConditioner: parseFloat(this.ac.nativeElement.value) || 0
    };

    const calculatedEmissions = this.calculateFootprint(rawData);
    const footprintData = {
      ...rawData,
      transportation: calculatedEmissions.transportation,
      electricity: calculatedEmissions.electricity,
      lpg: calculatedEmissions.lpg,
      shipping: calculatedEmissions.shipping,
      airConditioner: calculatedEmissions.airConditioner
    };

    this.userService.addCarbonFootprint(footprintData).subscribe({
      next: () => {
        this.loadCarbonFootprints();
        this.transportation.nativeElement.value = '';
        this.electricity.nativeElement.value = '';
        this.lpg.nativeElement.value = '';
        this.shipping.nativeElement.value = '';
        this.ac.nativeElement.value = '';
        this.showToastMessage('Carbon footprint added successfully', 'success');
        this.loading = false;
      },
      error: (error) => {
        console.error('Error adding footprint:', error);
        this.showToastMessage('Error adding carbon footprint', 'error');
        this.loading = false;
      }
    });
  }

  loadCarbonFootprints() {
    this.loading = true;
    this.userService.getLastNMonthsCarbonFootprint(6).subscribe({
      next: (data) => {
        if (!data) {
          this.carbonFootprints = [];
          this.generateAvailableMonths();
          this.loading = false;
          return;
        }

        const validFootprints = data.filter((footprint: any) => footprint !== null);
        
        this.carbonFootprints = validFootprints
          .sort((a: any, b: any) => {
            if (a.footprintYear !== b.footprintYear) {
              return b.footprintYear - a.footprintYear;
            }
            const months = ['January', 'February', 'March', 'April', 'May', 'June', 
                           'July', 'August', 'September', 'October', 'November', 'December'];
            return months.indexOf(b.footprintMonth) - months.indexOf(a.footprintMonth);
          });

        this.displayValues = [];
        
        this.carbonFootprints.forEach((footprint: any) => {
          this.displayValues.push(this.convertBackToOriginal(footprint));
        });
        
        this.generateAvailableMonths();
        
        if (this.carbonFootprints.length > 0) {
          setTimeout(() => {
            this.updateChart();
          }, 0);
        }
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading footprints:', error);
        this.showToastMessage('Error loading carbon footprints', 'error');
        this.carbonFootprints = [];
        this.generateAvailableMonths();
        this.loading = false;
      }
    });
  }

  generateAvailableMonths() {
    const months = ['January', 'February', 'March', 'April', 'May', 'June', 
                   'July', 'August', 'September', 'October', 'November', 'December'];
    const currentDate = new Date(); 
    const currentMonth = currentDate.getMonth();
    const currentYear = currentDate.getFullYear();
    
    let availableMonthsArray = [];
    for(let i = 0; i < 6; i++) {
      let monthIndex = currentMonth - i;
      let year = currentYear;
      
      if(monthIndex < 0) {
        monthIndex += 12;
        year--;
      }
      
      availableMonthsArray.push({
        month: months[monthIndex],
        year: year
      });
    }

    this.availableMonths = availableMonthsArray.filter(monthData => {
      return !this.carbonFootprints.some(footprint => {
        return footprint && 
               footprint.footprintMonth === monthData.month && 
               footprint.footprintYear === monthData.year;
      });
    });

    const groupedByYear = new Map();
    this.availableMonths.forEach(monthData => {
      if (!groupedByYear.has(monthData.year)) {
        groupedByYear.set(monthData.year, []);
      }
      groupedByYear.get(monthData.year).push(monthData.month);
    });

    this.availableYears = Array.from(groupedByYear.keys()).sort((a, b) => b - a);

    if (this.availableYears.length > 0) {
      this.updateMonthsForYear(this.availableYears[0]);
    }

    if (this.availableMonths.length === 0) {
      this.showToastMessage('No available months to add footprint data', 'error');
    }
  }

  updateMonthsForYear(selectedYear: number) {
    this.monthsForSelectedYear = this.availableMonths
      .filter(m => m.year === selectedYear)
      .map(m => m.month);
  }

  onYearChange(event: any) {
    const selectedYear = parseInt(event.target.value);
    this.updateMonthsForYear(selectedYear);
    
    if (this.month && this.month.nativeElement) {
      this.month.nativeElement.value = '';
    }
  }

  calculateTotalFootprint(footprint: any): number {
    if (!footprint) return 0;
    return Number((Number(footprint.transportation) + Number(footprint.electricity) + 
           Number(footprint.lpg) + Number(footprint.shipping) + 
           Number(footprint.airConditioner)).toFixed(2));
  }

  openUpdateForm(footprint: any) {
    this.selectedFootprint = {...footprint};
    this.showUpdateForm = true;
  }

  saveUpdate() {
    if (!this.selectedFootprint) return;
    this.loading = true;

    const calculatedEmissions = this.calculateFootprint(this.selectedFootprint);
    const updatedFootprint = {
      ...this.selectedFootprint,
      transportation: calculatedEmissions.transportation,
      electricity: calculatedEmissions.electricity,
      lpg: calculatedEmissions.lpg,
      shipping: calculatedEmissions.shipping,
      airConditioner: calculatedEmissions.airConditioner
    };
    
    this.userService.updateCarbonFootprint(updatedFootprint).subscribe({
      next: () => {
        this.loadCarbonFootprints();
        this.showUpdateForm = false;
        this.selectedFootprint = null;
        this.showToastMessage('Carbon footprint updated successfully', 'success');
        this.loading = false;
      },
      error: (error) => {
        console.error('Error updating footprint:', error);
        this.showToastMessage('Error updating carbon footprint', 'error');
        this.loading = false;
      }
    });
  }

  deleteFootprint(month: string, year: number) {
    if (confirm('Are you sure you want to delete this footprint record?')) {
      this.loading = true;
      this.userService.deleteCarbonFootprintByMonthAndYear(month, year).subscribe({
        next: () => {
          this.loadCarbonFootprints();
          this.showToastMessage('Carbon footprint deleted successfully', 'success');
          this.loading = false;
        },
        error: (error) => {
          console.error('Error deleting footprint:', error);
          this.showToastMessage('Error deleting carbon footprint', 'error');
          this.loading = false;
        }
      });
    }
  }

  updateChart() {
    const ctx = document.getElementById('footprintChart') as HTMLCanvasElement;
    if (!ctx) {
      console.error('Canvas element not found');
      return;
    }
    
    if (this.chart) {
      this.chart.destroy();
    }

    const reversedFootprints = [...this.carbonFootprints].reverse();
    const labels = reversedFootprints.map(f => `${f.footprintMonth} ${f.footprintYear}`);
    const datasets = [
      {
        label: 'Transportation',
        data: reversedFootprints.map(f => f.transportation),
        backgroundColor: 'rgba(255, 99, 132, 0.7)',
        borderColor: 'rgba(255, 99, 132, 1)',
        borderWidth: 2,
        borderRadius: 5,
        borderSkipped: false
      },
      {
        label: 'Electricity',
        data: reversedFootprints.map(f => f.electricity),
        backgroundColor: 'rgba(54, 162, 235, 0.7)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 2,
        borderRadius: 5,
        borderSkipped: false
      },
      {
        label: 'LPG',
        data: reversedFootprints.map(f => f.lpg),
        backgroundColor: 'rgba(255, 206, 86, 0.7)',
        borderColor: 'rgba(255, 206, 86, 1)',
        borderWidth: 2,
        borderRadius: 5,
        borderSkipped: false
      },
      {
        label: 'Shipping',
        data: reversedFootprints.map(f => f.shipping),
        backgroundColor: 'rgba(75, 192, 192, 0.7)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 2,
        borderRadius: 5,
        borderSkipped: false
      },
      {
        label: 'Air Conditioner',
        data: reversedFootprints.map(f => f.airConditioner),
        backgroundColor: 'rgba(153, 102, 255, 0.7)',
        borderColor: 'rgba(153, 102, 255, 1)',
        borderWidth: 2,
        borderRadius: 5,
        borderSkipped: false
      }
    ];

    this.chart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: labels,
        datasets: datasets
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: {
            beginAtZero: true,
            stacked: true,
            title: {
              display: true,
              text: 'CO₂ Emissions (kg)',
              font: {
                size: 14,
                weight: 'bold'
              }
            },
            grid: {
              display: false
            }
          },
          x: {
            stacked: true,
            title: {
              display: true,
              text: 'Month',
              font: {
                size: 14,
                weight: 'bold'
              }
            },
            grid: {
              display: false
            }
          }
        },
        plugins: {
          legend: {
            display: true,
            position: 'top',
            labels: {
              font: {
                size: 12,
                weight: 'bold'
              }
            }
          },
          tooltip: {
            callbacks: {
              label: function(context: any) {
                const label = context.dataset.label || '';
                const value = context.parsed.y;
                return `${label}: ${value.toFixed(2)} kg CO₂`;
              }
            }
          }
        }
      }
    });
  }
}
