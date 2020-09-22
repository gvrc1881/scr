export interface ProductModel{
    sno: number;
    id: number;
    facilityId:String ;
    depthUomId:String;
    diameterUomId: String;
    heightUomId:String;
    materialClassification:String;
    plNo:String;
    primaryProductCategoryId:String;
    productCodeTypeId:String;
    productTypeId:String;
    productId:String;
    productName:String;
    productDepth:String;
    productDiameter:String;
    productHeight:String;
    productWeight:String;
    productWidth:String;
    quantityIncluded:String;
    quantityUomId:String;
    rlyId:String;
    description: String;
    createdByUserLogin: String;
    createdDate: Date;
    modifiedBy: number;
    modifiedDate: String;
}
export interface ProductCategoryModel {
    sno: number;
    id: number;
    categoryName: string;
    primaryParentCategoryId: string;
    productCategoryId:string;
    productCategoryTypeId: string;
    description: string;

    createdStamp: Date;
    lastUpdatedStamp: number;
}

export interface ProductCategoryMemberModel {
    sno: number;
    id: number;
    productCategoryId: string;
    productId: string;
    quantity:string;
    fromDate: Date;
    thruDate: Date;
    comments:string;
    createdStamp: Date;
    lastUpdatedStamp: number;
}