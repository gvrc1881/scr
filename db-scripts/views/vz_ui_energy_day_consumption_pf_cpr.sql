--  select * from v_energy_consumption  

  -- View: public.v_energy_day_consumption_pf_cpr

-- DROP VIEW public.v_energy_day_consumption_pf_cpr;

CREATE OR REPLACE VIEW public.v_energy_day_consumption_pf_cpr AS 
 SELECT 
--- current day details
    cur.seq_id, cur.id ,
    cur.location, cur.feeder_id,
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
   -- cur.multiplication_fac,
    -- previous day -------
    pre.energy_reading_date AS previous_reading_date,
    cur.energy_reading_date - pre.energy_reading_date AS no_of_days_lapsed_pre_reading,
    pre.kwh AS pre_kwh,
    pre.kvah AS pre_kvah,
    pre.rkvah_lag AS pre_rkvah_lag,
    pre.rkvah_lead AS pre_rkvah_lead,
    -- joint reading ---
 
    jr.energy_reading_date AS joint_reading_date,
    cur.energy_reading_date - jr.energy_reading_date AS no_of_days_lapsed_j_reading,
    jr.kwh AS jr_kwh,
    jr.kvah AS jr_kvah,
    jr.rkvah_lag AS jr_rkvah_lag,
    jr.rkvah_lead AS jr_rkvah_lead,
    
    ---- derived values consumption 
     --    start_rkvah_lead   start_rkvah_lag  start_kwh  start_kvah  
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
        END AS rkvah_lead_consumption
       ,
        CASE
            WHEN
		CASE
			WHEN pre.energy_reading_date IS NULL THEN (cur.kwh - cur.em_start_kwh) * cur.multiplication_fac
			ELSE (cur.kwh - pre.kwh) * cur.multiplication_fac
		END = 0::numeric THEN 0::numeric
            ELSE
            CASE
                WHEN pre.energy_reading_date IS NULL THEN (cur.kvah - cur.em_start_kvah) * cur.multiplication_fac
                ELSE (cur.kwh - pre.kwh) * cur.multiplication_fac
            END /
            CASE
                WHEN pre.energy_reading_date IS NULL THEN (cur.kvah - cur.em_start_kvah) * cur.multiplication_fac
                ELSE (cur.kvah - pre.kvah) * cur.multiplication_fac
            END
        END AS pf1 ,
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

     LEFT JOIN v_energy_consumption pre ON  ( pre.energy_reading_date = ( SELECT max(cur1.energy_reading_date) AS max
									FROM v_energy_consumption cur1
									WHERE cur1.energy_reading_date < cur.energy_reading_date 
									AND cur1.feeder_id = cur.feeder_id )
						AND pre.feeder_id = cur.feeder_id )

         
     LEFT JOIN v_energy_consumption jr ON  ( jr.energy_reading_date = ( SELECT max(jr1.energy_reading_date) AS max
									FROM v_energy_consumption jr1
									WHERE jr1.energy_reading_date < cur.energy_reading_date 
									AND (jr1.joint_meter = 'y'::bpchar OR jr1.joint_meter = 'Y'::bpchar) 
									AND jr1.feeder_id = cur.feeder_id 
									)
						AND jr.feeder_id = cur.feeder_id );
 
ALTER TABLE public.v_energy_day_consumption_pf_cpr
  OWNER TO postgres;

 ---  select * from v_energy_consumption  
-------