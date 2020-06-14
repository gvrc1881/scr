
---09
-- View: v_energy_consumption

CREATE OR REPLACE VIEW public.v_energy_consumption AS
 SELECT ec.seq_id,
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
        CASE
            WHEN ec.kwh::text = ''::text THEN 0::numeric
            ELSE ec.kwh::numeric
        END AS multiplication_fac,
    em.seq_id AS em_seq_id,
    em.start_date AS em_start_date,
    em.end_date AS em_end_date,
        CASE
            WHEN em.m_start_reading::text = ''::text THEN 0::numeric
            ELSE em.m_start_reading::numeric
        END AS m_start_reading,
        CASE
            WHEN em.m_end_reading::text = ''::text THEN 0::numeric
            ELSE em.m_end_reading::numeric
        END AS m_end_reading,
    em.remarks AS em_remarks
   FROM energy_consumption ec,
    energy_meter em, tss_feeder_master tfm
  WHERE ec.location::text = tfm.feeder_name
  and tfm.feeder_id::text = em.feeder_id::text AND ec.energy_reading_date >= em.start_date AND (ec.energy_reading_date <= em.end_date OR em.end_date IS NULL);
