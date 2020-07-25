import {NgModule} from '@angular/core';
import { filterDirective } from '../common/filter.directive';
@NgModule({
declarations:[filterDirective],
exports:[filterDirective]
})
export class FilterModule
{
}