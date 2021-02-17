import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ProductAssociationComponent } from './product-association.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddProductAssociationComponent } from './add-product-association/add-product-association.component';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

const routes: Routes = [
    {
        path: '',
        component: ProductAssociationComponent,
    },   
    {
        path     : ':id',
        component: AddProductAssociationComponent
    },
    {
        path        : 'add-product-association',
        component:   AddProductAssociationComponent
    },
];

@NgModule({
    declarations: [
        ProductAssociationComponent,
        AddProductAssociationComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        CommonDirectivesModule
    ],
    providers: [
        
    ],
    exports:[
        ProductAssociationComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class ProductAssociationModule {

}