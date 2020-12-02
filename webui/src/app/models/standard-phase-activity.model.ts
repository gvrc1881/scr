export interface StandardPhaseActivityModel{
    id: number;    
    standardPhaseId: number;
    name: String;
    description: String;
    dependencyToStart:number;
    uom:String;
    isCheckList:String;
    isObjectIdRequired:String;
    depotType:Date;
    assetType:Date;
    
}