--3
-- View: v_asset_monthly_targets

CREATE OR REPLACE VIEW v_asset_monthly_targets AS 
 SELECT amt.seq_id, amt.facility_id, fac.facility_name, fac.facility_type_id, 
    fac.depot_type, amt.schedule_type, amt.asset_type, amt.target_jan, 
    amt.target_feb, amt.target_mar, amt.target_apr, amt.target_may, 
    amt.target_june AS target_jun, amt.target_july AS target_jul, 
    amt.target_aug, amt.target_sep, amt.target_oct, amt.target_nov, 
    amt.target_dec, 
    amt.target_jan + amt.target_feb + amt.target_mar + amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec AS total_target_year, 
    amt.year, 
    btrim((amt.year::text || '-'::text) || (((amt.year::integer + 1) % 100)::text)) AS fy
   FROM asset_monthly_targets amt, facility fac
  WHERE amt.facility_id::text = fac.facility_id::text;