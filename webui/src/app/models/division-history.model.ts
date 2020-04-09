export interface DivisionHistoryModel {   
    id:number;
    jobsHistoryId:number;
    divisionId:number;
    tableName :string;
    updatedDate:Date;
    updatedRecords:number;
    validateMatch:number;
    status:string;         
}