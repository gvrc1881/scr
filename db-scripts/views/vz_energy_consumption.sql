--09 --DROP VIEW public.v_energy_consumption cascade;

CREATE OR REPLACE VIEW public.v_energy_consumption AS 
 SELECT ec.id,
    ec.seq_id,
    ec.energy_reading_date,
    ec.location_type,
    ec.location,
        CASE
            WHEN ec.kwh::text = ''::text THEN 0::numeric
            ELSE ec.kwh::numeric
        END AS kwh,
        CASE
            WHEN ec.kvah::text = ''::text THEN 0::numeric
            ELSE ec.kvah::numeric
        END AS kvah,
        CASE
            WHEN ec.rkvah_lag::text = ''::text THEN 0::numeric
            ELSE ec.rkvah_lag::numeric
        END AS rkvah_lag,
        CASE
            WHEN ec.rkvah_lead::text = ''::text THEN 0::numeric
            ELSE ec.rkvah_lead::numeric
        END AS rkvah_lead,
        CASE
            WHEN ec.cmd::text = ''::text THEN 0::numeric
            ELSE ec.cmd::numeric
        END AS cmd,
        CASE
            WHEN ec.rmd::text = ''::text THEN 0::numeric
            ELSE ec.rmd::numeric
        END AS rmd,
        CASE
            WHEN ec.pf::text = ''::text THEN 0::numeric
            ELSE ec.pf::numeric
        END AS pf,
        CASE
            WHEN ec.cpf::text = ''::text THEN 0::numeric
            ELSE ec.cpf::numeric
        END AS cpf,
        CASE
            WHEN ec.vol_max::text = ''::text THEN 0::numeric
            ELSE ec.vol_max::numeric
        END AS vol_max,
        CASE
            WHEN ec.vol_min::text = ''::text THEN 0::numeric
            ELSE ec.vol_min::numeric
        END AS vol_min,
        CASE
            WHEN ec.max_load::text = ''::text THEN 0::numeric
            ELSE ec.max_load::numeric
        END AS max_load,
    to_char(ec.max_load_time, 'dd-Mon-YYYY'::text) AS max_load_time_date,
    to_char(ec.max_load_time, 'HH:MM'::text) AS max_load_time_hhmm,
    ec.joint_meter,
    ec.remarks,
    ec.current_status,
    em.feeder_id,
    em.seq_id AS em_seq_id,
    em.em_start_date,
    em.em_end_date,
    em.em_end_kwh,
    em.em_end_kvah,
    em.em_end_rkvah_lag,
    em.em_end_rkvah_lead,
    em.em_start_kwh,
    em.em_start_kvah,
    em.em_start_rkvah_lag,
    em.em_start_rkvah_lead,
    em.multiplication_fac,
    em.remarks AS em_remarks
   FROM energy_consumption ec,
    v_energy_meter em
  WHERE ec.location::text = em.feeder_name::text AND ec.energy_reading_date >= em.em_start_date AND (ec.energy_reading_date <= em.em_end_date OR em.em_end_date IS NULL);

ALTER TABLE public.v_energy_consumption
  OWNER TO postgres;