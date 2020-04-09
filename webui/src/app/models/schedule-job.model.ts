import { RepositoryModel } from './repository.model';
import { TimeIntervalModel } from './time-interval.model';
import { JobTypeModel } from './job-type.model';

export interface ScheduleJobModel {   
    jobId:number;
    jobType :JobTypeModel;
    repository:RepositoryModel;
    timeInterval:TimeIntervalModel;
    jobStatus:string;
    createdBy:number;
    createdDate:Date;
    isActive:number;
    modifiedBy:number;
    modifiedDate:Date;
    jobTypeName:string;     
}
