import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { filterDirective } from '../common/filter.directive';
import { InputDisabledDirective } from '../common/input-disabled.directive';


@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [
        filterDirective,
        InputDisabledDirective
    ],
    exports: [
        filterDirective,
        InputDisabledDirective
    ]
})
export class CommonDirectivesModule { }
