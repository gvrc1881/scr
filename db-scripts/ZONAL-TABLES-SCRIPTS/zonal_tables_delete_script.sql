

--- delete script for zonal schema  of all tables

DELETE FROM zonal.daily_progress_summery;
DELETE FROM zonal.assets_monthly_targets_history;
DELETE FROM zonal.asset_status_update;
DELETE FROM zonal.assets_schedule_history;
DELETE FROM zonal.asset_schedule_activity_record;
DELETE FROM zonal.asset_schedule_activity_assoc;
DELETE FROM zonal.asset_monthly_targets;
DELETE FROM zonal.asset_master_history;
 DELETE FROM zonal.asset_master_data;


DELETE FROM zonal.stations_sections;
DELETE FROM zonal.sub_sectors;
DELETE FROM zonal.sectors;
DELETE FROM zonal.schedule;

DELETE FROM zonal.asset_schedule_assoc;

DELETE FROM zonal.gantry_master_data;
DELETE FROM zonal.functional_location_hierarchy;

DELETE FROM zonal.energy_consumption;
DELETE FROM zonal.energy_meter;
DELETE FROM zonal.elementary_section_lines;

DELETE FROM zonal.elementary_section_controls;
DELETE FROM zonal.elementary_sections;

DELETE FROM zonal.switch_maintenence_history_amendment;
DELETE FROM zonal.switch_maintenence_history;
DELETE FROM zonal.requirement;
DELETE FROM zonal.requirement_status;

DELETE FROM zonal.power_blocks_amendment;
DELETE FROM zonal.power_blocks;
DELETE FROM zonal.person;
DELETE FROM zonal.pb_switch_control;

DELETE FROM zonal.inventory_transfer;

DELETE FROM zonal.inventory_item_status;
DELETE FROM zonal.inventory_item_detail;
DELETE FROM zonal.work_effort_status;
DELETE FROM zonal.work_effort;
DELETE FROM zonal.item_issuance;
DELETE FROM zonal.inventory_item;
DELETE FROM zonal.indent_track;
DELETE FROM zonal.incoming_consignment_detail;
DELETE FROM zonal.incoming_consignment;
DELETE FROM zonal.good_identification;
DELETE FROM zonal.dmtr_inventoryetc_assoc;
DELETE FROM zonal.dmtr;



DELETE FROM zonal.user_defualt_fac_cons_ind_etc;
DELETE FROM zonal.tss_feeder_master;
DELETE FROM zonal.tpc_board_reporting_facilitys;
DELETE FROM zonal.tpc_board;


DELETE FROM zonal.consignee;
DELETE FROM zonal.content_purpose_type;
DELETE FROM zonal.content_type;
DELETE FROM zonal.country_tele_code;
DELETE FROM zonal.country_capital;
DELETE FROM zonal.country_code;


DELETE FROM zonal.departments;

DELETE FROM zonal.foot_patrolling_sections;
DELETE FROM zonal.good_identification_type;

DELETE FROM zonal.inspection_type;
DELETE FROM zonal.inspection;
DELETE FROM zonal.line;
 DELETE FROM zonal.lot;
DELETE FROM zonal.make;
DELETE FROM zonal.party_supplemental_data;
DELETE FROM zonal.party_group;
DELETE FROM zonal.party_contact_mech_purpose;
DELETE FROM zonal.party_contact_mech;
DELETE FROM zonal.ohe_location;
DELETE FROM zonal.measure_or_activity_list;
DELETE FROM zonal.model;



DELETE FROM zonal.party_role;
DELETE FROM zonal.party_status;
DELETE FROM zonal.period_type;
DELETE FROM zonal.priority_type;

DELETE FROM zonal.product_category_member;
DELETE FROM zonal.product_category;
DELETE FROM zonal.product_category_type;
DELETE FROM zonal.product;
DELETE FROM zonal.product_type;



DELETE FROM zonal.psi_report_registry;
DELETE FROM zonal.report_group_member;
DELETE FROM zonal.report_group;
DELETE FROM zonal.report_registry;

DELETE FROM zonal.requirement_type;
DELETE FROM zonal.role_type;

DELETE FROM zonal.status_valid_change;
DELETE FROM zonal.status_item;
DELETE FROM zonal.status_type;

DELETE FROM zonal.structure_type;
DELETE FROM zonal.telecom_number;
DELETE FROM zonal.work_effort_type;


 DELETE FROM zonal.app_device_login;
DELETE FROM zonal.user_login_history;

 DELETE FROM zonal.asset_make_model_assoc;

 DELETE FROM zonal.app_device_unit;
DELETE FROM zonal.user_login;

 DELETE FROM zonal.party;
DELETE FROM zonal.app_device;
DELETE FROM zonal.facility;

DELETE FROM zonal.facility_type;
 DELETE FROM zonal.uom_conversion;
DELETE FROM zonal.uom;

DELETE FROM zonal.inventory_item_type;

DELETE FROM zonal.uom_type;
 DELETE FROM zonal.allocation;
 DELETE FROM zonal.failures;  
DELETE FROM zonal.precautionary_measure;  
DELETE FROM zonal.precautionary_measures_master; 


-- Delete script for zonal schema of some tables (means where table_scope in (‘DIVISION’,’MIXED’))


select 'DELETE FROM zonal.'|| table_name ||' ;' 
from information_schema.tables ist , job_sch_activity_details jsad
where table_schema = 'zonal' 
and ist.table_name = jsad.tab_name
and jsad.create_update_delete = 'CREATE'
and table_scope not in ('APPLICATION','ZONAL')
order by table_name;

DELETE FROM zonal.allocation ;
DELETE FROM zonal.app_device ;
DELETE FROM zonal.app_device_login ;
DELETE FROM zonal.app_device_unit ;
DELETE FROM zonal.asset_make_model_assoc ;
DELETE FROM zonal.asset_master_data ;
DELETE FROM zonal.asset_master_history ;
DELETE FROM zonal.asset_monthly_targets ;
DELETE FROM zonal.asset_schedule_activity_assoc ;
DELETE FROM zonal.asset_schedule_activity_record ;
DELETE FROM zonal.asset_schedule_assoc ;
DELETE FROM zonal.assets_monthly_targets_history ;
DELETE FROM zonal.assets_schedule_history ;
DELETE FROM zonal.asset_status_update ;
DELETE FROM zonal.consignee ;
DELETE FROM zonal.daily_progress_summery ;
DELETE FROM zonal.dmtr ;
DELETE FROM zonal.dmtr_inventoryetc_assoc ;
DELETE FROM zonal.elementary_section_controls ;
DELETE FROM zonal.elementary_section_lines ;
DELETE FROM zonal.elementary_sections ;
DELETE FROM zonal.energy_consumption ;
DELETE FROM zonal.energy_meter ;
DELETE FROM zonal.facility ;
DELETE FROM zonal.failures ;
DELETE FROM zonal.foot_patrolling_sections ;
DELETE FROM zonal.functional_location_hierarchy ;
DELETE FROM zonal.gantry_master_data ;
DELETE FROM zonal.good_identification ;
DELETE FROM zonal.incoming_consignment ;
DELETE FROM zonal.incoming_consignment_detail ;
DELETE FROM zonal.indent_track ;
DELETE FROM zonal.inspection ;
DELETE FROM zonal.inspection_type ;
DELETE FROM zonal.inventory_item ;
DELETE FROM zonal.inventory_item_detail ;
DELETE FROM zonal.inventory_item_status ;
DELETE FROM zonal.inventory_transfer ;
DELETE FROM zonal.item_issuance ;
DELETE FROM zonal.line ;
DELETE FROM zonal.lot ;
DELETE FROM zonal.make ;
DELETE FROM zonal.measure_or_activity_list ;
DELETE FROM zonal.model ;
DELETE FROM zonal.ohe_location ;
DELETE FROM zonal.party ;
DELETE FROM zonal.party_contact_mech ;
DELETE FROM zonal.party_group ;
DELETE FROM zonal.party_role ;
DELETE FROM zonal.party_status ;
DELETE FROM zonal.party_supplemental_data ;
DELETE FROM zonal.pb_switch_control ;
DELETE FROM zonal.person ;
DELETE FROM zonal.power_blocks ;
DELETE FROM zonal.power_blocks_amendment ;
DELETE FROM zonal.precautionary_measure ;
DELETE FROM zonal.precautionary_measures_master ;
DELETE FROM zonal.product ;
DELETE FROM zonal.product_category ;
DELETE FROM zonal.product_category_member ;
DELETE FROM zonal.requirement ;
DELETE FROM zonal.requirement_status ;
DELETE FROM zonal.schedule ;
DELETE FROM zonal.sectors ;
DELETE FROM zonal.stations_sections ;
DELETE FROM zonal.sub_sectors ;
DELETE FROM zonal.switch_maintenence_history ;
DELETE FROM zonal.switch_maintenence_history_amendment ;
DELETE FROM zonal.telecom_number ;
DELETE FROM zonal.tpc_board ;
DELETE FROM zonal.tpc_board_reporting_facilitys ;
DELETE FROM zonal.tss_feeder_master ;
DELETE FROM zonal.user_defualt_fac_cons_ind_etc ;
DELETE FROM zonal.user_login ;
DELETE FROM zonal.user_login_history ;
DELETE FROM zonal.work_effort ;
DELETE FROM zonal.work_effort_status ;
DELETE FROM zonal.work_effort_type ;





