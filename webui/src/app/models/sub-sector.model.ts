export interface SubSectorModel {
    sno: number;
    id: number;
    sector:string;
    subSectorCode:string;
    facilityId: string;
    sectorCode: string;
    fromLocation:string;
    fromLocationType: string;
    toLocation:string;
    toLocationType:string;
    division:string;
    line1:string;
    line2:string;
    createdStamp: Date;
    createdTxStamp:Date;
    lastUpdatedStamp: number;
    lastUpdatedTxStamp:number;
}