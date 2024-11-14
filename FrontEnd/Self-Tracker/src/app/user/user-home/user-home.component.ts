import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {
  currentMonth: number;
  currentYear: number;

  constructor() {
    const today = new Date();
    this.currentMonth = today.getMonth() + 1; // getMonth() returns 0-11
    this.currentYear = today.getFullYear();
  }


  ngOnInit(): void {
  }
}
