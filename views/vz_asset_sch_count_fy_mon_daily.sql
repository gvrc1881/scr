
--16
-- View: v_asset_sch_count_fy_mon_daily

CREATE OR REPLACE VIEW v_asset_sch_count_fy_mon_daily AS 
 SELECT d.fy, d.month1, d.schedule_date, d.facility_id, d.depot_type, 
    d.asset_type, d.schedule_code, d.daily_cnt, m.monthly_cnt, d.month_target, 
    y.fy_cnt, d.cum_month_target, d.total_year_target
   FROM ( SELECT vash.fy, vash.facility_id, vash.depot_type, vash.asset_type, 
            vash.schedule_code, count(*) AS fy_cnt, vash.month_target, 
            vash.cum_month_target, vash.total_year_target
           FROM v_assets_schedule_history_targets vash
          GROUP BY vash.fy, vash.facility_id, vash.depot_type, vash.asset_type, vash.schedule_code, vash.month_target, vash.cum_month_target, vash.total_year_target) y, 
    ( SELECT vash.fy, vash.month1, vash.facility_id, vash.depot_type, 
            vash.asset_type, vash.schedule_code, count(*) AS monthly_cnt, 
            vash.month_target, vash.cum_month_target, vash.total_year_target
           FROM v_assets_schedule_history_targets vash
          GROUP BY vash.fy, vash.month1, vash.facility_id, vash.depot_type, vash.asset_type, vash.schedule_code, vash.month_target, vash.cum_month_target, vash.total_year_target) m, 
    ( SELECT vash.fy, vash.month1, vash.schedule_date, vash.facility_id, 
            vash.depot_type, vash.asset_type, vash.schedule_code, 
            count(*) AS daily_cnt, vash.month_target, vash.cum_month_target, 
            vash.total_year_target
           FROM v_assets_schedule_history_targets vash
          GROUP BY vash.fy, vash.month1, vash.schedule_date, vash.facility_id, vash.depot_type, vash.asset_type, vash.schedule_code, vash.month_target, vash.cum_month_target, vash.total_year_target) d
  WHERE d.month1 = m.month1 AND d.facility_id::text = m.facility_id::text AND d.depot_type::text = m.depot_type::text AND d.asset_type::text = m.asset_type::text AND d.schedule_code::text = m.schedule_code::text AND d.fy = y.fy AND d.facility_id::text = y.facility_id::text AND d.depot_type::text = y.depot_type::text AND d.asset_type::text = y.asset_type::text AND d.schedule_code::text = y.schedule_code::text;
