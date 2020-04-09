export interface SchedulerTrackingModel {   
    id:number;
    endTime:string;
    jobId:number;
    jobTName :string;
    divisionCode:string;
    jobStatus:string;
    processedDate:Date;
    startTime:string;
    totalTablesCount:number;
    successTablesCount:number;
    failedTablesCount:number;
    timeInterval:string;
    runType:string;      
}