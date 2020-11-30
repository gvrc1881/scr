import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { GroupsSectionsComponent } from './groups-sections.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddGroupsSectionsComponent } from './add-groups-sections/add-groups-sections.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
import { MilestoneTargetsComponent } from 'src/app/components/groups-sections/milestone-targets/milestone-targets.component';

const routes: Routes = [
    {
        path: '',
        component: GroupsSectionsComponent,
    },   
    {
        path     : ':id',
        component: AddGroupsSectionsComponent
    },
    {
        path        : 'add-groups-sections',
        component:   AddGroupsSectionsComponent
    },
];

@NgModule({
    declarations: [
        GroupsSectionsComponent,
        AddGroupsSectionsComponent,
        MilestoneTargetsComponent
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
        DecimalValidationsModule,
        
    ],
    providers: [
        
    ],
    exports:[
        GroupsSectionsComponent
    ],
    entryComponents: [ MilestoneTargetsComponent ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class GroupsSectionsModule {

}