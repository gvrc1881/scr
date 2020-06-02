
--15
-- View: v_assets_schedule_completed_targets

CREATE OR REPLACE VIEW v_assets_schedule_completed_targets AS 
 SELECT to_char(vasht.schedule_date, 'dd-Mon-yyyy'::text) AS schedule_date, 
    vasht.asset_type, vasht.schedule_code, count(*) AS sch_done_count, 
    vasht.month_target, vasht.cum_month_target, vasht.total_year_target, 
    vasht.week1, vasht.month1, vasht.year1, vasht.fy, vasht.facility_id, 
    vasht.facility_name, vasht.facility_type_id, vasht.depot_type
   FROM v_assets_schedule_history_targets vasht
  GROUP BY vasht.fy, vasht.schedule_date, vasht.asset_type, vasht.schedule_code, vasht.facility_id, vasht.facility_name, vasht.facility_type_id, vasht.depot_type, vasht.week1, vasht.month1, vasht.year1, vasht.month_target, vasht.cum_month_target, vasht.total_year_target;
