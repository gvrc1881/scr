export interface DailySummaryModel {
    createdDate: Date;
    facilityId: string;
    nameOfStaff: string;
    dayProgress: string;
    npbProgress: string;
    psiProgress: string;
    tomorrowForecast: string;
    footPatrolling:string;
    footInspection:string;
    footPlateInspection:string;
    supervisor:string;
    staffStrength:string;
    powerBlock:string;
    nonPowerBlock:string;
    remarks: string;
}