
---20
-- View: v_sch_done_daily_run_count_fac_asstype_sch

CREATE OR REPLACE VIEW v_sch_done_daily_run_count_fac_asstype_sch AS 
 SELECT vasc.schedule_date, vasc.facility_id, vasc.asset_type, 
    vasc.schedule_code, vasc.facility_name, vasc.fy, vasc.month1, vasc.week1, 
    vasc.sch_done_count AS day_count, 
    sum(vasc.sch_done_count) OVER (PARTITION BY vasc.facility_id, vasc.asset_type, vasc.schedule_code, vasc.facility_name, vasc.fy, vasc.month1, vasc.week1 ORDER BY vasc.facility_id, vasc.asset_type, vasc.schedule_code, vasc.schedule_date) AS cum_count, 
        CASE
            WHEN vasc.month1 = 1::double precision THEN vmct.target_jan
            WHEN vasc.month1 = 2::double precision THEN vmct.target_feb
            WHEN vasc.month1 = 3::double precision THEN vmct.target_mar
            WHEN vasc.month1 = 4::double precision THEN vmct.target_apr
            WHEN vasc.month1 = 5::double precision THEN vmct.target_may
            WHEN vasc.month1 = 6::double precision THEN vmct.target_jun
            WHEN vasc.month1 = 7::double precision THEN vmct.target_jul
            WHEN vasc.month1 = 8::double precision THEN vmct.target_aug
            WHEN vasc.month1 = 9::double precision THEN vmct.target_sep
            WHEN vasc.month1 = 10::double precision THEN vmct.target_oct
            WHEN vasc.month1 = 11::double precision THEN vmct.target_nov
            WHEN vasc.month1 = 12::double precision THEN vmct.target_dec
            ELSE NULL::double precision
        END AS month_target, 
        CASE
            WHEN vasc.month1 = 1::double precision THEN vmct.cum_target_jan
            WHEN vasc.month1 = 2::double precision THEN vmct.cum_target_feb
            WHEN vasc.month1 = 3::double precision THEN vmct.cum_target_mar
            WHEN vasc.month1 = 4::double precision THEN vmct.cum_target_apr
            WHEN vasc.month1 = 5::double precision THEN vmct.cum_target_may
            WHEN vasc.month1 = 6::double precision THEN vmct.cum_target_jun
            WHEN vasc.month1 = 7::double precision THEN vmct.cum_target_jul
            WHEN vasc.month1 = 8::double precision THEN vmct.cum_target_aug
            WHEN vasc.month1 = 9::double precision THEN vmct.cum_target_sep
            WHEN vasc.month1 = 10::double precision THEN vmct.cum_target_oct
            WHEN vasc.month1 = 11::double precision THEN vmct.cum_target_nov
            WHEN vasc.month1 = 12::double precision THEN vmct.cum_target_dec
            ELSE NULL::double precision
        END AS cum_month_target
   FROM v_assets_schedule_counts vasc, v_monthly_cum_targets vmct
  WHERE vmct.asset_type::text = vasc.asset_type::text AND vmct.schedule_type::text = vasc.schedule_code::text AND vmct.facility_id::text = vasc.facility_id::text AND vmct.fy = vasc.fy
  GROUP BY vasc.facility_id, vasc.asset_type, vasc.schedule_code, vasc.facility_name, vasc.fy, vasc.month1, vasc.year1, vasc.week1, vasc.schedule_date, vasc.sch_done_count, 
        CASE
            WHEN vasc.month1 = 1::double precision THEN vmct.target_jan
            WHEN vasc.month1 = 2::double precision THEN vmct.target_feb
            WHEN vasc.month1 = 3::double precision THEN vmct.target_mar
            WHEN vasc.month1 = 4::double precision THEN vmct.target_apr
            WHEN vasc.month1 = 5::double precision THEN vmct.target_may
            WHEN vasc.month1 = 6::double precision THEN vmct.target_jun
            WHEN vasc.month1 = 7::double precision THEN vmct.target_jul
            WHEN vasc.month1 = 8::double precision THEN vmct.target_aug
            WHEN vasc.month1 = 9::double precision THEN vmct.target_sep
            WHEN vasc.month1 = 10::double precision THEN vmct.target_oct
            WHEN vasc.month1 = 11::double precision THEN vmct.target_nov
            WHEN vasc.month1 = 12::double precision THEN vmct.target_dec
            ELSE NULL::double precision
        END, 
        CASE
            WHEN vasc.month1 = 1::double precision THEN vmct.cum_target_jan
            WHEN vasc.month1 = 2::double precision THEN vmct.cum_target_feb
            WHEN vasc.month1 = 3::double precision THEN vmct.cum_target_mar
            WHEN vasc.month1 = 4::double precision THEN vmct.cum_target_apr
            WHEN vasc.month1 = 5::double precision THEN vmct.cum_target_may
            WHEN vasc.month1 = 6::double precision THEN vmct.cum_target_jun
            WHEN vasc.month1 = 7::double precision THEN vmct.cum_target_jul
            WHEN vasc.month1 = 8::double precision THEN vmct.cum_target_aug
            WHEN vasc.month1 = 9::double precision THEN vmct.cum_target_sep
            WHEN vasc.month1 = 10::double precision THEN vmct.cum_target_oct
            WHEN vasc.month1 = 11::double precision THEN vmct.cum_target_nov
            WHEN vasc.month1 = 12::double precision THEN vmct.cum_target_dec
            ELSE NULL::double precision
        END;
