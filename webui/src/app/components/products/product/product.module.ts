import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ProductComponent } from './product.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime'; 
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { AddProductComponent } from './add-product/add-product.component';

const routes: Routes = [
    {
        path: '',
        component: ProductComponent,
    },
    {
        path     : ':id',
        component: AddProductComponent,
    },
    {
    	path: 'add-product',
    	component: AddProductComponent,
    },
    
    
];


@NgModule({
    declarations: [
        ProductComponent,
        AddProductComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        DecimalValidationsModule,
        NumberValidationsModule,
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
    ],
    providers: [
        
    ],
    exports: [
    	ProductComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProductModule {

}








