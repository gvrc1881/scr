export interface PowerBlockModel {
    id: number;
    betweenTrains: string;
    createdBy: number;
    createdDate: string;
    modifiedBy: number;
    modifiedDate: String;
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