--17
-- View: v_assets_schedule_counts

CREATE OR REPLACE VIEW v_assets_schedule_counts AS 
 SELECT to_char(vash.schedule_date, 'dd-Mon-yyyy'::text)::date AS schedule_date, 
    vash.asset_type, vash.schedule_code, count(*) AS sch_done_count, vash.fy, 
    vash.facility_id, vash.facility_name, vash.facility_type_id, 
    vash.depot_type, vash.month1, vash.year1, vash.week1
   FROM v_assets_schedule_history vash, v_monthly_cum_targets vmct
  WHERE vmct.asset_type::text = vash.asset_type::text AND vmct.schedule_type::text = vash.schedule_code::text AND vmct.facility_id::text = vash.facility_id::text AND vmct.fy = vash.fy
  GROUP BY vash.fy, vash.month1, vash.year1, vash.week1, vash.schedule_date, vash.asset_type, vash.schedule_code, vash.facility_id, vash.facility_name, vash.facility_type_id, vash.depot_type;
