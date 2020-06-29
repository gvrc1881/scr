import { OnInit, Component, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ContentManagementService } from 'src/app/services/content-management.service';
import { DataSource } from '@angular/cdk/collections';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatDialogRef } from '@angular/material';
import { ContentManagementModel } from 'src/app/models/content-management.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ContentManagementDialogComponent } from '../content-management-edit-dialog/content-management-edit-dialog.component';

@Component({
    selector: 'content-management',
    templateUrl: './content-management.component.html',
    styleUrls: ['./content-management.component.scss']
})
export class ContentManagementComponent implements OnInit {
    editPermission: boolean = true;
    addPermission: boolean = true;
    deletePermission: boolean = true;
    filesExists: boolean = false;
    isSubmit: boolean = false;
    isReadOnly: boolean = true;
    onlyDrawing: boolean = false;
    update: boolean = false;
    selectedFiles: File[] = [];
    uploadedFilesList: any;
    selectedGenOps;
    selected:string;
    userdata: any = JSON.parse(localStorage.getItem('userData'));
    contentManagementFormGroup: FormGroup;
    progress: { percentage: number } = { percentage: 0 }
    pattern = "[a-zA-Z][a-zA-Z ]*";
    GenOpsArray = [{ ID: 1, VALUE: 'Circulars' }, { ID: 2, VALUE: 'Drawing' }, { ID: 3, VALUE: 'Tech Specs' }, { ID: 4, VALUE: 'Operational Manual' }, { ID: 5, VALUE: 'User manuals' }];

    displayedColumns = ['sno', 'originalFileName','size', 'genOps', 'description', 'actions'];
    // dataSource = new ExampleDataSource(initialData);
    dataSource: MatTableDataSource<ContentManagementModel>;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    contentManagementDialogRef: MatDialogRef<ContentManagementDialogComponent>;
    constructor(
        public dialog: MatDialog,
        private formBuilder: FormBuilder,
        private service: ContentManagementService,
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService: CommonService,
    ) {
        console.log('in constructor');
    }
    ngOnInit() {
        // console.log(JSON.stringify(this.userdata))
        this.createCMForm();

        var permissionName = this.commonService.getPermissionNameByLoggedData("CONTENT MANAGEMENT","CONTENT MANAGEMENT") ;
  		console.log("permissionName = "+permissionName);
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

        this.contentManagementFormGroup.get('GenOps').valueChanges.subscribe(item => {
           /*  let ops = this.GenOpsArray.filter(function (value) {
                return item == value.ID;
            });
            if (item == 2) {
                this.onlyDrawing = true;
            } else {
                this.onlyDrawing = false;
            }
            console.log(this.selectedGenOps) */
          //  this.selectedGenOps = ops[0].VALUE;
            //this.getUploadedFiles();
        })

    }

    createCMForm() {
        this.contentManagementFormGroup = this.formBuilder.group({
            GenOps: ['', Validators.required],
            description: ['', Validators.compose([Validators.required, Validators.pattern(this.pattern)])],
            uploadFiles: ['', Validators.required],
            assetTypeRlyId: ['', Validators.compose([Validators.required, Validators.pattern(this.pattern)])],
            make: ['', Validators.compose([Validators.required, Validators.pattern(this.pattern)])],
            model: ['', Validators.compose([Validators.required, Validators.pattern(this.pattern)])],
            docCategory: ['', Validators.compose([Validators.required, Validators.pattern(this.pattern)])],
        });
    }

    getUploadedFiles() {
        this.spinnerService.show();
        const uploadedFiles: ContentManagementModel[] = [];
        this.service.getUploadedFiles(this.userdata.id, this.selected).subscribe(data => {
           // console.log(JSON.stringify(data))
            this.spinnerService.hide();

            this.uploadedFilesList = data;
            for (let i = 0; i < this.uploadedFilesList.length; i++) {
                this.uploadedFilesList[i].sno = i + 1;
                uploadedFiles.push(this.uploadedFilesList[i]);
            }
            this.dataSource = new MatTableDataSource(uploadedFiles);
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;
        }, error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
        })
    }
    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.dataSource.filter = filterValue;
    }

    public get f() { return this.contentManagementFormGroup.controls; }

    upload(event) {
        if (event.target.files.length > 0) { this.filesExists = true; }
        for (var i = 0; i < event.target.files.length; i++) {
            this.selectedFiles.push(event.target.files[i]);
        }
    }
    removeFile(id) {
        this.selectedFiles.splice(id, 1);
    }
    onContentManagementSubmit() {
        console.log(this.contentManagementFormGroup.value.GenOps);
        if (this.onlyDrawing) {
            this.isSubmit = true;
            if (this.contentManagementFormGroup.invalid) {
                this.isSubmit = false;
                return;
            }
        } else {
            if (this.contentManagementFormGroup.value.description == '' ||
                this.contentManagementFormGroup.value.GenOps == '' ||
                this.contentManagementFormGroup.value.uploadFiles == '') {
                this.isSubmit = false;
                return false;
            } else {
                this.isSubmit = true;
            }
        }
        if (this.isSubmit) {
            this.spinnerService.show();
            let opsId = this.contentManagementFormGroup.value.GenOps;
            let description = this.contentManagementFormGroup.value.description;
            let ops = this.GenOpsArray.filter(function (value) {
                return opsId == value.ID;
            });

            let saveDetails = {
                'description': this.contentManagementFormGroup.value.description,
                'divisionCode': this.userdata.divisionCode,
                'createdBy': this.userdata.id,
                'createdDate': new Date(),
                'GenOps': ops[0].VALUE,
                'zonal': 'zonal',
                'FU': 'PSI',
                'topic': 'Indents',
                "assetTypeRlyId": this.contentManagementFormGroup.value.assetTypeRlyId,
                "make": this.contentManagementFormGroup.value.make,
                "model": this.contentManagementFormGroup.value.model,
                "docCategory": this.contentManagementFormGroup.value.docCategory,
            }
            this.service.uploadAttachedFiles(this.selectedFiles, saveDetails).subscribe(data => {
                console.log(JSON.stringify(data));
                this.spinnerService.hide();
                this.commonService.showAlertMessage("Files Uploaded and Saved Successfully");
                this.selectedFiles = [];
                this.filesExists = false;
                window.location.reload();
                //this.contentManagementFormGroup.reset();
                this.getUploadedFiles();
            }, error => {
                console.log('ERROR >>>');
                this.spinnerService.hide();
                this.commonService.showAlertMessage("Files Uploading Failed.");
            })
        }
    }

    deleteFile(id: number) {
        this.spinnerService.show();
        this.service.deleteFile(id).subscribe(data => {
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Files Uploaded and Saved Successfully");
            this.selectedFiles = [];
            this.filesExists = false;
            this.contentManagementFormGroup.reset();
            this.getUploadedFiles();
        }, error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Files Uploading Failed.");
        })
    }
    editDescription(id: number) {
        let selectedRow = this.dataSource.filteredData.filter(data => {
            return id == data.id;
        });
        console.log(selectedRow);
        this.contentManagementDialogRef = this.dialog.open(ContentManagementDialogComponent, {
            disableClose: false,
            //height: '200px',
            width: '40%',
            data: selectedRow
        });
        this.contentManagementDialogRef.afterClosed().subscribe(result => {
            console.log(`Dialog result: ${result}`);
            this.getUploadedFiles();
        });
    }


    genOpsChange() {
        if(this.selectedGenOps){
       // console.log('venkat;' + this.selectedGenOps);
        let id = this.selectedGenOps;
        let ops = this.GenOpsArray.find(function (value) {
            return id == value.ID;
        });
        if (this.selectedGenOps == 2) {
            this.onlyDrawing = true;
        } else {
            this.onlyDrawing = false;
        }
     //  console.log(ops)
        this.selected = ops.VALUE;
       // console.log(this.selected)
        this.getUploadedFiles();
        }
    }

}