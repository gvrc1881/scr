

-- View: public.v_energy_day_consumption_pf_cpr

-- DROP VIEW public.v_energy_day_consumption_pf_cpr cascade;

CREATE OR REPLACE VIEW public.v_energy_day_consumption_pf_cpr AS 
 SELECT cur.seq_id,
    cur.id,
    cur.location,
    cur.feeder_id,
    cur.energy_reading_date AS cuurent_reading_date,
    cur.kwh AS cur_kwh,
    cur.kvah AS cur_kvah,
    cur.rkvah_lag AS cur_rkvah_lag,
    cur.rkvah_lead AS cur_rkvah_lead,
    cur.cmd,
    cur.rmd,
    cur.vol_max,
    cur.vol_min,
    cur.max_load,
    cur.max_load_time_hhmm,
    cur.max_load_time_date,
    pre.energy_reading_date AS previous_reading_date,
    cur.energy_reading_date - pre.energy_reading_date AS no_of_days_lapsed_pre_reading,
    pre.kwh AS pre_kwh,
    pre.kvah AS pre_kvah,
    pre.rkvah_lag AS pre_rkvah_lag,
    pre.rkvah_lead AS pre_rkvah_lead,
    jr.energy_reading_date AS joint_reading_date,
    cur.energy_reading_date - jr.energy_reading_date AS no_of_days_lapsed_j_reading,
    jr.kwh AS jr_kwh,
    jr.kvah AS jr_kvah,
    jr.rkvah_lag AS jr_rkvah_lag,
    jr.rkvah_lead AS jr_rkvah_lead,
        CASE
            WHEN pre.energy_reading_date IS NULL THEN (cur.kwh - cur.em_start_kwh) * cur.multiplication_fac
            ELSE (cur.kwh - pre.kwh) * cur.multiplication_fac
        END AS kwh_consumption,
        CASE
            WHEN pre.energy_reading_date IS NULL THEN (cur.kvah - cur.em_start_kvah) * cur.multiplication_fac
            ELSE (cur.kwh - pre.kwh) * cur.multiplication_fac
        END AS kvah_consumption,
        CASE
            WHEN pre.energy_reading_date IS NULL THEN (cur.rkvah_lag - cur.em_start_rkvah_lag) * cur.multiplication_fac
            ELSE (cur.rkvah_lag - pre.rkvah_lag) * cur.multiplication_fac
        END AS rkvah_lag_consumption,
        CASE
            WHEN pre.energy_reading_date IS NULL THEN (cur.rkvah_lead - cur.em_start_rkvah_lead) * cur.multiplication_fac
            ELSE (cur.rkvah_lead - pre.rkvah_lead) * cur.multiplication_fac
        END AS rkvah_lead_consumption,
        CASE
            WHEN
            CASE
                WHEN pre.energy_reading_date IS NULL THEN (cur.kwh - cur.em_start_kwh) * cur.multiplication_fac
                ELSE (cur.kwh - pre.kwh) * cur.multiplication_fac
            END = 0::numeric THEN 0::numeric
            ELSE
            CASE
                WHEN pre.energy_reading_date IS NULL THEN (cur.kvah - cur.em_start_kwh) * cur.multiplication_fac
                ELSE (cur.kwh - pre.kwh) * cur.multiplication_fac
            END /
            CASE
                WHEN pre.energy_reading_date IS NULL THEN (cur.kvah - cur.em_start_kvah) * cur.multiplication_fac
                ELSE (cur.kvah - pre.kvah) * cur.multiplication_fac
            END
        END AS pf1,
        CASE
            WHEN
            CASE
                WHEN jr.energy_reading_date IS NULL THEN (cur.kwh - cur.em_start_kwh) * cur.multiplication_fac
                ELSE (cur.kwh - jr.kwh) * cur.multiplication_fac
            END = 0::numeric THEN 0::numeric
            ELSE
            CASE
                WHEN jr.energy_reading_date IS NULL THEN (cur.kwh - cur.em_start_kwh) * cur.multiplication_fac
                ELSE (cur.kwh - jr.kwh) * cur.multiplication_fac
            END /
            CASE
                WHEN jr.energy_reading_date IS NULL THEN (cur.kvah - cur.em_start_kvah) * cur.multiplication_fac
                ELSE (cur.kvah - jr.kvah) * cur.multiplication_fac
            END
        END AS cpf1
   FROM v_energy_consumption cur
     LEFT JOIN v_energy_consumption pre ON pre.energy_reading_date = (( SELECT max(cur1.energy_reading_date) AS max
           FROM v_energy_consumption cur1
          WHERE cur1.energy_reading_date < cur.energy_reading_date AND cur1.feeder_id::text = cur.feeder_id::text)) AND pre.feeder_id::text = cur.feeder_id::text
     LEFT JOIN v_energy_consumption jr ON jr.energy_reading_date = (( SELECT max(jr1.energy_reading_date) AS max
           FROM v_energy_consumption jr1
          WHERE jr1.energy_reading_date < cur.energy_reading_date AND (jr1.joint_meter::bpchar = 'y'::bpchar OR jr1.joint_meter::bpchar = 'Y'::bpchar) AND jr1.feeder_id::text = cur.feeder_id::text)) AND jr.feeder_id::text = cur.feeder_id::text;

ALTER TABLE public.v_energy_day_consumption_pf_cpr
  OWNER TO postgres;