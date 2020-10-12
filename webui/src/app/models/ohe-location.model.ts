export interface OheLocationModel{
    sno: number;
    id: number;
    division:String;
    section:String;
    pwi:String;
    trackLine: String;
    oheMast:String;
    structureType:String;
    engFeature:String;
    oheFeature:String;
    longitude:String;
    latitude: String;
    altitude:String;
    date:Date;
    validity:String;
    satellites:String;
    speed:String;
    heading:String;
    remarkOne:String;
    remarkTwo:String;

    oheSequence:String;
    curvature:String;
    curvatureRemark:String;
    chainage:String;
    chainageRemark:String;

    
    createdStamp: Date;
    createdTxStamp: Date;
    lastUpdatedStamp: Date;
    lastUpdatedTxStamp: Date;

}