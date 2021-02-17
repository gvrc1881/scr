export interface ProductAssociationModel {
    id: number;
    productId:any;
    productIdTo:any;
    productAssocTypeId: any;
    reason: string;
    fromDate:Date;
    thruDate:Date;
    quantity:string;
    scrapFactor:any;
}