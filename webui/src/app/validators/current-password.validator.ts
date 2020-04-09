import { FormControl } from '@angular/forms';

export class CurrentPasswordValidator{
    static validCurrentPassword(fc: FormControl){
        console.log("fc.value "+fc.value);
        if(fc.value.toLowerCase() === "abc123" || fc.value.toLowerCase() === "123abc"){
          return {
            validCurrentPassword: true
          };
        } else {
          return null;
        }
      }
}