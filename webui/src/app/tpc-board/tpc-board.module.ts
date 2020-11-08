import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TpcBoardComponent } from 'src/app/tpc-board/tpc-board.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';

const routes: Routes = [
    {
        path:'',
        component: TpcBoardComponent,
    }
]

@NgModule({
    declarations: [
        TpcBoardComponent
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
        TpcBoardComponent
    ]
})
export class TPCBoardModule{

}