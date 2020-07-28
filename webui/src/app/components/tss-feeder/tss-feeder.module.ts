import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TssFeederComponent } from './tss-feeder.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

const routes: Routes = [
    {
        path:'',
        component: TssFeederComponent,
    }
]

@NgModule({
    declarations: [
        TssFeederComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule
        
    ],
    providers: [
       
   ],
    exports: [
        TssFeederComponent
    ]
})
export class TssFeederModule{

}
 
    
    
   

    

    


  
    
 