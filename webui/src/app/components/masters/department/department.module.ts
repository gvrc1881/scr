import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DepartmentComponent } from './department.component';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { MatCheckboxModule } from '@angular/material';
import { MaterialModule } from 'src/app/modules/material.modules';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { DepartmentService } from 'src/app/services/department.service';

const routes: Routes = [
    {
        path     : '',
        component: DepartmentComponent
    }   
   
];

@NgModule({
    imports     : [
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        MaterialModule,
        FormsModule,
        ReactiveFormsModule,
        MatCheckboxModule
    ],
    declarations: [
        DepartmentComponent      
       
    ],
    providers: [        
        DepartmentService
    ],
    exports     : [
        DepartmentComponent
    ]
})
export class DepartmentModule{

}
