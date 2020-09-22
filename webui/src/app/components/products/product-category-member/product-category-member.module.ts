import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ProductCategoryMemberComponent } from './product-category-member.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddProductCategoryMemberComponent } from './add-product-category-member/add-product-category-member.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
const routes: Routes = [
    {
        path: '',
        component: ProductCategoryMemberComponent,
    },   
    {
        path     : ':id',
        component: AddProductCategoryMemberComponent
    },
    {
        path        : 'add-product-category-member',
        component:   AddProductCategoryMemberComponent
    },
];

@NgModule({
    declarations: [
        ProductCategoryMemberComponent,
        AddProductCategoryMemberComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        NumberValidationsModule,
        DecimalValidationsModule
    ],
    providers: [
        
    ],
    exports:[
        ProductCategoryMemberComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class ProductCategoryMemberModule {

}