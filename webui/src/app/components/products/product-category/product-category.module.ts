import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ProductCategoryComponent } from './product-category.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddProductCategoryComponent } from './add-product-category/add-product-category.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
import { filterDirective } from 'src/app/common/filter.directive';

const routes: Routes = [
    {
        path: '',
        component: ProductCategoryComponent,
    },   
    {
        path     : ':id',
        component: AddProductCategoryComponent
    },
    {
        path        : 'add-product-category',
        component:   AddProductCategoryComponent
    },
];

@NgModule({
    declarations: [
        ProductCategoryComponent,
        AddProductCategoryComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        NumberValidationsModule,
        CommonDirectivesModule,
        DecimalValidationsModule
    ],
    providers: [
        
    ],
    exports:[
        ProductCategoryComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class ProductCategoryModule {

}