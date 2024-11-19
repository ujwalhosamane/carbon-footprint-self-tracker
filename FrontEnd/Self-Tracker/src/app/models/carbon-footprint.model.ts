export class CarbonFootprint {
    footprintMonth: string;
    footprintYear: number;
    transportation: number;
    electricity: number;
    lpg: number;
    shipping: number;
    airConditioner: number;
  
    constructor(
      footprintMonth: string,
      footprintYear: number,
      transportation: number,
      electricity: number,
      lpg: number,
      shipping: number,
      airConditioner: number
    ) {
      this.footprintMonth = footprintMonth;
      this.footprintYear = footprintYear;
      this.transportation = transportation;
      this.electricity = electricity;
      this.lpg = lpg;
      this.shipping = shipping;
      this.airConditioner = airConditioner;
    }
  }