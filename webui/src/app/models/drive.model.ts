export interface DriveModel {
    sno: number;
    id: number;
    name: string,
    description: string;
    fromDate: string;
    toDate: string;
    depoType: number;
    assetType: string;
    assetDescription: string;
    criteria: string;
    targetQuantity: number;
    isIdRequired: string;
    functionalUnit: string;
    checkList: string;
    active: string;

    createdBy: number;
    createdDate: string;

    modifiedBy: number;
    modifiedDate: String;
}

export interface DriveChecklistModel {
    sno: number;
    id: number;
    drive: string;
    measureActivityList: string;
    displayOrder: number;
    lowerLimit: number;
    upperLimit: number
    active: string;

    createdBy: number;
    createdDate: string;
    modifiedBy: number;
    modifiedDate: String;
}

export interface DriveTargetModel{
    unitType: string;
	unitName: string;
	target:number;
	poulation: string;
    driveId: string;
    
    createdBy: number;
    createdDate: string;
    modifiedBy: number;
    modifiedDate: String;
}

export interface DriveDailyProgressModel{

    activityId:number;
	performedDate:Date;
	division: string;
	depot: string;
	section: string;
	performedCount:number;
	supervisor: string;

    createdBy: number;
    createdDate: string;
    modifiedBy: number;
    modifiedDate: String;
}

export interface FailureAnalysisModel{
    failure_id: string;
	reported: string;
	repurcussion: string;
	date:Date;
	div:number;
	failureSection:number;
	assetType:number;
	assetId:number;
	subAssetType: string;
	subAssetId: string;
	make:number;
	model:number;
	rootCause: string;
	actionPlan: string;
	actionStatus:number;
	approvedBy: string;
	actionTargetDate:Date;
	actionCompletedDate:Date;
	actionDescription: string;

    createdBy: number;
    createdDate: string;
    modifiedBy: number;
    modifiedDate: String;
}

export interface StipulationstModel {
    id: number;
    stipulation: String;
    stipulationTo: String;
    dateOfStipulation: Date;
    dateComplied: Date;
    compliance: String;
    attachment: String;
    compliedBy: String;
    credatedBy: String;
    CreatedOn: Date;
    updatedBy: String;
    updatedOn: Date;
    assetType: String;
}

export interface InspectionstModel {
    id: number;
    inspectionType: String;
    section: String;
    sectionStartLocation: String;
    sectionEndLocation: String;
    dateOfInspection: Date;
    RKM: number;
    TKM: number;
    remarks: String;
    authorisationDate: Date;
    chargingDate: Date;
    attachment: String;
    station: String;
    credatedBy: String;
    createdOn: Date;
    updatedBy: String;
    updatedOn: Date;
    stipulationsId: String;
}

export interface ElectrificationTargetstModel {
    id: number;
    section: String;
    guage: String;
    targetDate:Date;
    status: String;
    division: String;
    executionAgency: String;
    TKM: number;
    RKM: number;
    crsInspection: String;
    crsAuthorisation: String;
    targetSetBy: String;
    doublingTrippling: String;
    state: String;
    phase: String;
    proposalScheme: String;
    sanctionByBoard: String;
    yearOfSanction: String;
    dateOfCompletion: Date;
    createdBy: String;
    createdOn: Date;
    updatedBy: String;
    updatedOn: Date;
}