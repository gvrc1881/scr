--14
-- View: v_assets_schedule_history_targets

CREATE OR REPLACE VIEW v_assets_schedule_history_targets AS 
 SELECT ash.asset_id, ash.asset_type, ash.schedule_code, ash.schedule_date, 
    ash.year1, ash.month1, ash.week1, ash.fy, ash.status, ash.details_of_maint, 
    ash.done_by, ash.initial_of_incharge, ash.remarks, ash.pb_operation_seq_id, 
        CASE
            WHEN ash.month1 = 1::double precision THEN vmct.target_jan
            WHEN ash.month1 = 2::double precision THEN vmct.target_feb
            WHEN ash.month1 = 3::double precision THEN vmct.target_mar
            WHEN ash.month1 = 4::double precision THEN vmct.target_apr
            WHEN ash.month1 = 5::double precision THEN vmct.target_may
            WHEN ash.month1 = 6::double precision THEN vmct.target_jun
            WHEN ash.month1 = 7::double precision THEN vmct.target_jul
            WHEN ash.month1 = 8::double precision THEN vmct.target_aug
            WHEN ash.month1 = 9::double precision THEN vmct.target_sep
            WHEN ash.month1 = 10::double precision THEN vmct.target_oct
            WHEN ash.month1 = 11::double precision THEN vmct.target_nov
            WHEN ash.month1 = 12::double precision THEN vmct.target_dec
            ELSE NULL::double precision
        END AS month_target, 
        CASE
            WHEN ash.month1 = 1::double precision THEN vmct.cum_target_jan
            WHEN ash.month1 = 2::double precision THEN vmct.cum_target_feb
            WHEN ash.month1 = 3::double precision THEN vmct.cum_target_mar
            WHEN ash.month1 = 4::double precision THEN vmct.cum_target_apr
            WHEN ash.month1 = 5::double precision THEN vmct.cum_target_may
            WHEN ash.month1 = 6::double precision THEN vmct.cum_target_jun
            WHEN ash.month1 = 7::double precision THEN vmct.cum_target_jul
            WHEN ash.month1 = 8::double precision THEN vmct.cum_target_aug
            WHEN ash.month1 = 9::double precision THEN vmct.cum_target_sep
            WHEN ash.month1 = 10::double precision THEN vmct.cum_target_oct
            WHEN ash.month1 = 11::double precision THEN vmct.cum_target_nov
            WHEN ash.month1 = 12::double precision THEN vmct.cum_target_dec
            ELSE NULL::double precision
        END AS cum_month_target, 
    vmct.total_year_target, ash.facility_id, fac.facility_name, 
    fac.facility_type_id, fac.depot_type
   FROM v_assets_schedule_history ash, facility fac, v_monthly_cum_targets vmct
  WHERE ash.facility_id::text = fac.facility_id::text AND vmct.asset_type::text = ash.asset_type::text AND vmct.schedule_type::text = ash.schedule_code::text AND vmct.facility_id::text = ash.facility_id::text AND vmct.fy = ash.fy;
