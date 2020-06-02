---2
-- View: v_asset_master_data

CREATE OR REPLACE VIEW v_asset_master_data AS 
 SELECT amd.seq_id, amd.asset_type, amd.section, amd.part1, amd.part2, 
    amd.part3, amd.kilometer, amd.position_id, amd.asset_id, amd.facility_id, 
    fac.facility_name, fac.facility_type_id, fac.depot_type, 
    amd.elementary_section, amd.oem_serial, amd.rly_assigned_serial, 
    amd.parent_asset_type, amd.parent_asset_type_id, amd.equipped_date, 
    amd.strip_date, amd.date_of_commision, amd.date_of_manufacture, 
    amd.date_of_received, amd.source, amd.make, amd.model, amd.warranty_amc, 
    amd.warranty_amc_end_date, amd.vendor, amd.expiry_date
   FROM asset_master_data amd, facility fac
  WHERE amd.facility_id::text = fac.facility_id::text;