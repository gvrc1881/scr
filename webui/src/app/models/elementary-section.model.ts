export interface ElementarySectionModel {
    sno: number;
    id: number;
    elementarySectionCode:string;
    facilityId: string;
    stationCode:string;
    trackCode:string;
    sidingMain:string;
    sectionCode:string;
    sectorCode:string;
    subSectorCode:string;
    fromKm:string;
    fromSeq: string;
    toKm:string;
    toSeq:string;
    devisionId:string;
    protectionCrossover:string;
    protectionTurnout:string;
    longitudinal:string;
    remarksShunting:string;
    remarksNo:string;
    isAutoDead:string;
    createdStamp: Date;
    createdTxStamp:Date;
    lastUpdatedStamp: number;
    lastUpdatedTxStamp:number;
}