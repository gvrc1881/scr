import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { TpcBoardsComponent } from './tpc-boards.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddTpcBoardsComponent } from './add-tpc-board/add-tpc-boards.component';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

const routes: Routes = [
    {
        path: '',
        component: TpcBoardsComponent,
    },   
    {
        path     : ':id',
        component: AddTpcBoardsComponent
    },
    {
        path        : 'add-tpc-boards',
        component:   AddTpcBoardsComponent
    },
];

@NgModule({
    declarations: [
        TpcBoardsComponent,
        AddTpcBoardsComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule, 
        CommonDirectivesModule,     
        Ng4LoadingSpinnerModule.forRoot(),
    ],
    providers: [
        
    ],
    exports:[
        TpcBoardsComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class TpcBoardsModule {

}