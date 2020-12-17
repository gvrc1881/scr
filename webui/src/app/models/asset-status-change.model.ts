export interface AssetStatusChangeModel{
    assetType: string ,
    assetId: string, 
    currentStatus:string,
    remarks :string,
    facilityId :string,
    status :string,
    createdOn :Date,
  createdStamp: Date,
  createdTxStamp :Date, 
  lastUpdatedStamp :Date,
  lastUpdatedTxStamp: Date
      
  }