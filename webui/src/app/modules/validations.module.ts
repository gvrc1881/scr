import {NgModule} from '@angular/core';
import { NumberDirective } from '../common/validations';
@NgModule({
declarations:[NumberDirective],
exports:[NumberDirective]
})
export class ValidationsModule
{
}