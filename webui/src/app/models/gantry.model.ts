export interface GantryModel{
    sno: number;
    id: number;
    facilityId:String;
    gantryCode:String;
    elementarySections:String;
    protectionTraverseCrossover: String;
    protectionTraverseTurnout:String;
    protectionLongitudnalUp:String;
    protectionLongitudnalDn:String;

    
    normallyOpen: String;
    tpcBoard:String;
    remarks:String;
    
   
    
    createdStamp: Date;
    createdTxStamp: Date;
    lastUpdatedStamp: Date;
    lastUpdatedTxStamp: Date;

}