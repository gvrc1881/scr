export interface ProductModel{
    sno: number;
    id: number;
    productId:String;
    rlyId:String;
    plNo:String;
    description: String;
    quantityUomId:String;
    materialClassification:String;
    productTypeId:String;
    primaryProductCategoryId:String;

    /*depthUomId:String;
    diameterUomId: String;
    heightUomId:String;
    productCodeTypeId:String;
    
    productName:String;
    productDepth:String;
    productDiameter:String;
    productHeight:String;
    productWeight:String;
    productWidth:String;
    quantityIncluded:String;*/
    
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