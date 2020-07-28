import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { filterDirective } from '../common/filter.directive';


@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [
        filterDirective
    ],
    exports: [
        filterDirective
    ]
})
export class CommonDirectivesModule { }
