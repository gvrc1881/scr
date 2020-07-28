import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MatToolbarModule, MatIconModule, MatSidenavModule, MatListModule, MatButtonModule, MatFormFieldModule, MatInputModule, MatDialogModule, MatSnackBarModule, MatMenuModule, MatTableModule, MatPaginatorModule, MatTabsModule } from  '@angular/material';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { AuthenticationService } from './services/authentication.service';
import { AlertService } from './services/alert.service';
import { AuthGuard } from './core/guards';
import { CommonService } from './common/common.service';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './modules/material.modules';
import { FuseConfirmDialogComponent } from './components/confirm-dialog/confirm-dialog.component';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';
import { ChangePasswordDialogComponent } from './components/change-password-dialog/change-password-dialog.component';
import { DivisionHistoryDialogComponent } from './components/division-history-dialog/division-history-dialog.component';
import { RemarkDialogComponent } from './components/remark-dialog/remark-dialog.component';
import { DocumentDialogComponent } from './components/document-view-dialog/document-dialog.component';
import { InspectionDocumentComponent } from './components/inpection-document-dialog/inspection-document-dialog.component';
// Import angular-fusioncharts
import { FusionChartsModule } from 'angular-fusioncharts';
 
// Import FusionCharts library and chart modules
import * as FusionCharts from 'fusioncharts';
import * as Charts from 'fusioncharts/fusioncharts.charts';

import * as FusionTheme from 'fusioncharts/themes/fusioncharts.theme.fusion';
import { SatPopoverModule } from '@ncstate/sat-popover';
import { ContentManagementDialogComponent } from './components/content-management-edit-dialog/content-management-edit-dialog.component';
import { FilesInformationDialogComponent } from './components/file-information-dialog/file-information-dialog.component';
import { DriveModule } from './components/drives/drive/drive.module';
import { DriveElectrificationTargetsModule } from './components/drives/drive-electrification-targets/drive-electrification-targets.module';
import { DriveStipulationModule } from './components/drives/drive-stipulation/drive-stipulation.module';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime'; 
import {  DecimalValidationsModule } from './modules/decimal-validations.module';
import { NumberValidationsModule } from './modules/number-validations.module';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { PreviousRouteService } from './services/previousRoute.service';

// Pass the fusioncharts library and chart modules
FusionChartsModule.fcRoot(FusionCharts, Charts, FusionTheme);

@NgModule({
  declarations: [
    AppComponent,         
    FuseConfirmDialogComponent,
    FooterComponent,
    HeaderComponent, 
    ChangePasswordDialogComponent,    
    RemarkDialogComponent,    
    DivisionHistoryDialogComponent,    
    ContentManagementDialogComponent,
    DocumentDialogComponent,
    FilesInformationDialogComponent,
    InspectionDocumentComponent,
  ],
  imports: [
    BrowserModule,
    FusionChartsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    AppRoutingModule,    
    FormsModule,
    ReactiveFormsModule,
    CommonModule,        
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule,
    MatMenuModule,
    MatTableModule,
    MatPaginatorModule,
    MatTabsModule,
    SatPopoverModule,
    DriveModule,
    DriveElectrificationTargetsModule,
    DriveStipulationModule,
    NumberValidationsModule,
    DecimalValidationsModule,
    OwlDateTimeModule, 
    OwlNativeDateTimeModule,
    
    ],
  entryComponents: [
        FuseConfirmDialogComponent,
        DivisionHistoryDialogComponent,
        RemarkDialogComponent,
        ContentManagementDialogComponent,
        DocumentDialogComponent,
        FilesInformationDialogComponent,
        InspectionDocumentComponent,
  ],
  providers: [
    AuthenticationService,
    AlertService,
    AuthGuard,
    MaterialModule,
    CommonService,
    ReactiveFormsModule,
    DatePipe,
    SendAndRequestService,
    PreviousRouteService
  ],
  bootstrap: [AppComponent],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class AppModule { }
