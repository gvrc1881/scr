--- select * from v_energy_meter  
--  drop view v_energy_meter cascade;

create view v_energy_meter as 
select feeder_name,
em.id ,
cmd ,
em.created_stamp ,
em.created_tx_stamp ,
em.data_div as em_data_div,
end_date as em_end_date,
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
        END AS em_end_rkvah_lead ,
	em.feeder_id ,
	em.last_updated_stamp ,
	em.last_updated_tx_stamp ,
	meter_make ,
	meter_model ,
	meter_no ,
	CASE
            WHEN em.multiplication_fac::text = ''::text THEN 0::numeric
            ELSE em.multiplication_fac::numeric
        END AS multiplication_fac ,

	em.remarks ,
	seq_id ,
	start_date as em_start_date,
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

from energy_meter em , tss_feeder_master tss
where tss.feeder_id = em.feeder_id ;
