import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AssetMasterDataComponent } from './asset-master-data.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';

const routes: Routes = [
    {
        path:'',
        component: AssetMasterDataComponent,
    }
]

@NgModule({
    declarations: [
        AssetMasterDataComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        CommonDirectivesModule,        
        DecimalValidationsModule,
        
    ],
    providers: [
       
   ],
    exports: [
        AssetMasterDataComponent
    ]
})
export class AssetMasterDataModule{

}