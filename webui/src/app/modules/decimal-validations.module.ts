
import {NgModule} from '@angular/core';
import { DecimaNumberDirective } from '../common/decimal-validations';
@NgModule({
declarations:[DecimaNumberDirective],
exports:[DecimaNumberDirective]
})
export class DecimalValidationsModule
{
}