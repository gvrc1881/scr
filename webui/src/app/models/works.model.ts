export interface WorksModel{
  id: number;
  allocation: string;
  division: string;
  estdLatestAnticCost: string;
  executedBy: string;
  executingAgency: string;
  financialProgressPercentage: string;
  latestRevisedCost: number;
  pbLawLswp: string;
  pbLawLswpCode: string;
  physicalProgressPercentage: string;
  presentStatus: string;
  reWorks: string;
  rkm: number;
  sanctionCost: number;
  section: string;
  statusRemarks: string;
  targetDateOfCompletion: Date;
  tkm: number;
  workName: string;
  yearOfSanction: number;
}
export interface standardPhaseActivityModel{
    id: number;
    assetType: string;
    depotType: string;
    isCheckList: string;
    isObjectIdRequired: string;
    name: string;
    uom: string;
    standardPhaseId: any;    
}

export interface WPADailyProgressModel {
    
    id: number;
    performedCount: number;
    workGroupId: any;
    workPhaseActivity: any;
    date: any;
    alreadyDoneCount: number;
        
}

export interface WPASectionPopulationModel {
    id: number;
    population: number;
    workGroupId: any;
    workPhaseActivityId: any; 
}

export interface WPASectionTargetsModel {
    
    id: number;
    workPhaseActivityId: any;
    workGroupId: any;
    yearType: string;
    year: number;
    apr: number;
    may: number;
    jun: number;
    jul: number;
    aug: number;
    sep: number;
    oct: number;
    nov: number;
    dec: number;
    jan: number;
    feb: number;
    mar: number;
    population: number;
    
}