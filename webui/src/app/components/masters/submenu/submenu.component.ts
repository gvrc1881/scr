import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatDialogRef } from '@angular/material';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { SubMenuService } from '../../..//services/sub-menu.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { FuseConfirmDialogComponent } from '../../../components/confirm-dialog/confirm-dialog.component';
import { CommonService } from '../../../common/common.service';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'app-sub-menu',
  templateUrl: './submenu.component.html',
  styleUrls: ['./submenu.component.scss']
})
export class SubMenuComponent implements OnInit {
  subMenuForm: FormGroup;
  formErrors: any;
  subMenuList: any;
  rolepermissions: any;
  menuList: any;
  checkInput: boolean = false;
  subForm: boolean;
  id: number = 0;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  title: string = "Save";
  responseStatus: Object = [];
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'MenuName', 'SubMenu', 'SubMenuURL', 'id'];
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  dataSource: MatTableDataSource<SubMenuModel>;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild('filter', {static: true}) filter: ElementRef;
  response:any;
  users: any =[];
  data: any ;

  constructor(private formBuilder: FormBuilder, private _subMenuService: SubMenuService,
    private _router: Router,
    private actRoute: ActivatedRoute,
    public dialog: MatDialog,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService
  ) {
    this.formErrors = {
      menu: {},
      submenu: {},
      submenuURL: {},
    };
  }

  ngOnInit() {
    this.spinnerService.show();
    this.subForm = false;
    this._subMenuService.getMainMenu().subscribe((data) => {
      this.menuList = data;
    })
    this.subMenuForm = this.formBuilder.group({
      id: 0,
      'submenu': [null, Validators.compose([Validators.required, Validators.pattern(this.pattern)]), this.duplicateSubMenu.bind(this)],
      'submenuURL': [null, Validators.pattern(this.pattern)],
      'menu': ['', Validators.required]
    });
    this.subMenuForm.valueChanges.subscribe(() => {
      this.onFormValuesChanged();
    });
    this.getSubMenuList();
  }
  processSubMenuEdit(id) {
    this._subMenuService.getSubMenuById(id)
      .subscribe(resp => {        
        this.response = resp;
        this.subMenuForm.patchValue({
          id: this.response.id,
          submenu: this.response.submenu,
          submenuURL: this.response.submenuURL,
          menu: this.response.menuId,
        });
      }, error => this.formErrors = error);
    this.commonService.scrollTop("forms");
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  private getSubMenuList() {
    this.subForm = false;    
    const submenu: SubMenuModel[] = [];
    this._subMenuService.getSubMenu().subscribe((data) => {      
      this.subMenuList = data;
      this.rolepermissions = (data["#result-set-1"]);      
      for (let i = 0; i < this.rolepermissions.length; i++) {
        this.rolepermissions[i].sno = i + 1;
        submenu.push(this.rolepermissions[i]);
      }
      this.dataSource = new MatTableDataSource(submenu);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.formErrors = error;
      this.spinnerService.hide();
    }

    );
  }
  onFormValuesChanged() {
    for (const field in this.formErrors) {
      if (!this.formErrors.hasOwnProperty(field)) {
        continue;
      }
      // Clear previous errors
      this.formErrors[field] = {};
      // Get the control
      const control = this.subMenuForm.get(field);
      if (control && control.dirty && !control.valid) {
        this.formErrors[field] = control.errors;
      }
    }
  }
  AddSubMenu() {
    this.subForm = true;
    this.checkInput = false;
  }
  onSubMenuFormSubmit() {   
    let var_id: string = this.subMenuForm.value.id;
    let var_menu: string = this.subMenuForm.value.menu;
    let var_submenu: string = this.subMenuForm.value.submenu;
    let var_submenuURL: string = this.subMenuForm.value.submenuURL;
    if (!this.subMenuForm.valid) {
      return;
  }
    if (this.title == "Save") {
      this.spinnerService.show();      
      this.checkInput = false;
      this._subMenuService.saveSubMenu({
        'menuId': var_menu,
        'MenuId': var_menu,
        'Menu': var_menu,
        'Orders': 0,
        'StatusId': 1,
        'submenu': var_submenu,
        'submenuIcon': 1,
        'submenuURL': var_submenuURL,
        'createdBy': this.userdata['one'].ID,
        'modifiedBy': this.userdata['one'].ID,
      }).subscribe((data) => {             
        this.commonService.showAlertMessage('SubMenu Added Successfully.'); 
        this.getSubMenuList();
      }, error => error => {
        this.formErrors = error
        this.commonService.showAlertMessage(this.formErrors);
        this.commonService.scrollTop("forms");
        this.spinnerService.hide();
      });

    }
    else if (this.title == "Update") {
      this.spinnerService.show();
      this._subMenuService.updateSubMenu({
        'MenuName': var_menu,
        'menuId': var_menu,
        'orders': 0,
        'statusId': 1,
        'submenu': this.commonService.removeSpaceMaoreThanOne(var_submenu),
        'submenuURL': this.commonService.removeSpaceMaoreThanOne(var_submenuURL),
        'submenuIcon': 1,
        'createdBy': this.userdata['one'].ID,
        'modifiedBy': this.userdata['one'].ID,
        'createdDate': null,
        'modifiedDate': null,
        'id': var_id,
      }, var_id)
        .subscribe((data) => {
          this.title = "Save";         
          this.commonService.showAlertMessage('SubMenu Updated Successfully.');
          this.commonService.scrollTop("forms");
          this.getSubMenuList();
          this.subMenuForm.reset();
        }, error => {
          this.formErrors = error
          this.commonService.showAlertMessage(this.formErrors);
          this.commonService.scrollTop("forms");
          this.spinnerService.hide();
        });
    }

  }

  updateSubmenu(id) {
    this.spinnerService.show();
    this.subForm = true;    
    this.checkInput = true;    
    this.processSubMenuEdit(id);
    this.title = "Update";
    this.spinnerService.hide();    
  }
  deletesubmenuList(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete the selected user?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this._subMenuService.deleteSubmenu(id)
          .subscribe((data) => {          
            this.commonService.showAlertMessage('SubMenu Deleted Successfully.'); 
            this.commonService.scrollTop("forms");
            this.getSubMenuList();            
          });
      }
      this.confirmDialogRef = null;
    }, error => {
      this.formErrors = error
      this.commonService.showAlertMessage(this.formErrors);
      this.commonService.scrollTop("forms");
      this.spinnerService.hide();
    });   
    this.spinnerService.hide();
  }
  onGoBack() {
    this.subMenuForm.reset();
    this.subForm = false;  
  }
  duplicateSubMenu() {   
    const q = new Promise((resolve, reject) => {
      this._subMenuService.duplicateSubMenu({
        'submenu': this.subMenuForm.controls['submenu'].value,
        'menuId': this.subMenuForm.controls['menu'].value,
        'id': null,
        'modifiedBy': null,
        'modifiedDate': null,
        'statusId': null,
        'createdBy': null,
        'createdDate': null,
        'MenuId': this.subMenuForm.controls['menu'].value,
        'orders': null,
        'submenuURL': null,
        'submenuIcon': null,
      }).subscribe((dublicateMenu) => {        
        if (dublicateMenu) {
          resolve({ 'duplicateSubMenu': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateSubMenu': true }); });
    });
    return q;
  }
}
export interface SubMenuModel {
  MenuName: String,
  menu: String,
  SubMenuName: String,
  PermissionName: String,
  id: number,
  ModifiedBy: number,
  ModifiedDate: String,
  sno: number,
  statusId: number,
  CreatedBy: number,
  CreatedDate: string,
  ID: number,
  MenuId: number,
  Orders: number,
  StatusId: number,
  SubMenu: String,
  SubMenuURL: String,
  SubmenuIcon: number,
}