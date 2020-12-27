export interface AssistanceModel {
    sno: number;
    id: number;
    workId: any;
    workGroupId: any;
    typeOfAssistance:string;
    assistance: string;
    requestedBy:string;
    requestedDate:Date;
    requestTo:string;
    responseBy:string;
    responseDate:Date;
    response: string;
    remark:string;
    status: string;
    attachment: String;

}