-- View: public.v_energy_meter

-- DROP VIEW public.v_energy_meter cascade;

CREATE OR REPLACE VIEW public.v_energy_meter AS 
 SELECT tss.feeder_name,
    em.id,
    em.cmd,
    em.created_stamp,
    em.created_tx_stamp,
    em.data_div AS em_data_div,
    em.end_date AS em_end_date,
        CASE
            WHEN em.end_kwh::text = ''::text THEN 0::numeric
            ELSE em.end_kwh::numeric
        END AS em_end_kwh,
        CASE
            WHEN em.end_kvah::text = ''::text THEN 0::numeric
            ELSE em.end_kvah::numeric
        END AS em_end_kvah,
        CASE
            WHEN em.end_rkvah_lag::text = ''::text THEN 0::numeric
            ELSE em.end_rkvah_lag::numeric
        END AS em_end_rkvah_lag,
        CASE
            WHEN em.end_rkvah_lead::text = ''::text THEN 0::numeric
            ELSE em.end_rkvah_lead::numeric
        END AS em_end_rkvah_lead,
    em.feeder_id,
    em.last_updated_stamp,
    em.last_updated_tx_stamp,
    em.meter_make,
    em.meter_model,
    em.meter_no,
   
        CASE
            WHEN em.multiplication_fac::text = ''::text THEN 0::numeric
            ELSE em.multiplication_fac::numeric
        END AS multiplication_fac,
    em.remarks,
    em.seq_id,
    em.start_date AS em_start_date,
        CASE
            WHEN em.m_start_reading::text = ''::text THEN 0::numeric
            ELSE em.m_start_reading::numeric
        END AS em_m_start_reading,
        CASE
            WHEN em.m_end_reading::text = ''::text THEN 0::numeric
            ELSE em.m_end_reading::numeric
        END AS em_m_end_reading,
        CASE
            WHEN em.start_kwh::text = ''::text THEN 0::numeric
            ELSE em.start_kwh::numeric
        END AS em_start_kwh,
        CASE
            WHEN em.start_kvah::text = ''::text THEN 0::numeric
            ELSE em.start_kvah::numeric
        END AS em_start_kvah,
        CASE
            WHEN em.start_rkvah_lag::text = ''::text THEN 0::numeric
            ELSE em.start_rkvah_lag::numeric
        END AS em_start_rkvah_lag,
        CASE
            WHEN em.start_rkvah_lead::text = ''::text THEN 0::numeric
            ELSE em.start_rkvah_lead::numeric
        END AS em_start_rkvah_lead
   FROM energy_meter em,
    tss_feeder_master tss
  WHERE tss.feeder_id::text = em.feeder_id::text;

ALTER TABLE public.v_energy_meter
  OWNER TO postgres;
