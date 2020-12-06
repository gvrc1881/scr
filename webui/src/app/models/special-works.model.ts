export interface SpecialWorksModel {
    sno: number;
    id: number;
    count: number;
    facilityId: string;
    fromDateTime:Date;
    thruDateTime: Date;
    location:string;
    precautionaryMeasure:string;
    doneBy:string;
    remarks:string;
    createdDate:Date;
    createdStamp: Date;
    createdTxStamp:Date;
    lastUpdatedStamp: number;
    lastUpdatedTxStamp:number;
}