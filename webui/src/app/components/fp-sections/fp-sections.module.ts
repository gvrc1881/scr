import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FpSectionsComponent } from './fp-sections.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddFpSectionsComponent } from './add-fp-sections/add-fp-sections.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';


const routes: Routes = [
    {
        path: '',
        component: FpSectionsComponent,
    },   
    {
        path     : ':id',
        component: AddFpSectionsComponent
    },
    {
        path        : 'add-fp-sections',
        component:   AddFpSectionsComponent
    },
];

@NgModule({
    declarations: [
        FpSectionsComponent,
        AddFpSectionsComponent,
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
    ],
    providers: [
        
    ],
    exports:[
        FpSectionsComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class FpSectionsModule {

}