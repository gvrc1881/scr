export interface PowerBlockModel {
    id: number;
    betweenTrains: string;
	createdBy: string;
	createdDate: string;
	createdOn: string;
	createdStamp: string;
	createdTxStamp: string;
	currentStatus: string;
	dataDiv: string;
	elementarySectionCode: string;
	equipmentToWork: string;
	facilityId: string;
	fieldNoPtwIssue: string;
	fieldNoPtwReturn: string;
	fromLocation: string;
	fromTime: string;
	grantPeriod: string;
	kmOfWork: string;
	lastUpdatedStamp: string;
	lastUpdatedTxStamp: string;
	line: string;
	line2: string;
	messageTime: string;
	pbGrantedFromDateTime: string;
	pbGrantedThruDateTime: string;
	pbOperationSeqId: string;
	pbRequestedFromDateTime: string;
	pbRequestedThruDateTime: string;
	post: string;
	powerBlockSection: string;
	ptwAvailedFromDateTime: string;
	ptwAvailedThruDateTime: string;
	ptwDetailsByManual: string;
	purpose: string;
	remarks: string;
	reqDepartment: string;
	reqPeriod: string;
	reqnBy: string;
	schedule: string;
	section: string;
	sectionController: string;
	shadowBlock: string;
	specialRemarks: string;
	staffToWork: string;
	supervisorIncharge: string;
	switchingEquipment: string;
	switchingStation: string;
	tkm: string;
	toLocation: string;
	toTime: string;
	tpcBoard: string;
	tpcNoPtwIssue: string;
	tpcNoPtwReturn: string;
	typeOfOperation: string;
}

export interface SwitchMaintenenceHistory {
	id: number;
	closeDoneTimeLapse: string;
	closeTimeLapse: string;
	fieldNoIoClose: string;
	fieldNoIoCloseDone: string;
	fieldNoIoOpen: string;
	fieldNoIoOpenDone: string;
	ioClosedBy: string;
	ioClosedDateTime: string;
	ioClosedDateTimeDone: string;
	ioLocation: string;
	ioOpenedBy: string;
	ioOpenedDateTime: string;
	ioOpenedDateTimeDone: string;
	ioType: string;
	openDoneTimeLapse: string;
	openTimeLapse: string;
	pbOperationSeqId: string;
	tpcNoIoClose: string;
	tpcNoIoCloseDone: string;
	tpcNoIoOpen: string;
	tpcNoIoOpenDone: string;
}

export interface PbSwitchControl {

	createdStamp: string;
	createdTxStamp: string;
	dataDiv: string;
	isNormallyOpened: string;
	lastUpdatedStamp: string;
	lastUpdatedTxStamp: string;
	line: string;
	pbExtentCode: string;
	pbExtentType: string;
	seqId: string;
	switchId: string;
	switchType: string;
	upDn: string;

}

export interface SwitchMaintenenceHistory {

	closeDoneTimeLapse: string;
	closeTimeLapse: string;
	createdStamp: string;
	createdTxStamp: string;
	dataDiv: string;
	deleteStatus: string;
	fieldNoIoClose: string;
	fieldNoIoCloseDone: string;
	fieldNoIoOpen: string;
	fieldNoIoOpenDone: string;
	ioClosedBy: string;
	ioClosedDateTime: string;
	ioClosedDateTimeDone: string;
	ioLocation: string;
	ioOpenedBy: string;
	ioOpenedDateTime: string;
	ioOpenedDateTimeDone: string;
	ioType: string;
	isFieldOperated: string;
	lastUpdatedStamp: string;
	lastUpdatedTxStamp: string;
	openDoneTimeLapse: string;
	openTimeLapse: string;
	pbOperationSeqId: string;
	seqId: string;
	tpcNoIoClose: string;
	tpcNoIoCloseDone: string;
	tpcNoIoOpen: string;
	tpcNoIoOpenDone: string;
	
}