import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { filterDirective } from '../common/filter.directive';
import { InputDisabledDirective } from '../common/input-disabled.directive';
import { DateFilterPipe } from '../common/date-filter.pipe';


@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [
        filterDirective,
        InputDisabledDirective,
        DateFilterPipe,
    ],
    exports: [
        filterDirective,
        InputDisabledDirective,
        DateFilterPipe,
    ]
})
export class CommonDirectivesModule { }
