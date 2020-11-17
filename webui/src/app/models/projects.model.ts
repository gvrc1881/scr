export interface ProjectModel{
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
  commencementDate :Date,
  endKm :number,
  expectedCompletion:Date,
  lineType :string,
  loaDate :Date,
  loaNo:string,
  startKm :number,
  tenderValue :number,
    
    createdBy: String;
    createdOn: Date;
    modifiedBy: number;
    modifiedOn: String;
}