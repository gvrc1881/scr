export interface ProjectPhaseModel{
    id: number;    
    workId:number;
    phaseName: String;
    description: String;
    sequence:number;
    dependencyToStart:String;
    weightage: number;
    status:String;
    plannedStartDate:Date;
    targetCompletionDate:Date;
    expectedCompletion:Date;
    commenceDate:Date;
    completionDate:Date;
    
    createdBy: String;
    createdOn: Date;
    modifiedBy: number;
    modifiedOn: String;
}