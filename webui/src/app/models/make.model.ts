import { Timestamp } from 'rxjs/internal/operators/timestamp';

export interface MakeModel{
    brandName: string ,
    createdBy: string, 
    description:string,
    makeCode :string,
    makeName :string,
    makeType :string,
    createdOn :Date,
  createdStamp: Date,
  createdTxStamp :Date, 
  lastUpdatedStamp :Date,
  lastUpdatedTxStamp: Date
      
  }