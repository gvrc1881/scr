import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TPCBoardDepotAssocComponent } from './tpc-board-depot-assoc.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';

const routes: Routes = [
    {
        path:'',
        component: TPCBoardDepotAssocComponent,
    }
]

@NgModule({
    declarations: [
        TPCBoardDepotAssocComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule
        
    ],
    providers: [
        
    ],
    exports: [
        TPCBoardDepotAssocComponent

    ]
})
export class TPCBoardDepotAssocModule{

}