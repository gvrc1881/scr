--19
-- View: v_sch_all_days_done_run_count_fac_asstype_sch

CREATE OR REPLACE VIEW v_sch_all_days_done_run_count_fac_asstype_sch AS 
 SELECT vasc1.days_fy, vasc1.schedule_date, vasc1.facility_id, 
    (vasc1.asset_type::text || '_'::text) || vasc1.schedule_code::text AS at_st, 
    vasc1.asset_type, vasc1.schedule_code, vasc1.is_dpr, vasc1.facility_name, 
    vasc1.fy, vasc1.month1, vasc1.week1, vasc1.day_count, 
    sum(vasc1.day_count) OVER (PARTITION BY vasc1.facility_id, vasc1.asset_type, vasc1.schedule_code, vasc1.facility_name, vasc1.fy, vasc1.month1, vasc1.week1 ORDER BY vasc1.facility_id, vasc1.asset_type, vasc1.schedule_code, vasc1.schedule_date) AS cum_count, 
        CASE
            WHEN vasc1.month1 = 1::double precision THEN vmct.target_jan
            WHEN vasc1.month1 = 2::double precision THEN vmct.target_feb
            WHEN vasc1.month1 = 3::double precision THEN vmct.target_mar
            WHEN vasc1.month1 = 4::double precision THEN vmct.target_apr
            WHEN vasc1.month1 = 5::double precision THEN vmct.target_may
            WHEN vasc1.month1 = 6::double precision THEN vmct.target_jun
            WHEN vasc1.month1 = 7::double precision THEN vmct.target_jul
            WHEN vasc1.month1 = 8::double precision THEN vmct.target_aug
            WHEN vasc1.month1 = 9::double precision THEN vmct.target_sep
            WHEN vasc1.month1 = 10::double precision THEN vmct.target_oct
            WHEN vasc1.month1 = 11::double precision THEN vmct.target_nov
            WHEN vasc1.month1 = 12::double precision THEN vmct.target_dec
            ELSE NULL::double precision
        END AS month_target, 
        CASE
            WHEN vasc1.month1 = 1::double precision THEN vmct.cum_target_jan
            WHEN vasc1.month1 = 2::double precision THEN vmct.cum_target_feb
            WHEN vasc1.month1 = 3::double precision THEN vmct.cum_target_mar
            WHEN vasc1.month1 = 4::double precision THEN vmct.cum_target_apr
            WHEN vasc1.month1 = 5::double precision THEN vmct.cum_target_may
            WHEN vasc1.month1 = 6::double precision THEN vmct.cum_target_jun
            WHEN vasc1.month1 = 7::double precision THEN vmct.cum_target_jul
            WHEN vasc1.month1 = 8::double precision THEN vmct.cum_target_aug
            WHEN vasc1.month1 = 9::double precision THEN vmct.cum_target_sep
            WHEN vasc1.month1 = 10::double precision THEN vmct.cum_target_oct
            WHEN vasc1.month1 = 11::double precision THEN vmct.cum_target_nov
            WHEN vasc1.month1 = 12::double precision THEN vmct.cum_target_dec
            ELSE NULL::double precision
        END AS cum_month_target
   FROM ( SELECT ads.days_fy, ads.is_dpr, 
            date_part('year'::text, ads.days_fy) AS year1, 
            date_part('month'::text, ads.days_fy) AS month1, 
            date_part('week'::text, ads.days_fy) AS week1, 
                CASE
                    WHEN date_part('month'::text, ads.days_fy) = 1::double precision OR date_part('month'::text, ads.days_fy) = 2::double precision OR date_part('month'::text, ads.days_fy) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (ads.days_fy - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, ads.days_fy)::integer, 100), '99'::text))
                    WHEN date_part('month'::text, ads.days_fy) = 4::double precision OR date_part('month'::text, ads.days_fy) = 5::double precision OR date_part('month'::text, ads.days_fy) = 6::double precision OR date_part('month'::text, ads.days_fy) = 7::double precision OR date_part('month'::text, ads.days_fy) = 8::double precision OR date_part('month'::text, ads.days_fy) = 9::double precision OR date_part('month'::text, ads.days_fy) = 10::double precision OR date_part('month'::text, ads.days_fy) = 11::double precision OR date_part('month'::text, ads.days_fy) = 12::double precision THEN (btrim(to_char(date_part('year'::text, ads.days_fy), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (ads.days_fy + '1 year'::interval)::date)::integer, 100), '99'::text))
                    ELSE NULL::text
                END AS fy, 
            ads.facility_id, ads.facility_name, ads.asset_type, 
            ads.schedule_code, vasc.schedule_date, 
                CASE
                    WHEN vasc.sch_done_count IS NULL THEN 0::bigint
                    ELSE vasc.sch_done_count
                END AS day_count
           FROM v_assets_schedule_counts vasc
      RIGHT JOIN ( SELECT generate_series(se.first_date::timestamp with time zone, se.last_date::timestamp with time zone, '1 day'::interval)::date AS days_fy, 
                    asa.asset_type, asa.schedule_code, asa.duration, 
                    asa.uom_of_duration, asa.is_dpr, asa.description, 
                    asa.sequence_code, ohe_fac.facility_id, 
                    ohe_fac.facility_name
                   FROM ( SELECT min(assets_schedule_history.schedule_date)::date AS first_date, 
                            max(assets_schedule_history.schedule_date)::date AS last_date
                           FROM assets_schedule_history) se, 
                    ( SELECT asset_schedule_assoc.asa_seq_id, asset_schedule_assoc.asset_type, 
                            asset_schedule_assoc.schedule_code, 
                            asset_schedule_assoc.sequence_code, 
                            asset_schedule_assoc.duration, 
                            asset_schedule_assoc.uom_of_duration, 
                            asset_schedule_assoc.description, 
                            asset_schedule_assoc.is_dpr, 
                            asset_schedule_assoc.created_on, 
                            asset_schedule_assoc.created_by, 
                            asset_schedule_assoc.last_updated_stamp, 
                            asset_schedule_assoc.last_updated_tx_stamp, 
                            asset_schedule_assoc.created_stamp, 
                            asset_schedule_assoc.created_tx_stamp
                           FROM asset_schedule_assoc) asa, 
                    ( SELECT facility.facility_id, facility.facility_name
                           FROM facility
                          WHERE facility.depot_type::text = 'OHE'::text) ohe_fac) ads ON ads.asset_type::text = vasc.asset_type::text AND ads.schedule_code::text = vasc.schedule_code::text AND ads.days_fy = vasc.schedule_date AND ads.facility_id::text = vasc.facility_id::text) vasc1
   LEFT JOIN v_monthly_cum_targets vmct ON vmct.asset_type::text = vasc1.asset_type::text AND vmct.schedule_type::text = vasc1.schedule_code::text AND vmct.facility_id::text = vasc1.facility_id::text AND vmct.fy = vasc1.fy
  GROUP BY vasc1.facility_id, vasc1.asset_type, vasc1.schedule_code, vasc1.facility_name, vasc1.fy, vasc1.month1, vasc1.year1, vasc1.week1, vasc1.schedule_date, vasc1.day_count, 
   CASE
       WHEN vasc1.month1 = 1::double precision THEN vmct.target_jan
       WHEN vasc1.month1 = 2::double precision THEN vmct.target_feb
       WHEN vasc1.month1 = 3::double precision THEN vmct.target_mar
       WHEN vasc1.month1 = 4::double precision THEN vmct.target_apr
       WHEN vasc1.month1 = 5::double precision THEN vmct.target_may
       WHEN vasc1.month1 = 6::double precision THEN vmct.target_jun
       WHEN vasc1.month1 = 7::double precision THEN vmct.target_jul
       WHEN vasc1.month1 = 8::double precision THEN vmct.target_aug
       WHEN vasc1.month1 = 9::double precision THEN vmct.target_sep
       WHEN vasc1.month1 = 10::double precision THEN vmct.target_oct
       WHEN vasc1.month1 = 11::double precision THEN vmct.target_nov
       WHEN vasc1.month1 = 12::double precision THEN vmct.target_dec
       ELSE NULL::double precision
   END, 
   CASE
       WHEN vasc1.month1 = 1::double precision THEN vmct.cum_target_jan
       WHEN vasc1.month1 = 2::double precision THEN vmct.cum_target_feb
       WHEN vasc1.month1 = 3::double precision THEN vmct.cum_target_mar
       WHEN vasc1.month1 = 4::double precision THEN vmct.cum_target_apr
       WHEN vasc1.month1 = 5::double precision THEN vmct.cum_target_may
       WHEN vasc1.month1 = 6::double precision THEN vmct.cum_target_jun
       WHEN vasc1.month1 = 7::double precision THEN vmct.cum_target_jul
       WHEN vasc1.month1 = 8::double precision THEN vmct.cum_target_aug
       WHEN vasc1.month1 = 9::double precision THEN vmct.cum_target_sep
       WHEN vasc1.month1 = 10::double precision THEN vmct.cum_target_oct
       WHEN vasc1.month1 = 11::double precision THEN vmct.cum_target_nov
       WHEN vasc1.month1 = 12::double precision THEN vmct.cum_target_dec
       ELSE NULL::double precision
   END, vasc1.days_fy, vasc1.is_dpr;