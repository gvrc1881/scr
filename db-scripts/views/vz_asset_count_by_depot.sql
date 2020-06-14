--1
-- View: v_asset_count_by_depot

CREATE OR REPLACE VIEW v_asset_count_by_depot AS 
 SELECT amd.asset_type, count(*) AS number_of_assets, amd.facility_id, 
    fac.facility_name, fac.facility_type_id, fac.depot_type
   FROM asset_master_data amd, facility fac
  WHERE amd.facility_id::text = fac.facility_id::text
  GROUP BY amd.asset_type, amd.facility_id, fac.facility_name, fac.facility_type_id, fac.depot_type;