import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AuthGuard } from './core/guards';

const routes: Routes = [
  {
    path: '',
    loadChildren: './components/login/login.module#LoginModule',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadChildren: './components/login/login.module#LoginModule',
    pathMatch: 'full'
  },
  {
    path: 'forgotPassword',
    loadChildren: './components/forgot-password/forgot-password.module#ForgotPasswordModule',
    pathMatch: 'full'
  },
  {
    path: 'registration',
    loadChildren: './components/register/register.module#RegisterModule',
    pathMatch: 'full'
  },
  {
    path: 'dashboard',
    loadChildren: './components/dashboard/dashboard.module#DashboardModule',
    canActivate: [AuthGuard]
  },{
    path: 'energyBillPayment',
    loadChildren: './components/energy-bill-payment/energy-bill-payment.module#EnergyBillPaymentModule',
    canActivate: [AuthGuard]
  },{
    path: 'contentManagement',
    loadChildren: './components/content-management/content-management.module#ContentManagementModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'guidenceItem',
    loadChildren: './components/energy-bill-payment/guidence-item/guidence-item.module#GuidenceItemModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'work',
    loadChildren: './components/energy-bill-payment/works/works.module#WorksModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'tractionEneTariff',
    loadChildren: './components/energy-bill-payment/traction-energy-tariff/traction-energy-tariff.module#TractionEnergyTariffModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'track',
    loadChildren: './components/energy-bill-payment/track/track.module#TrackModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'energyMeter',
    loadChildren: './components/energy-bill-payment/energy-meter/energy-meter.module#EnergyMeterModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'foot-patrolling-sections',
    loadChildren: './components/energy-bill-payment/foot-patrolling-sections/foot-patrolling-sections.module#FootPatrollingSectionsModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'sidings',
    loadChildren: './components/energy-bill-payment/sidings/sidings.module#SidingsModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'stations-sections',
    loadChildren: './components/energy-bill-payment/stations-sections/stations-sections.module#StationsSectionsModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'tpc-board',
    loadChildren: './components/energy-bill-payment/tpc-board/tpc-board.module#TPCBoardModule',
    canActivate: [AuthGuard]
  },
  {
    path:'tpc-board-depot-assoc',
    loadChildren:'./components/energy-bill-payment/tpc-board-depot-assoc/tpc-board-depot-assoc.module#TPCBoardDepotAssocModule',
    canActivate: [AuthGuard]
  },
   {
    path: 'daily-summary', 
    loadChildren: './components/energy-bill-payment/daily-summary/daily-summary.module#DailySummaryModule',
    canActivate: [AuthGuard]
  }, 
  {
    path: 'observation-categories',
    loadChildren: './components/energy-bill-payment/observation-categories/observation-categories.module#ObservationCategoriesModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'observation-check-list',
    loadChildren: './components/energy-bill-payment/observation-check-list/observation-check-list.module#ObservationCheckListModule',
    canActivate: [AuthGuard]
  },
 {
    path: 'foot-patrolling-inspection',
    loadChildren: './components/energy-bill-payment/foot-patrolling-inspection/foot-patrolling-inspection.module#FootPatrollingInspectionModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'menuMaster',
    loadChildren: './components/masters/menu/main-menu.module#MainMenuModule',
    canActivate: [AuthGuard]
  }, {
    path: 'masters',
    loadChildren: './components/masters/role-type/role-type.module#RoleTypeModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'schedule',
    loadChildren: './components/scheduler-tracking/scheduler-tracking.module#SchedulerTrackingModule',
    canActivate: [AuthGuard]
  }
  ,
  {
    path: 'divisions/:id/:name',
    loadChildren: './components/scheduler-tracking/divisions-info/divisions-info.module#DivisionInfoModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'jobs/:id',
    loadChildren: './components/scheduler-tracking/jobs-info/jobs-info.module#JobsInfoModule',
    canActivate: [AuthGuard]
  }
  ,
  {
    path: 'settings',
    loadChildren: './components/scheduler-settings/scheduler-settings.module#SchedulerSettingsModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'repository',
    loadChildren: './components/scheduler-settings/repository/repository.module#RepositoryModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'jobType',
    loadChildren: './components/scheduler-settings/job-type/job-type.module#JobTypeModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'timeInterval',
    loadChildren: './components/scheduler-settings/time-interval/time-interval.module#TimeIntervalModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'reports',
    loadChildren: './components/reports/reports.module#ReportsModule',
    canActivate: [AuthGuard]
  }, 
  {
    path: 'daily-progress-reports',
    loadChildren: './components/reports/report-names/report-names.module#ReportNamesModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'asset-reports',
    loadChildren: './components/reports/report-names/report-names.module#ReportNamesModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'asset-master-reports',
    loadChildren: './components/reports/report-names/report-names.module#ReportNamesModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'inventory-reports',
    loadChildren: './components/reports/report-names/report-names.module#ReportNamesModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'zonal-reports',
    loadChildren: './components/reports/report-names/report-names.module#ReportNamesModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'psi-reports',
    loadChildren: './components/reports/report-names/report-names.module#ReportNamesModule',
    canActivate: [AuthGuard]
  },
   {
    path: 'submenuMaster',
    loadChildren: './components/masters/submenu/submenu.module#SubMenuModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'users',
    loadChildren: './components/masters/users/users.module#UserModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'department',
    loadChildren: './components/masters/department/department.module#DepartmentModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'roles',
    loadChildren: './components/masters/role-type/role-type.module#RoleTypeModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'rolePermissions',
    loadChildren: './components/masters/role-permissions/role-permissions.module#RolePermissionsModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'mailConfirmation',
    loadChildren: './components/mail-confirm/mail-confirm.module#MailConfirmModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'changePassword',
    loadChildren: './components/change-password/change-password.module#ChangePasswordModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'reportParameterDisplay/:id',
    loadChildren: './components/reports/reportParameterDisplay.module#ReportParameterDisplayModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'resetPassword',
    loadChildren: './components/forgot-password/forgot-password.module#ForgotPasswordModule',
  },
  {
    path: 'drives',
    loadChildren: './components/drives/drive/drive.module#DriveModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'checklist',
    loadChildren: './components/drives/drive-checklist/drive-checklist.module#DriveChecklistModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'target',
    loadChildren: './components/drives/drive-target/drive-target.module#DriveTargetModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'progress-record',
    loadChildren: './components/drives/drive-progress-record/drive-progress-record.module#DriveProgressRecordModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'failure-analysis',
    loadChildren: './components/drives/drive-failure-analysis/drive-failure-analysis.module#DriveFailureAnalysisModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'electrification-targets',
    loadChildren: './components/drives/drive-electrification-targets/drive-electrification-targets.module#DriveElectrificationTargetsModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'inspection',
    loadChildren: './components/drives/drive-inspection/drive-inspection.module#DriveInspectionModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'stipulation',
    loadChildren: './components/drives/drive-stipulation/drive-stipulation.module#DriveStipulationModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'ins',
    loadChildren: './components/drives/drive-inspection/drive-inspection.module#DriveInspectionModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'ins-stipulation',
    loadChildren: './components/drives/drive-stipulation/drive-stipulation.module#DriveStipulationModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'make',
    loadChildren: './components/energy-bill-payment/make/make.module#MakeModule',
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    Ng4LoadingSpinnerModule.forRoot()
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
