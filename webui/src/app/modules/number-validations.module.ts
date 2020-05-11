import {NgModule} from '@angular/core';
import { NumberDirective } from '../common/number-validations';
@NgModule({
declarations:[NumberDirective],
exports:[NumberDirective]
})
export class NumberValidationsModule
{
}