export interface SectorModel {
    sno: number;
    id: number;
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