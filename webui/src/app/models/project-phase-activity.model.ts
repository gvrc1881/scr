export interface ProjectPhaseActivityModel{
    id: number;    
    workPhaseId:number;
    name:String;
    assetType: String;
    description: String;
    sequence:number;
    dependencyToStart:String;
    depotType: number;
    isCheckList:String;
    isObjectIdRequired:String;
    uom:String;
    plannedStartDate:Date;
    targetCompletionDate:Date;    
    commenceDate:Date;
    completionDate:Date;
    
    createdBy: String;
    createdOn: Date;
    updatedBy: String;
    updateOn: Date;
 


 
 

 

  
}