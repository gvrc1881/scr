
--1
-- View: v_asset_count_by_depot

CREATE OR REPLACE VIEW v_asset_count_by_depot AS 
 SELECT amd.asset_type, count(*) AS number_of_assets, amd.facility_id, 
    fac.facility_name, fac.facility_type_id, fac.depot_type
   FROM asset_master_data amd, facility fac
  WHERE amd.facility_id::text = fac.facility_id::text
  GROUP BY amd.asset_type, amd.facility_id, fac.facility_name, fac.facility_type_id, fac.depot_type;

---2
-- View: v_asset_master_data

CREATE OR REPLACE VIEW v_asset_master_data AS 
 SELECT amd.seq_id, amd.asset_type, amd.section, amd.part1, amd.part2, 
    amd.part3, amd.kilometer, amd.position_id, amd.asset_id, amd.facility_id, 
    fac.facility_name, fac.facility_type_id, fac.depot_type, 
    amd.elementary_section, amd.oem_serial, amd.rly_assigned_serial, 
    amd.parent_asset_type, amd.parent_asset_type_id, amd.equipped_date, 
    amd.strip_date, amd.date_of_commision, amd.date_of_manufacture, 
    amd.date_of_received, amd.source, amd.make, amd.model, amd.warranty_amc, 
    amd.warranty_amc_end_date, amd.vendor, amd.expiry_date
   FROM asset_master_data amd, facility fac
  WHERE amd.facility_id::text = fac.facility_id::text;

--3
-- View: v_asset_monthly_targets

CREATE OR REPLACE VIEW v_asset_monthly_targets AS 
 SELECT amt.seq_id, amt.facility_id, fac.facility_name, fac.facility_type_id, 
    fac.depot_type, amt.schedule_type, amt.asset_type, amt.target_jan, 
    amt.target_feb, amt.target_mar, amt.target_apr, amt.target_may, 
    amt.target_june AS target_jun, amt.target_july AS target_jul, 
    amt.target_aug, amt.target_sep, amt.target_oct, amt.target_nov, 
    amt.target_dec, 
    amt.target_jan + amt.target_feb + amt.target_mar + amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec AS total_target_year, 
    amt.year, 
    btrim((amt.year::text || '-'::text) || (((amt.year::integer + 1) % 100)::text)) AS fy
   FROM asset_monthly_targets amt, facility fac
  WHERE amt.facility_id::text = fac.facility_id::text;

--4
-- View: v_asset_schedule_activity_assoc


-- DROP VIEW v_asset_schedule_activity_assoc;

CREATE OR REPLACE VIEW v_asset_schedule_activity_assoc AS

  SELECT asa.asset_type,

     asa.schedule_code,
     asa.duration,
     asa.uom_of_duration,
     asa.is_dpr,
     asa.target_plan_months,

     asaa.activity_id,

     asaa.activity_position_id,

     mal.activity_name,

     mal.activity_type,

     asaa.make_code,

     asaa.model_code,

     asaa.lower_limit,

     asaa.upper_limit,

     asaa.description,

     asaa.seq_id,

     asaa.asa_seq_id,

     asaa.sub_asset_type,

     asaa.activity_flag,

     asaa.display_order,
     asaa.report_column_header

    FROM asset_schedule_activity_assoc asaa,

     asset_schedule_assoc asa,

     measure_or_activity_list mal

   WHERE asaa.asa_seq_id::text = asa.asa_seq_id::text AND

asaa.activity_id::text = mal.activity_id::text;

ALTER TABLE v_asset_schedule_activity_assoc

   OWNER TO postgres;

--05
-- View: v_asset_schedule_activity_record_latest

CREATE OR REPLACE VIEW v_asset_schedule_activity_record_latest AS 
 SELECT row_number() OVER (ORDER BY amd.kilometer::integer, amd.position_id::integer) AS s_no, 
    asar.asset_id, amd.asset_type, asar.schedule_code, asar.schedule_date, 
    asar.schedule_actual_date, asar.status, asar.m1::numeric AS m1, 
    amd.kilometer::numeric AS kilometer, 
    amd.position_id::numeric AS position_id, amd.part1::numeric AS span, 
    asar.m2::numeric AS m2, asar.m3::numeric AS m3, asar.m4::numeric AS m4, 
    asar.m5::numeric AS m5, asar.m6::numeric AS m6, asar.m7::numeric AS m7, 
    asar.m8::numeric AS m8, asar.m9::numeric AS m9, asar.m10::numeric AS m10, 
    asar.m11::numeric AS m11, asar.m12::numeric AS m12, 
    asar.m13::numeric AS m13, asar.m14::numeric AS m14, 
    asar.m15::numeric AS m15, asar.m16::numeric AS m16, 
    asar.m17::numeric AS m17, asar.m18::numeric AS m18, 
    asar.m19::numeric AS m19, asar.m20::numeric AS m20, 
    asar.m21::numeric AS m21, asar.m22::numeric AS m22, asar.a1, asar.a2, 
    asar.a3, asar.a4, asar.a5, asar.a6, asar.a7, asar.a8, asar.a9, asar.a10, 
    asar.a11, asar.a12, asar.a13, asar.a14, asar.a15, asar.a16, asar.a17, 
    asar.a18, asar.a19, asar.a20, asar.a21, asar.a22
   FROM asset_schedule_activity_record asar, asset_master_data amd
  WHERE asar.asset_id::text = amd.asset_id::text AND asar.schedule_date = (( SELECT max(asar1.schedule_date) AS max
           FROM asset_schedule_activity_record asar1
          WHERE asar.asset_id::text = asar1.asset_id::text AND asar.schedule_code::text = 'AOH'::text));
--06
--View: v_assets_schedule_history

CREATE OR REPLACE VIEW v_assets_schedule_history AS 
 SELECT ash.seq_id, ash.asset_id, ash.asset_type, ash.schedule_code, 
    ash.schedule_date, date_part('year'::text, ash.schedule_date) AS year1, 
    date_part('month'::text, ash.schedule_date) AS month1, 
    date_part('week'::text, ash.schedule_date) AS week1, 
        CASE
            WHEN date_part('month'::text, ash.schedule_date) = 1::double precision OR date_part('month'::text, ash.schedule_date) = 2::double precision OR date_part('month'::text, ash.schedule_date) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (ash.schedule_date - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, ash.schedule_date)::integer, 100), '99'::text))
            WHEN date_part('month'::text, ash.schedule_date) = 4::double precision OR date_part('month'::text, ash.schedule_date) = 5::double precision OR date_part('month'::text, ash.schedule_date) = 6::double precision OR date_part('month'::text, ash.schedule_date) = 7::double precision OR date_part('month'::text, ash.schedule_date) = 8::double precision OR date_part('month'::text, ash.schedule_date) = 9::double precision OR date_part('month'::text, ash.schedule_date) = 10::double precision OR date_part('month'::text, ash.schedule_date) = 11::double precision OR date_part('month'::text, ash.schedule_date) = 12::double precision THEN (btrim(to_char(date_part('year'::text, ash.schedule_date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (ash.schedule_date + '1 year'::interval)::date)::integer, 100), '99'::text))
            ELSE NULL::text
        END AS fy, 
    ash.status, ash.details_of_maint, ash.done_by, ash.initial_of_incharge, 
    ash.remarks, ash.pb_operation_seq_id, ash.facility_id, fac.facility_name, 
    fac.facility_type_id, fac.depot_type
   FROM assets_schedule_history ash, facility fac
  WHERE ash.facility_id::text = fac.facility_id::text;

---07
-- View: v_asset_sch_next_due_date

CREATE OR REPLACE VIEW v_asset_sch_next_due_date AS 
 SELECT amd.asset_id, vash.asset_type, vash.schedule_code, asa.duration, 
    asa.uom_of_duration, amd.date_of_commision, 
        CASE
            WHEN asa.uom_of_duration::text = 'Time in Years'::text THEN (amd.date_of_commision + asa.duration * '1 year'::interval)::date
            WHEN asa.uom_of_duration::text = 'Time in Days'::text THEN (amd.date_of_commision + asa.duration * '1 day'::interval)::date
            WHEN asa.uom_of_duration::text = 'Time in Months'::text THEN (amd.date_of_commision + asa.duration * '1 mon'::interval)::date
            WHEN asa.uom_of_duration::text = 'TF_yr'::text THEN (amd.date_of_commision + asa.duration * '1 year'::interval)::date
            WHEN asa.uom_of_duration::text = 'TF_mon'::text THEN (amd.date_of_commision + asa.duration * '1 mon'::interval)::date
            ELSE NULL::date
        END AS next_due_date_from_comm, 
    vash.schedule_date::date AS schedule_date, 
        CASE
            WHEN asa.uom_of_duration::text = 'Time in Years'::text THEN (vash.schedule_date + asa.duration * '1 year'::interval)::date
            WHEN asa.uom_of_duration::text = 'Time in Days'::text THEN (vash.schedule_date + asa.duration * '1 day'::interval)::date
            WHEN asa.uom_of_duration::text = 'Time in Months'::text THEN (vash.schedule_date + asa.duration * '1 mon'::interval)::date
            WHEN asa.uom_of_duration::text = 'TF_yr'::text THEN (vash.schedule_date + asa.duration * '1 year'::interval)::date
            WHEN asa.uom_of_duration::text = 'TF_mon'::text THEN (vash.schedule_date + asa.duration * '1 mon'::interval)::date
            ELSE NULL::date
        END AS next_due_date_from_sche
   FROM asset_master_data amd
   LEFT JOIN v_assets_schedule_history vash ON vash.asset_id::text = amd.asset_id::text AND vash.asset_type::text = amd.asset_type::text
   LEFT JOIN asset_schedule_assoc asa ON asa.asset_type::text = amd.asset_type::text AND asa.schedule_code::text = vash.schedule_code::text;

--08
-- View: v_cb_trippings_count_duration_daily

CREATE OR REPLACE VIEW v_cb_trippings_count_duration_daily AS 
 SELECT f.type_of_failure, 
    to_char(f.from_date_time, 'yyyy-mm-dd'::text) AS date1, fac.facility_name, 
    f.asset_id, count(*) AS daily_count, 
    sum(date_part('day'::text, f.thru_date_time - f.from_date_time) * 24::double precision * 60::double precision + date_part('hour'::text, f.thru_date_time - f.from_date_time) * 60::double precision + date_part('minute'::text, f.thru_date_time - f.from_date_time)) AS failure_duration_min, 
    f.nature_of_closure, f.cause_of_failure, 
    date_part('year'::text, f.from_date_time) AS year1, 
    date_part('month'::text, f.from_date_time) AS month1, 
    date_part('week'::text, f.from_date_time) AS week1, f.facility_id, 
    fac.facility_type_id, fac.depot_type, f.sub_station, 
        CASE
            WHEN date_part('month'::text, f.from_date_time) = 1::double precision OR date_part('month'::text, f.from_date_time) = 2::double precision OR date_part('month'::text, f.from_date_time) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (f.from_date_time - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, f.from_date_time)::integer, 100), '99'::text))
            WHEN date_part('month'::text, f.from_date_time) = 4::double precision OR date_part('month'::text, f.from_date_time) = 5::double precision OR date_part('month'::text, f.from_date_time) = 6::double precision OR date_part('month'::text, f.from_date_time) = 7::double precision OR date_part('month'::text, f.from_date_time) = 8::double precision OR date_part('month'::text, f.from_date_time) = 9::double precision OR date_part('month'::text, f.from_date_time) = 10::double precision OR date_part('month'::text, f.from_date_time) = 11::double precision OR date_part('month'::text, f.from_date_time) = 12::double precision THEN (btrim(to_char(date_part('year'::text, f.from_date_time), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (f.from_date_time + '1 year'::interval)::date)::integer, 100), '99'::text))
            ELSE NULL::text
        END AS fy
   FROM failures f
   LEFT JOIN facility fac ON f.facility_id::text = fac.facility_id::text
  WHERE f.type_of_failure::text = 'POWER_FAILURE'::text
  GROUP BY f.asset_id, date_part('year'::text, f.from_date_time), date_part('month'::text, f.from_date_time), date_part('week'::text, f.from_date_time), to_char(f.from_date_time, 'yyyy-mm-dd'::text), 
   CASE
       WHEN date_part('month'::text, f.from_date_time) = 1::double precision OR date_part('month'::text, f.from_date_time) = 2::double precision OR date_part('month'::text, f.from_date_time) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (f.from_date_time - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, f.from_date_time)::integer, 100), '99'::text))
       WHEN date_part('month'::text, f.from_date_time) = 4::double precision OR date_part('month'::text, f.from_date_time) = 5::double precision OR date_part('month'::text, f.from_date_time) = 6::double precision OR date_part('month'::text, f.from_date_time) = 7::double precision OR date_part('month'::text, f.from_date_time) = 8::double precision OR date_part('month'::text, f.from_date_time) = 9::double precision OR date_part('month'::text, f.from_date_time) = 10::double precision OR date_part('month'::text, f.from_date_time) = 11::double precision OR date_part('month'::text, f.from_date_time) = 12::double precision THEN (btrim(to_char(date_part('year'::text, f.from_date_time), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (f.from_date_time + '1 year'::interval)::date)::integer, 100), '99'::text))
       ELSE NULL::text
   END, f.type_of_failure, f.facility_id, fac.facility_name, fac.facility_type_id, fac.depot_type, f.sub_station, f.nature_of_closure, f.cause_of_failure;

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
--10
-- View: v_energy_day_consumption_pf_cpr

CREATE OR REPLACE VIEW v_energy_day_consumption_pf_cpr AS 
 SELECT cur.seq_id, cur.location, 
    cur.energy_reading_date AS cuurent_reading_date, cur.kwh AS cur_kwh, 
    cur.kvah AS cur_kvah, cur.rkvah_lag AS cur_rkvah_lag, 
    cur.rkvah_lead AS cur_rkvah_lead, cur.cmd, cur.rmd, cur.vol_max, 
    cur.vol_min, cur.max_load, cur.max_load_time_hhmm, cur.max_load_time_date, 
    cur.multiplication_fac, pre.energy_reading_date AS previous_reading_date, 
    cur.energy_reading_date - pre.energy_reading_date AS no_of_days_lapsed_pre_reading, 
    pre.kwh AS pre_kwh, pre.kvah AS pre_kvah, pre.rkvah_lag AS pre_rkvah_lag, 
    pre.rkvah_lead AS pre_rkvah_lead, 
    jr.energy_reading_date AS joint_reading_date, 
    cur.energy_reading_date - jr.energy_reading_date AS no_of_days_lapsed_j_reading, 
    jr.kwh AS jr_kwh, jr.kvah AS jr_kvah, jr.rkvah_lag AS jr_rkvah_lag, 
    jr.rkvah_lead AS jr_rkvah_lead, 
        CASE
            WHEN pre.energy_reading_date IS NULL THEN (cur.kwh - cur.m_start_reading) * cur.multiplication_fac
            ELSE (cur.kwh - pre.kwh) * cur.multiplication_fac
        END AS kwh_consumption, 
        CASE
            WHEN pre.energy_reading_date IS NULL THEN (cur.kvah - cur.m_start_reading) * cur.multiplication_fac
            ELSE (cur.kwh - pre.kwh) * cur.multiplication_fac
        END AS kvah_consumption, 
        CASE
            WHEN pre.energy_reading_date IS NULL THEN (cur.rkvah_lag - cur.m_start_reading) * cur.multiplication_fac
            ELSE (cur.rkvah_lag - pre.rkvah_lag) * cur.multiplication_fac
        END AS rkvah_lag_consumption, 
        CASE
            WHEN pre.energy_reading_date IS NULL THEN (cur.rkvah_lead - cur.m_start_reading) * cur.multiplication_fac
            ELSE (cur.rkvah_lead - pre.rkvah_lead) * cur.multiplication_fac
        END AS rkvah_lead_consumption, 
        CASE
            WHEN 
            CASE
                WHEN pre.energy_reading_date IS NULL THEN (cur.kwh - cur.m_start_reading) * cur.multiplication_fac
                ELSE (cur.kwh - pre.kwh) * cur.multiplication_fac
            END = 0::numeric THEN 0::numeric
            ELSE 
            CASE
                WHEN pre.energy_reading_date IS NULL THEN (cur.kvah - cur.m_start_reading) * cur.multiplication_fac
                ELSE (cur.kwh - pre.kwh) * cur.multiplication_fac
            END / 
            CASE
                WHEN pre.energy_reading_date IS NULL THEN (cur.kwh - cur.m_start_reading) * cur.multiplication_fac
                ELSE (cur.kwh - pre.kwh) * cur.multiplication_fac
            END
        END AS pf1, 
        CASE
            WHEN 
            CASE
                WHEN jr.energy_reading_date IS NULL THEN (cur.kwh - cur.m_start_reading) * cur.multiplication_fac
                ELSE (cur.kwh - jr.kwh) * cur.multiplication_fac
            END = 0::numeric THEN 0::numeric
            ELSE 
            CASE
                WHEN jr.energy_reading_date IS NULL THEN (cur.kvah - cur.m_start_reading) * cur.multiplication_fac
                ELSE (cur.kwh - jr.kwh) * cur.multiplication_fac
            END / 
            CASE
                WHEN jr.energy_reading_date IS NULL THEN (cur.kwh - cur.m_start_reading) * cur.multiplication_fac
                ELSE (cur.kwh - jr.kwh) * cur.multiplication_fac
            END
        END AS cpf1
   FROM v_energy_consumption cur
   LEFT JOIN v_energy_consumption pre ON pre.energy_reading_date = (( SELECT max(cur1.energy_reading_date) AS max
      FROM v_energy_consumption cur1
     WHERE cur1.energy_reading_date < cur.energy_reading_date AND cur1.location::text = cur.location::text))
   LEFT JOIN v_energy_consumption jr ON jr.energy_reading_date = (( SELECT max(jr1.energy_reading_date) AS max
   FROM v_energy_consumption jr1
  WHERE jr1.energy_reading_date < cur.energy_reading_date AND jr1.location::text = cur.location::text AND (jr1.joint_meter = 'y'::bpchar OR jr1.joint_meter = 'Y'::bpchar)));

--11
-- View: v_failures_count_duration_daily

CREATE OR REPLACE VIEW v_failures_count_duration_daily AS 
 SELECT f.type_of_failure, count(*) AS daily_count, 
    sum(date_part('day'::text, f.thru_date_time - f.from_date_time) * 24::double precision * 60::double precision + date_part('hour'::text, f.thru_date_time - f.from_date_time) * 60::double precision + date_part('minute'::text, f.thru_date_time - f.from_date_time)) AS delay_time, 
    to_char(f.from_date_time, 'yyyy-mm-dd'::text) AS date1, 
    date_part('year'::text, f.from_date_time) AS year1, 
    date_part('month'::text, f.from_date_time) AS month1, 
    date_part('week'::text, f.from_date_time) AS week1, f.facility_id, 
    fac.facility_name, fac.facility_type_id, fac.depot_type, f.sub_station, 
        CASE
            WHEN date_part('month'::text, f.from_date_time) = 1::double precision OR date_part('month'::text, f.from_date_time) = 2::double precision OR date_part('month'::text, f.from_date_time) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (f.from_date_time - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, f.from_date_time)::integer, 100), '99'::text))
            WHEN date_part('month'::text, f.from_date_time) = 4::double precision OR date_part('month'::text, f.from_date_time) = 5::double precision OR date_part('month'::text, f.from_date_time) = 6::double precision OR date_part('month'::text, f.from_date_time) = 7::double precision OR date_part('month'::text, f.from_date_time) = 8::double precision OR date_part('month'::text, f.from_date_time) = 9::double precision OR date_part('month'::text, f.from_date_time) = 10::double precision OR date_part('month'::text, f.from_date_time) = 11::double precision OR date_part('month'::text, f.from_date_time) = 12::double precision THEN (btrim(to_char(date_part('year'::text, f.from_date_time), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (f.from_date_time + '1 year'::interval)::date)::integer, 100), '99'::text))
            ELSE NULL::text
        END AS fy
   FROM failures f
   LEFT JOIN facility fac ON f.facility_id::text = fac.facility_id::text
  GROUP BY date_part('year'::text, f.from_date_time), date_part('month'::text, f.from_date_time), date_part('week'::text, f.from_date_time), to_char(f.from_date_time, 'yyyy-mm-dd'::text), 
   CASE
       WHEN date_part('month'::text, f.from_date_time) = 1::double precision OR date_part('month'::text, f.from_date_time) = 2::double precision OR date_part('month'::text, f.from_date_time) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (f.from_date_time - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, f.from_date_time)::integer, 100), '99'::text))
       WHEN date_part('month'::text, f.from_date_time) = 4::double precision OR date_part('month'::text, f.from_date_time) = 5::double precision OR date_part('month'::text, f.from_date_time) = 6::double precision OR date_part('month'::text, f.from_date_time) = 7::double precision OR date_part('month'::text, f.from_date_time) = 8::double precision OR date_part('month'::text, f.from_date_time) = 9::double precision OR date_part('month'::text, f.from_date_time) = 10::double precision OR date_part('month'::text, f.from_date_time) = 11::double precision OR date_part('month'::text, f.from_date_time) = 12::double precision THEN (btrim(to_char(date_part('year'::text, f.from_date_time), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (f.from_date_time + '1 year'::interval)::date)::integer, 100), '99'::text))
       ELSE NULL::text
   END, f.type_of_failure, f.facility_id, fac.facility_name, fac.facility_type_id, fac.depot_type, f.sub_station;

---12
-- View: v_failures_count_duration_fy_mon_wk_daily

CREATE OR REPLACE VIEW v_failures_count_duration_fy_mon_wk_daily AS 
 SELECT d.type_of_failure, d.fy, d.month1, d.week1, d.date1, 
    d.cum_delay_time_daily, w.cum_delay_time_weekly, m.cum_delay_time_monthly, 
    fy.cum_delay_time_fy, d.daily_count, w.w_count, m.m_count, fy.fy_count
   FROM ( SELECT v_failures_count_duration_daily.type_of_failure, 
            v_failures_count_duration_daily.fy, 
            sum(v_failures_count_duration_daily.delay_time) AS cum_delay_time_fy, 
            sum(v_failures_count_duration_daily.daily_count) AS fy_count
           FROM v_failures_count_duration_daily
          GROUP BY v_failures_count_duration_daily.type_of_failure, v_failures_count_duration_daily.fy) fy, 
    ( SELECT v_failures_count_duration_daily.type_of_failure, 
            v_failures_count_duration_daily.fy, 
            v_failures_count_duration_daily.month1, 
            sum(v_failures_count_duration_daily.delay_time) AS cum_delay_time_monthly, 
            sum(v_failures_count_duration_daily.daily_count) AS m_count
           FROM v_failures_count_duration_daily
          GROUP BY v_failures_count_duration_daily.type_of_failure, v_failures_count_duration_daily.month1, v_failures_count_duration_daily.fy) m, 
    ( SELECT v_failures_count_duration_daily.type_of_failure, 
            v_failures_count_duration_daily.fy, 
            v_failures_count_duration_daily.month1, 
            v_failures_count_duration_daily.week1, 
            sum(v_failures_count_duration_daily.delay_time) AS cum_delay_time_weekly, 
            sum(v_failures_count_duration_daily.daily_count) AS w_count
           FROM v_failures_count_duration_daily
          GROUP BY v_failures_count_duration_daily.type_of_failure, v_failures_count_duration_daily.month1, v_failures_count_duration_daily.week1, v_failures_count_duration_daily.fy) w, 
    ( SELECT v_failures_count_duration_daily.type_of_failure, 
            v_failures_count_duration_daily.fy, 
            v_failures_count_duration_daily.month1, 
            v_failures_count_duration_daily.week1, 
            v_failures_count_duration_daily.date1, 
            sum(v_failures_count_duration_daily.delay_time) AS cum_delay_time_daily, 
            sum(v_failures_count_duration_daily.daily_count) AS daily_count
           FROM v_failures_count_duration_daily
          GROUP BY v_failures_count_duration_daily.type_of_failure, v_failures_count_duration_daily.month1, v_failures_count_duration_daily.week1, v_failures_count_duration_daily.date1, v_failures_count_duration_daily.fy) d
  WHERE d.fy = w.fy AND d.fy = m.fy AND d.fy = fy.fy AND d.type_of_failure::text = fy.type_of_failure::text AND d.month1 = m.month1 AND d.month1 = w.month1 AND d.type_of_failure::text = m.type_of_failure::text AND d.week1 = w.week1 AND d.type_of_failure::text = w.type_of_failure::text;

---13
-- View: v_monthly_cum_targets

CREATE OR REPLACE VIEW v_monthly_cum_targets AS 
 SELECT asmt.seq_id, asmt.facility_id, asmt.facility_name, 
    asmt.facility_type_id, asmt.depot_type, asmt.schedule_type, asmt.asset_type, 
    asmt.target_jan, asmt.target_feb, asmt.target_mar, asmt.target_apr, 
    asmt.target_may, asmt.target_jun, asmt.target_jul, asmt.target_aug, 
    asmt.target_sep, asmt.target_oct, asmt.target_nov, asmt.target_dec, 
    asmt.target_apr AS cum_target_apr, 
    asmt.target_apr + asmt.target_may AS cum_target_may, 
    asmt.target_apr + asmt.target_may + asmt.target_jun AS cum_target_jun, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul AS cum_target_jul, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug AS cum_target_aug, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep AS cum_target_sep, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct AS cum_target_oct, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov AS cum_target_nov, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec AS cum_target_dec, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec + asmt.target_jan AS cum_target_jan, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec + asmt.target_jan + asmt.target_feb AS cum_target_feb, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec + asmt.target_jan + asmt.target_feb + asmt.target_mar AS cum_target_mar, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec + asmt.target_jan + asmt.target_feb + asmt.target_mar AS total_year_target, 
    asmt.year, asmt.fy
   FROM v_asset_monthly_targets asmt;

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

--15
-- View: v_assets_schedule_completed_targets

CREATE OR REPLACE VIEW v_assets_schedule_completed_targets AS 
 SELECT to_char(vasht.schedule_date, 'dd-Mon-yyyy'::text) AS schedule_date, 
    vasht.asset_type, vasht.schedule_code, count(*) AS sch_done_count, 
    vasht.month_target, vasht.cum_month_target, vasht.total_year_target, 
    vasht.week1, vasht.month1, vasht.year1, vasht.fy, vasht.facility_id, 
    vasht.facility_name, vasht.facility_type_id, vasht.depot_type
   FROM v_assets_schedule_history_targets vasht
  GROUP BY vasht.fy, vasht.schedule_date, vasht.asset_type, vasht.schedule_code, vasht.facility_id, vasht.facility_name, vasht.facility_type_id, vasht.depot_type, vasht.week1, vasht.month1, vasht.year1, vasht.month_target, vasht.cum_month_target, vasht.total_year_target;

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

--17
-- View: v_assets_schedule_counts

CREATE OR REPLACE VIEW v_assets_schedule_counts AS 
 SELECT to_char(vash.schedule_date, 'dd-Mon-yyyy'::text)::date AS schedule_date, 
    vash.asset_type, vash.schedule_code, count(*) AS sch_done_count, vash.fy, 
    vash.facility_id, vash.facility_name, vash.facility_type_id, 
    vash.depot_type, vash.month1, vash.year1, vash.week1
   FROM v_assets_schedule_history vash, v_monthly_cum_targets vmct
  WHERE vmct.asset_type::text = vash.asset_type::text AND vmct.schedule_type::text = vash.schedule_code::text AND vmct.facility_id::text = vash.facility_id::text AND vmct.fy = vash.fy
  GROUP BY vash.fy, vash.month1, vash.year1, vash.week1, vash.schedule_date, vash.asset_type, vash.schedule_code, vash.facility_id, vash.facility_name, vash.facility_type_id, vash.depot_type;

--18
-- View: v_asset_monthly_targets_div

CREATE OR REPLACE VIEW v_asset_monthly_targets_div AS 
 SELECT vamt.asset_type, vamt.schedule_type, vamt.year AS fy, 
    sum(vamt.target_jan) AS div_target_jan, 
    sum(vamt.target_feb) AS div_target_feb, 
    sum(vamt.target_mar) AS div_target_mar, 
    sum(vamt.target_apr) AS div_target_apr, 
    sum(vamt.target_may) AS div_target_may, 
    sum(vamt.target_jun) AS div_target_jun, 
    sum(vamt.target_jul) AS div_target_jul, 
    sum(vamt.target_aug) AS div_target_aug, 
    sum(vamt.target_sep) AS div_target_sep, 
    sum(vamt.target_oct) AS div_target_oct, 
    sum(vamt.target_nov) AS div_target_nov, 
    sum(vamt.target_dec) AS div_target_dec, 
    sum(vamt.cum_target_jan) AS div_cum_target_jan, 
    sum(vamt.cum_target_feb) AS div_cum_target_feb, 
    sum(vamt.cum_target_mar) AS div_cum_target_mar, 
    sum(vamt.cum_target_apr) AS div_cum_target_apr, 
    sum(vamt.cum_target_may) AS div_cum_target_may, 
    sum(vamt.cum_target_jun) AS div_cum_target_june, 
    sum(vamt.cum_target_jul) AS div_cum_target_jul, 
    sum(vamt.cum_target_aug) AS div_cum_target_aug, 
    sum(vamt.cum_target_sep) AS div_cum_target_sep, 
    sum(vamt.cum_target_oct) AS div_cum_target_oct, 
    sum(vamt.cum_target_nov) AS div_cum_target_nov, 
    sum(vamt.cum_target_dec) AS div_cum_target_dec, 
    sum(vamt.total_year_target) AS div_target_year
   FROM v_monthly_cum_targets vamt
  GROUP BY vamt.asset_type, vamt.schedule_type, vamt.year;

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

--21  
-- View: v_subdivisions

CREATE OR REPLACE VIEW v_subdivisions AS 
 SELECT fsd.facility_name AS sub_division, f.facility_name AS depot_name, 
    f.facility_id, fa.facility_id_to, f.facility_type_id, f.depot_type
   FROM facility_assoc fa
   LEFT JOIN facility f ON f.facility_id::text = fa.facility_id_to::text
   LEFT JOIN facility fsd ON fsd.facility_id::text = fa.facility_id::text
  WHERE fa.facility_assoc_type_id::text = 'SUBDIVISION_UNIT'::text;

--22
-- View: v_power_block_switch_details

CREATE OR REPLACE VIEW v_power_block_switch_details AS 
 SELECT pb.pb_operation_seq_id, pb.created_date AS pb_date, pb.req_department, 
    pb.facility_id, fac.facility_name, fac.facility_type_id, fac.depot_type, 
    pb.type_of_operation, pb.elementary_section_code AS es_sec_subsec_code,  shadow_block,
    pb.section AS pb_over_es_sec_subsec, pb.line, pb.line2,  
    pb.pb_requested_from_date_time, pb.pb_requested_thru_date_time, 
    pb.pb_granted_from_date_time, pb.pb_granted_thru_date_time, 
    pb.ptw_availed_from_date_time, pb.ptw_availed_thru_date_time, 
    pb.tpc_no_ptw_issue, pb.field_no_ptw_issue, pb.tpc_no_ptw_return, 
    pb.field_no_ptw_return, pb.req_period, pb.reqn_by,      pb.post, 
    pb.switching_station, pb.switching_equipment, pb.equipment_to_work, 
    pb.special_remarks, pb.remarks, pb.supervisor_incharge, pb.current_status, tpc_board , schedule, purpose , staff_to_work ,
    smh.seq_id, smh.pb_operation_seq_id AS smh_pb_operation_seq_id, 
    smh.io_location AS smh_io_location, smh.io_type AS smh_io_type, 
    smh.io_opened_by AS smh_io_opened_by, 
    smh.io_opened_date_time AS smh_io_opened_date_time, 
    smh.tpc_no_io_open AS smh_tpc_no_io_open, 
    smh.field_no_io_open AS smh_field_no_io_open, 
    smh.io_opened_date_time_done AS smh_io_opened_date_time_done, 
    smh.tpc_no_io_open_done AS smh_tpc_no_io_open_done, 
    smh.field_no_io_open_done AS smh_field_no_io_open_done, 
    smh.io_closed_by AS smh_io_closed_by, 
    smh.io_closed_date_time AS smh_io_closed_date_time, 
    smh.tpc_no_io_close AS smh_tpc_no_io_close, 
    smh.field_no_io_close AS smh_field_no_io_close, 
    smh.io_closed_date_time_done AS smh_io_closed_date_time_done, 
    smh.tpc_no_io_close_done AS smh_tpc_no_io_close_done, 
    smh.field_no_io_close_done AS smh_field_no_io_close_done, 
    smh.is_field_operated, 
    date_part('day'::text, smh.io_closed_date_time_done - smh.io_opened_date_time_done) * 24::double precision * 60::double precision + date_part('hour'::text, smh.io_closed_date_time_done - smh.io_opened_date_time_done) * 60::double precision + date_part('minute'::text, smh.io_closed_date_time_done - smh.io_opened_date_time_done) AS effective_pb_duration, 
        CASE
            WHEN (date_part('day'::text, smh.io_closed_date_time_done - smh.io_opened_date_time_done) * 24::double precision * 60::double precision + date_part('hour'::text, smh.io_closed_date_time_done - smh.io_opened_date_time_done) * 60::double precision + date_part('minute'::text, smh.io_closed_date_time_done - smh.io_opened_date_time_done)) > 0::double precision THEN 'ISSUED'::text
            ELSE 'NOT_ISSUED'::text
        END AS issued_not_issued
   FROM power_blocks pb, facility fac, switch_maintenence_history smh
  WHERE pb.facility_id::text = fac.facility_id::text AND pb.pb_operation_seq_id::text = smh.pb_operation_seq_id::text;

--23

--view: v_power_block_depot_daily_summary;

-- get details of PB issued_or not_issued  for count of PB and issued duration in min daywise, depot, status (active/deleted), req dept (   ) wise , week/month/Qtr/year/FY etc, 
-- This  is used for next aggregation at depot level or other status for any time period of week/month/Qtr/year/FY etc, 
CREATE OR REPLACE VIEW v_power_block_depot_daily_summary AS
  SELECT v_power_block_switch_details.req_department, 
    v_power_block_switch_details.facility_name, 
    v_power_block_switch_details.depot_type, 
    v_power_block_switch_details.current_status, count(*) AS cnt, 
    sum(v_power_block_switch_details.req_period::integer) AS total_min_asked, 
    sum(v_power_block_switch_details.effective_pb_duration) AS pb_issued_min, 
    v_power_block_switch_details.issued_not_issued, 
    to_char(v_power_block_switch_details.pb_date, 'yyyy-mm-dd'::text) AS date1, 
    date_part('year'::text, v_power_block_switch_details.pb_date) AS year1, 
    date_part('month'::text, v_power_block_switch_details.pb_date) AS month1, 
    date_part('week'::text, v_power_block_switch_details.pb_date) AS week1, 
    v_power_block_switch_details.line, 
        CASE
            WHEN date_part('month'::text, v_power_block_switch_details.pb_date) = 1::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 2::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (v_power_block_switch_details.pb_date - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, v_power_block_switch_details.pb_date)::integer, 100), '99'::text))
            WHEN date_part('month'::text, v_power_block_switch_details.pb_date) = 4::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 5::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 6::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 7::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 8::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 9::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 10::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 11::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 12::double precision THEN (btrim(to_char(date_part('year'::text, v_power_block_switch_details.pb_date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (v_power_block_switch_details.pb_date + '1 year'::interval)::date)::integer, 100), '99'::text))
            ELSE NULL::text
        END AS fy
   FROM v_power_block_switch_details
  GROUP BY v_power_block_switch_details.req_department, v_power_block_switch_details.depot_type, v_power_block_switch_details.line, v_power_block_switch_details.current_status, v_power_block_switch_details.facility_name, v_power_block_switch_details.issued_not_issued, v_power_block_switch_details.pb_date, 
        CASE
            WHEN date_part('month'::text, v_power_block_switch_details.pb_date) = 1::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 2::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (v_power_block_switch_details.pb_date - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, v_power_block_switch_details.pb_date)::integer, 100), '99'::text))
            WHEN date_part('month'::text, v_power_block_switch_details.pb_date) = 4::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 5::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 6::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 7::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 8::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 9::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 10::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 11::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 12::double precision THEN (btrim(to_char(date_part('year'::text, v_power_block_switch_details.pb_date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (v_power_block_switch_details.pb_date + '1 year'::interval)::date)::integer, 100), '99'::text))
            ELSE NULL::text
        END, date_part('year'::text, v_power_block_switch_details.pb_date), date_part('month'::text, v_power_block_switch_details.pb_date), date_part('week'::text, v_power_block_switch_details.pb_date), to_char(v_power_block_switch_details.pb_date, 'yyyy-mm-dd'::text);

--24
-- View: v_switch_maintenence_history_message_final


CREATE OR REPLACE VIEW v_switch_maintenence_history_message_final AS 
 SELECT sw.seq_id, pb.created_date, pb.tpc_board, pb.facility_id, 
    fac.facility_name, fac.facility_type_id, fac.depot_type, 
    pb.type_of_operation, pb.section, pb.current_status, sw.pb_operation_seq_id, 
    sw.io_location, sw.io_type, sw.io_opened_by, sw.open_time_lapse, 
    sw.open_done_time_lapse, sw.close_time_lapse, sw.close_done_time_lapse, 
        CASE
            WHEN swa.io_opened_date_time IS NULL THEN 
            CASE
                WHEN sw.open_time_lapse IS NULL THEN sw.io_opened_date_time
                ELSE sw.io_opened_date_time + ((sw.open_time_lapse::text || ' Minutes'::text)::interval)
            END
            ELSE swa.io_opened_date_time
        END AS swf_io_opened_date_time, 
        CASE
            WHEN swa.tpc_no_io_open IS NULL THEN sw.tpc_no_io_open::text
            ELSE swa.tpc_no_io_open::text || '*'::text
        END AS swf_tpc_no_io_open, 
        CASE
            WHEN swa.field_no_io_open IS NULL THEN sw.field_no_io_open::text
            ELSE swa.field_no_io_open::text || '*'::text
        END AS swf_field_no_io_open, 
        CASE
            WHEN swa.io_opened_date_time_done IS NULL THEN 
            CASE
                WHEN sw.open_done_time_lapse IS NULL THEN sw.io_opened_date_time_done
                ELSE sw.io_opened_date_time_done + ((sw.open_done_time_lapse::text || ' Minutes'::text)::interval)
            END
            ELSE swa.io_opened_date_time_done
        END AS swf_io_opened_date_time_done, 
        CASE
            WHEN swa.tpc_no_io_open_done IS NULL THEN sw.tpc_no_io_open_done::text
            ELSE swa.tpc_no_io_open_done::text || '*'::text
        END AS swf_tpc_no_io_open_done, 
        CASE
            WHEN swa.field_no_io_open_done IS NULL THEN sw.field_no_io_open_done::text
            ELSE swa.field_no_io_open_done::text || '*'::text
        END AS swf_field_no_io_open_done, 
        CASE
            WHEN swa.io_closed_date_time IS NULL THEN 
            CASE
                WHEN sw.close_time_lapse IS NULL THEN sw.io_closed_date_time
                ELSE sw.io_closed_date_time + ((sw.close_time_lapse::text || ' Minutes'::text)::interval)
            END
            ELSE swa.io_closed_date_time
        END AS swf_io_closed_date_time, 
        CASE
            WHEN swa.tpc_no_io_close IS NULL THEN sw.tpc_no_io_close::text
            ELSE swa.tpc_no_io_close::text || '*'::text
        END AS swf_tpc_no_io_close, 
        CASE
            WHEN swa.field_no_io_close IS NULL THEN sw.field_no_io_close::text
            ELSE swa.field_no_io_close::text || '*'::text
        END AS swf_field_no_io_close, 
        CASE
            WHEN swa.io_closed_date_time_done IS NULL THEN 
            CASE
                WHEN sw.close_done_time_lapse IS NULL THEN sw.io_closed_date_time_done
                ELSE sw.io_closed_date_time_done + ((sw.close_done_time_lapse::text || ' Minutes'::text)::interval)
            END
            ELSE swa.io_closed_date_time_done
        END AS swf_io_closed_date_time_done, 
        CASE
            WHEN swa.tpc_no_io_close_done IS NULL THEN sw.tpc_no_io_close_done::text
            ELSE swa.tpc_no_io_close_done::text || '*'::text
        END AS swf_tpc_no_io_close_done, 
        CASE
            WHEN swa.field_no_io_close_done IS NULL THEN sw.field_no_io_close_done::text
            ELSE swa.field_no_io_close_done::text || '*'::text
        END AS swf_field_no_io_close_done, 
    sw.io_closed_by, sw.is_field_operated, 
        CASE
            WHEN (date_part('day'::text, sw.io_closed_date_time_done - sw.io_opened_date_time_done) * 24::double precision * 60::double precision + date_part('hour'::text, sw.io_closed_date_time_done - sw.io_opened_date_time_done) * 60::double precision + date_part('minute'::text, sw.io_closed_date_time_done - sw.io_opened_date_time_done)) > 0::double precision THEN 'ISSUED'::text
            ELSE 'NOT_ISSUED'::text
        END AS issued_not_issued, 
    sw.last_updated_stamp AS sw_last_updated_stamp, 
    sw.last_updated_tx_stamp AS sw_last_updated_tx_stamp, 
    sw.created_stamp AS sw_created_stamp, 
    sw.created_tx_stamp AS sw_created_tx_stamp, swa.amendment_seq_id, 
    swa.io_opened_by AS swa_io_opened_by, swa.io_closed_by AS swa_io_closed_by, 
    swa.last_updated_stamp AS swa_last_updated_stamp, 
    swa.last_updated_tx_stamp AS swa_last_updated_tx_stamp, 
    swa.created_stamp AS swa_created_stamp, 
    swa.created_tx_stamp AS swa_created_tx_stamp, 
        CASE
            WHEN swa.io_opened_date_time IS NOT NULL THEN 'true'::text
            ELSE 'false'::text
        END AS io_opened_date_time_amended, 
        CASE
            WHEN swa.io_opened_date_time_done IS NOT NULL THEN 'true'::text
            ELSE 'false'::text
        END AS io_opened_date_time_done_amended, 
        CASE
            WHEN swa.io_closed_date_time IS NOT NULL THEN 'true'::text
            ELSE 'false'::text
        END AS io_closed_date_time_amended, 
        CASE
            WHEN swa.io_closed_date_time_done IS NOT NULL THEN 'true'::text
            ELSE 'false'::text
        END AS io_closed_date_time_done_amended
   FROM switch_maintenence_history sw
   LEFT JOIN switch_maintenence_history_amendment swa ON (sw.seq_id::text = swa.seq_id::text  and swa.delete ='false'), 
    power_blocks pb, facility fac
  WHERE pb.facility_id::text = fac.facility_id::text AND pb.pb_operation_seq_id::text = sw.pb_operation_seq_id::text;

--25
-- View: v_power_blocks_amendment_message_final


CREATE OR REPLACE VIEW v_power_blocks_amendment_message_final AS 
 SELECT pb.pb_operation_seq_id, pb.created_date, fsw.field_operated_switch_list, 
    pb.facility_id, fac.facility_name, fac.facility_type_id, fac.depot_type, 
    pb.type_of_operation, pb.shadow_block, pb.elementary_section_code, grant_period,
       pb.section, pb.line, pb.line2, 
        CASE
            WHEN pb.line IS NULL OR pb.line2 IS NULL THEN 'UP/DN/UD/UP&DN'::text
            ELSE (pb.line::text || '/'::text) || pb.line2::text
        END AS pb_section_lines, 
    pb.req_department, pb.req_period, pb.reqn_by, 
        CASE
            WHEN pba.ptw_availed_from_date_time IS NULL THEN pb.ptw_availed_from_date_time
            ELSE pba.ptw_availed_from_date_time
        END AS ptw_availed_from_date_time, 
        CASE
            WHEN pba.ptw_availed_from_date_time IS NULL THEN 
                    case when pb.ptw_availed_from_date_time is null then pb.ptw_availed_from_date_time
                    else pb.ptw_availed_from_date_time+(grant_period::integer || ' Minutes'::text)::interval
                     end 
            ELSE 
                    case when  pba.ptw_availed_from_date_time is not null then  
                     pba.ptw_availed_from_date_time+(grant_period::integer || ' Minutes'::text)::interval 
                    else  pba.ptw_availed_from_date_time end 
            END AS ptw_availed_till_date_time, 
        CASE
            WHEN pba.tpc_no_ptw_issue IS NULL THEN pb.tpc_no_ptw_issue::text
            ELSE pba.tpc_no_ptw_issue::text || '*'::text
        END AS tpc_no_ptw_issue, 
        CASE
            WHEN pba.field_no_ptw_issue IS NULL THEN pb.field_no_ptw_issue::text
            ELSE pba.field_no_ptw_issue::text || '*'::text
        END AS field_no_ptw_issue, 
        CASE
            WHEN pba.ptw_availed_thru_date_time IS NULL THEN pb.ptw_availed_thru_date_time
            ELSE pba.ptw_availed_thru_date_time
        END AS ptw_availed_thru_date_time, 
        CASE
            WHEN pba.tpc_no_ptw_return IS NULL THEN pb.tpc_no_ptw_return::text
            ELSE pba.tpc_no_ptw_return::text || '*'::text
        END AS tpc_no_ptw_return, 
        CASE
            WHEN pba.field_no_ptw_return IS NULL THEN pb.field_no_ptw_return::text
            ELSE pba.field_no_ptw_return::text || '*'::text
        END AS field_no_ptw_return, 
    pb.purpose, pb.pb_requested_from_date_time, pb.pb_requested_thru_date_time, 
    pb.pb_granted_from_date_time, pb.pb_granted_thru_date_time, 
    pb.staff_to_work, pb.post, pb.switching_station, pb.switching_equipment, 
    pb.equipment_to_work, pb.special_remarks, pb.remarks, pb.tpc_board, 
    pb.schedule, pb.supervisor_incharge, pb.current_status, pb.created_on, 
    pb.created_by, pb.last_updated_stamp, pb.last_updated_tx_stamp, 
    pb.created_stamp, pb.created_tx_stamp, 
    pba.pb_operation_seq_id AS pba_operation_seq_id, pba.pb_amendment_seq_id, 
    pba.created_by AS pba_created_by, pba.updated_by AS pba_updated_by, 
    pba.delete AS pba_delete, pba.last_updated_stamp AS pba_last_updated_stamp, 
    pba.last_updated_tx_stamp AS pba_last_updated_tx_stamp, 
    pba.created_stamp AS pba_created_stamp, 
    pba.created_tx_stamp AS pba_created_tx_stamp, 
        CASE
            WHEN pba.ptw_availed_thru_date_time IS NOT NULL THEN 'true'::text
            ELSE 'false'::text
        END AS ptw_availed_thru_date_time_amended, 
        CASE
            WHEN pba.ptw_availed_from_date_time IS NOT NULL THEN 'true'::text
            ELSE 'false'::text
        END AS ptw_availed_from_date_time_amended
   FROM power_blocks pb
   LEFT JOIN power_blocks_amendment pba ON (pba.pb_operation_seq_id::text = pb.pb_operation_seq_id::text and pba.delete ='false' )
   LEFT JOIN ( SELECT pb_1.pb_operation_seq_id, pb_1.ptw_availed_thru_date_time, 
       pb_1.ptw_availed_from_date_time, sw.field_operated_switch_list
      FROM power_blocks pb_1
   LEFT JOIN ( SELECT switch_maintenence_history.pb_operation_seq_id, 
               switch_maintenence_history.is_field_operated, 
               string_agg(switch_maintenence_history.io_location::text, ', '::text) AS field_operated_switch_list
              FROM switch_maintenence_history
             WHERE switch_maintenence_history.is_field_operated::text = 'true'::text OR switch_maintenence_history.is_field_operated::text = 'TRUE'::text
             GROUP BY switch_maintenence_history.pb_operation_seq_id, switch_maintenence_history.is_field_operated) sw ON pb_1.pb_operation_seq_id::text = sw.pb_operation_seq_id::text) fsw ON fsw.pb_operation_seq_id::text = pb.pb_operation_seq_id::text, 
    facility fac
  WHERE pb.facility_id::text = fac.facility_id::text;
  
--26
-- View: v_product_qty_uom_params  

create view v_product_qty_uom_params as

select product_id , quantity_uom_id ,

case quantity_uom_id

when 'WT_kg' then 3

when 'OTH_no' then 0

when 'LEN_m' then 3

when 'LEN_km' then 3

when 'LEN_ft' then 2

when 'LEN_yd' then 2

when 'VLIQ_L' then 3

end as decimals_no,

case quantity_uom_id

when 'WT_kg' then 'Kilograms ' 

when 'OTH_no' then 'NOs '

when 'LEN_m' then ' Meters '

when 'LEN_km' then 'KM '

when 'LEN_ft' then ' Feet '

when 'LEN_yd' then ' Yards '

when 'VLIQ_L' then ' Liters '

end as unit1 ,

case quantity_uom_id

when 'WT_kg' then 'Grams '

when 'OTH_no' then ' '

when 'LEN_m' then 'centimeters '

when 'LEN_km' then ' Meters '

when 'LEN_ft' then ' Inches '

when 'LEN_yd' then ' Feet '

when 'VLIQ_L' then ' Milli liters '

end as subunit1

from product

----27

	CREATE VIEW  v_cb_trip_daily_eqp_cnt AS
		
	 	SELECT f.type_of_failure,
	    to_char(f.from_date_time, 'yyyy-mm-dd'::text) AS date1,
	    fac.facility_name,
	    f.equipment,
	    f.asset_id,
	    count(*) AS daily_count,
	    ( SELECT count(*) AS tripped_identified_fault
	           FROM failures f1
	          WHERE to_char(f1.from_date_time, 'yyyy-mm-dd'::text) >= '1990-01-01'::text AND 
	          f1.type_of_failure::text = 'POWER_FAILURE'::text AND (f1.tripped_identified_fault::text = 'false'::text OR f1.tripped_identified_fault IS NULL) AND f1.sub_station::text = f.sub_station::text 
	          AND f1.equipment::text = f.equipment::text) AS transient_trip_count,
	    ( SELECT count(*) AS tripped_identified_fault
	           FROM failures f1
	          WHERE to_char(f1.from_date_time, 'yyyy-mm-dd'::text) >= '1990-01-01'::text AND 
	          f1.type_of_failure::text = 'POWER_FAILURE'::text AND f1.tripped_identified_fault::text = 'true'::text AND f1.sub_station::text = f.sub_station::text AND f1.equipment::text = f.equipment::text) AS identified_trip_count,
	    sum(date_part('day'::text, f.thru_date_time - f.from_date_time) * 24::double precision * 60::double precision + date_part('hour'::text, f.thru_date_time - f.from_date_time) * 60::double precision + date_part('minute'::text, f.thru_date_time - f.from_date_time)) AS failure_duration_min,
	    to_char(f.created_date, 'YYYY'::text) AS year,
	    to_char(f.created_date, 'MM'::text) AS month,
	    date_part('year'::text, f.from_date_time) AS year1,
	    date_part('month'::text, f.from_date_time) AS month1,
	    date_part('week'::text, f.from_date_time) AS week1,
	    f.facility_id,
	    fac.facility_type_id,
	    fac.depot_type,
	    f.sub_station,
	        CASE
	            WHEN date_part('month'::text, f.from_date_time) = 1::double precision OR date_part('month'::text, f.from_date_time) = 2::double precision OR date_part('month'::text, f.from_date_time) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (f.from_date_time - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, f.from_date_time)::integer, 100), '99'::text))
	            WHEN date_part('month'::text, f.from_date_time) = 4::double precision OR date_part('month'::text, f.from_date_time) = 5::double precision OR date_part('month'::text, f.from_date_time) = 6::double precision OR date_part('month'::text, f.from_date_time) = 7::double precision OR date_part('month'::text, f.from_date_time) = 8::double precision OR date_part('month'::text, f.from_date_time) = 9::double precision OR date_part('month'::text, f.from_date_time) = 10::double precision OR date_part('month'::text, f.from_date_time) = 11::double precision OR date_part('month'::text, f.from_date_time) = 12::double precision THEN (btrim(to_char(date_part('year'::text, f.from_date_time), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (f.from_date_time + '1 year'::interval)::date)::integer, 100), '99'::text))
	            ELSE NULL::text
	        END AS fy
	   FROM failures f
	     LEFT JOIN facility fac ON f.sub_station::text = fac.facility_id::text
	  WHERE to_char(f.from_date_time, 'yyyy-mm-dd'::text) >= '1990-01-01'::text AND
	   f.type_of_failure::text = 'POWER_FAILURE'::text
	  GROUP BY f.asset_id, (date_part('year'::text, f.from_date_time)), (date_part('month'::text, f.from_date_time)), (date_part('week'::text, f.from_date_time)), (to_char(f.from_date_time, 'yyyy-mm-dd'::text)), (
	        CASE
	            WHEN date_part('month'::text, f.from_date_time) = 1::double precision OR date_part('month'::text, f.from_date_time) = 2::double precision OR date_part('month'::text, f.from_date_time) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (f.from_date_time - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, f.from_date_time)::integer, 100), '99'::text))
	            WHEN date_part('month'::text, f.from_date_time) = 4::double precision OR date_part('month'::text, f.from_date_time) = 5::double precision OR date_part('month'::text, f.from_date_time) = 6::double precision OR date_part('month'::text, f.from_date_time) = 7::double precision OR date_part('month'::text, f.from_date_time) = 8::double precision OR date_part('month'::text, f.from_date_time) = 9::double precision OR date_part('month'::text, f.from_date_time) = 10::double precision OR date_part('month'::text, f.from_date_time) = 11::double precision OR date_part('month'::text, f.from_date_time) = 12::double precision THEN (btrim(to_char(date_part('year'::text, f.from_date_time), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (f.from_date_time + '1 year'::interval)::date)::integer, 100), '99'::text))
	            ELSE NULL::text
	        END), f.type_of_failure, f.facility_id, fac.facility_name, fac.facility_type_id, fac.depot_type, f.sub_station, f.equipment, (to_char(f.created_date, 'YYYY'::text)), (to_char(f.created_date, 'MM'::text));
	
	
   
   --28 View: v_asset_schedule_activity_record
   
	create view v_asset_schedule_activity_record AS
  	SELECT row_number() OVER () AS s_no,
     asar.asset_id, amd.asset_type,
     asar.schedule_code,  asar.schedule_date,
     asar.schedule_actual_date,  asar.status as status,
     ash.status as ash_status,  asar.details_of_maint as details_of_maint,
     ash.details_of_maint as ash_details_of_maint,     asar.done_by as done_by,
     ash.done_by as ash_done_by,     asar.remarks as remarks,
     ash.remarks as ash_remarks,     --100 AS kilometer,
     
     kilometer AS kilometer,  amd.position_id,  amd.location_position,
     amd.capacity_rating as capacity,  amd.part1 AS span,

     asar.status AS measure_entry_status, f.facility_name,  f.depot_type,

     amd.make,  amd.model,  amd.oem_serial,  amd.section,

     amd.date_of_manufacture, amd.date_of_commision,  amd.stagger,

     amd.stagger1, amd.stagger2,  amd.stagger3,   amd.stay1_insulator_make,

     amd.stay2_insulator_make,  amd.stay3_insulator_make,  amd.bracket1_insulator_make,

     amd.bracket2_insulator_make,  amd.bracket3_insulator_make,  amd.stag1_ton9_insulator_make,

     amd.stag2_ton9_insulator_make,   amd.stag3_ton9_insulator_make,  amd.structure AS type_of_mast,

     amd.implantation,     amd.line,

    asar.m1,	asar.m2,	asar.m3,	asar.m4,	asar.m5,	asar.m6,	asar.m7,	asar.m8,
	asar.m9,	asar.m10,	asar.m11,	asar.m12,	asar.m13,	asar.m14,	asar.m15,	asar.m16,
	asar.m17,	asar.m18,	asar.m19,
	asar.m20,	asar.m21,	asar.m22,	asar.a1,	asar.a2,	asar.a3,	asar.a4,	asar.a5,
	asar.a6,	asar.a7,	asar.a8,	asar.a9,	asar.a10,	asar.a11,	asar.a12,	asar.a13,
	asar.a14,	asar.a15,	asar.a16,	asar.a17,	asar.a18,	asar.a19,	asar.a20,	asar.a21,
	asar.a22,	asar.m23,	asar.m24,	asar.m25,	asar.m26,	asar.m27,	asar.m28,	asar.m29,
	asar.m30,	asar.m31,	asar.m32,	asar.m33,	asar.m34,	asar.m35,	asar.m36,	asar.m37,
	asar.m38,	asar.m39,	asar.m40,	asar.m41,	asar.m42,	asar.m43,	asar.m44,	asar.m45,
	asar.m46,	asar.m47,	asar.m48,	asar.m49,	asar.m50,	asar.a23,	asar.a24,	asar.a25,
	asar.a26,	asar.a27,	asar.a28,	asar.a29,	asar.a30,	asar.a31,	asar.a32,	asar.a33,
	asar.a34,	asar.a35,	asar.a36,	asar.a37,	asar.a38,	asar.a39,	asar.a40,	asar.a41,
	asar.a42,	asar.a43,	asar.a44,	asar.a45,	asar.a46,	asar.a47,	asar.a48,	asar.a49,
	asar.a50,	asar.mma1,	asar.mma2,	asar.mma3,	asar.mma4,	asar.mma5,	asar.mma6,	asar.mma7,
	asar.mma8,	asar.mma9,	asar.mma10,	asar.m51,	asar.m52,	asar.m53,	asar.m54,	asar.m55,
	asar.m56,	asar.m57,	asar.m58,	asar.m59,	asar.m60,	asar.m61,	asar.m62,	asar.m63,
	asar.m64,	asar.m65,	asar.m66,	asar.m67,	asar.m68,	asar.m69,	asar.m70,	asar.a51,
	asar.a52,	asar.a53,	asar.a54,	asar.a55,	asar.a56,	asar.a57,	asar.a58,	asar.a59,
	asar.a60,	asar.a61,	asar.a62,	asar.a63,	asar.a64,	asar.a65,	asar.a66,	asar.a67,
	asar.a68,	asar.a69,	asar.a70,	asar.a71,	asar.a72,	asar.a73,	asar.a74,	asar.a75,
	asar.a76,	asar.a77,	asar.a78,	asar.a79,	asar.a80,	asar.a81,	asar.a82,	asar.a83,
	asar.a84,	asar.a85,	asar.a86,	asar.a87,	asar.a88,	asar.a89,	asar.a90,	asar.a91,
	asar.a92,	asar.a93,	asar.a94,	asar.a95,	asar.a96,	asar.a97,	asar.a98,	asar.a99,
	asar.a100,	asar.a101,	asar.a102,	asar.a103,	asar.a104,	asar.a105,	asar.a106,	asar.a107,
	asar.a108,	asar.a109,	asar.a110,	asar.a111,	asar.a112,	asar.a113,	asar.a114,	asar.a115,
	asar.a116,	asar.a117,	asar.a118,	asar.a119,	asar.a120,	asar.a121,	asar.a122,	asar.a123,
	asar.a124,	asar.a125,	asar.a126,	asar.a127,	asar.a128,	asar.a129,	asar.a130,	f.facility_id
    FROM asset_schedule_activity_record asar,
   asset_master_data amd,
   facility f,
   assets_schedule_history ash
   WHERE asar.asset_id::text = amd.asset_id::text AND

asar.asset_type::text = amd.asset_type::text AND 
asar.facility_id::text= f.facility_id::text AND
 asar.facility_id::text = amd.facility_id::text

AND ash.seq_id::text = asar.asset_schedule_history_id::text;

ALTER TABLE public.v_asset_schedule_activity_record

   OWNER TO postgres;
  
  --29 function: DROP FUNCTION public.asset_schedule_graph(text);

CREATE  FUNCTION public.asset_schedule_graph(IN depotid text)
  RETURNS TABLE(sno bigint, des_cription text, t_population integer, ty_target integer, tt_month integer, t_progress integer, os_countt integer, f_quency text, a_type character varying, s_code character varying, progress integer, percentage_op numeric, facilityid text, t_y_targer_facid character varying, at_st text, fac_id character varying) AS
$BODY$

BEGIN
Return query 
Select S_No,   description , Total_Population, total_year_target::INTEGER, target_till_month::INTEGER , Total_progress::INTEGER,  os_count::INTEGER, 
 frequency, a.Asset_type , Schedule_code, 
case when progres is null then 0 else progress end progress,
 case when PErcentage_of_progress is null then 0 else PErcentage_of_progress end PErcentage_of_progress,a.facility_id , ty_tgt.facility_id t_y_target_facility_id, 
 osc.at_st, osc.facility_id
 --  
From
(
select row_number() OVER (ORDER BY (Asset_type ,Schedule_code) ) S_No, AT_ST_description  description , Total_Population::INTEGER, 
--apr_cnt::INTEGER, may_cnt::INTEGER, juN_cnt::INTEGER, jul_cnt::INTEGER, aug_cnt::INTEGER, sep_cnt::INTEGER,
--oct_cnt::INTEGER, nov_cnt::INTEGER, dec_cnt::INTEGER, jan_cnt::INTEGER, feb_cnt::INTEGER, mar_cnt::INTEGER,
(apr_cnt+may_cnt+jun_cnt+jul_cnt+aug_cnt+sep_cnt+oct_cnt+ nov_cnt+dec_cnt+ jan_cnt +feb_cnt+mar_cnt)::INTEGER as Total_progress,

avg_cum_month_target_till_month::INTEGER target_till_month ,
frequency, Asset_type , Schedule_code,
tprog::integer  progres, depotid::text facility_id,

CASE when (avg_cum_month_target_till_month is null or avg_cum_month_target_till_month = 0 ) 
then 0 else ROUND((tprog/avg_cum_month_target_till_month*100)::numeric,2) end as PErcentage_of_progress

from
(

Select  AT_ST_description, Total_Population, frequency, Asset_type , Schedule_code, month_target, avg(cum_month_target_till_month) avg_cum_month_target_till_month,

case when sum(apr_cnt)::INTEGER is null THEN 0 else sum(apr_cnt)/12::INTEGER  end as  apr_cnt ,
case when sum(may_cnt)::INTEGER is null THEN 0 else sum(may_cnt)/12::INTEGER  end as  may_cnt ,
case when sum(jun_cnt)::INTEGER is null THEN 0 else sum(jun_cnt)/12::INTEGER  end as  jun_cnt ,
case when sum(jul_cnt)::INTEGER is null THEN 0 else sum(jul_cnt)/12::INTEGER  end as  jul_cnt ,
case when sum(aug_cnt)::INTEGER is null THEN 0 else sum(aug_cnt)/12::INTEGER  end as  aug_cnt ,
case when sum(sep_cnt)::INTEGER is null THEN 0 else sum(sep_cnt)/12::INTEGER  end as  sep_cnt ,
case when sum(oct_cnt)::INTEGER is null THEN 0 else sum(oct_cnt)/12::INTEGER  end as  oct_cnt ,
case when sum(nov_cnt)::INTEGER is null THEN 0 else sum(nov_cnt)/12::INTEGER  end as  nov_cnt ,
case when sum(dec_cnt)::INTEGER is null THEN 0 else sum(dec_cnt)/12::INTEGER  end as  dec_cnt ,
case when sum(jan_cnt)::INTEGER is null THEN 0 else sum(jan_cnt)/12::INTEGER  end as  jan_cnt ,
case when sum(feb_cnt)::INTEGER is null THEN 0 else sum(feb_cnt)/12::INTEGER end as  feb_cnt ,
case when sum(mar_cnt)::INTEGER is null THEN 0 else sum(mar_cnt)/12::INTEGER end as  mar_cnt ,

(sum(apr_cnt)+sum(may_cnt)+sum(jun_cnt)+sum(jul_cnt)+sum(aug_cnt)+sum(sep_cnt)+sum(oct_cnt)+sum(nov_cnt)+sum(dec_cnt)+sum(jan_cnt)+sum(feb_cnt)+sum(mar_cnt))/12::integer tprog
From
(
               -- To get the details of 
               select row_number() OVER (ORDER BY (as12m.Asset_type , as12m.Schedule_code) ),   as12m.Asset_type||'-'|| as12m.Schedule_code AT_ST_description, 
               Case when Total_Population is null THEN 0 else Total_Population end as Total_Population,
               as12m.Asset_type , as12m.Schedule_code,
               case when month_target is null then 0 else month_target end month_target,
               case when cum_month_target_till_month is null then 0 else cum_month_target_till_month end cum_month_target_till_month,
               case when Mon_cnt is null then 0 else Mon_cnt end Mon_cnt, frequency ,

 prog_of_year, prog_of_month, prog_of_year||'-'||prog_of_month,
                    case when prog_of_month != 4  then  0 else   mon_cnt end as apr_cnt  ,
                    case when prog_of_month != 5  then  0 else   mon_cnt end as may_cnt  ,
                    case when prog_of_month != 6  then  0 else   mon_cnt end as jun_cnt  ,
                    case when prog_of_month != 7  then  0 else   mon_cnt end as jul_cnt  ,
                    case when prog_of_month != 8  then  0 else   mon_cnt end as aug_cnt  ,
                    case when prog_of_month != 8  then  0 else   mon_cnt end as sep_cnt  ,
                    case when prog_of_month != 10 then  0 else   mon_cnt end as oct_cnt  ,
                    case when prog_of_month != 11 then  0 else   mon_cnt end as nov_cnt  ,
                    case when prog_of_month != 12 then  0 else   mon_cnt end as dec_cnt  ,
                    case when prog_of_month != 1  then  0 else   mon_cnt end as jan_cnt  ,
                    case when prog_of_month != 2  then  0 else   mon_cnt end as feb_cnt  ,
                    case when prog_of_month != 3  then  0 else   mon_cnt end as mar_cnt  
                    
               from (
                              -- to get all asset types and scehdule types (other than UNSCHED and COMM for month wise 
                              select extract(year from dd2) y1, extract(month from dd2) m1 , fy, asa1.Asset_type||' - '|| asa1.Schedule_code AT_ST_description , 
                              asa1.Asset_type , asa1.schedule_code, 
                              asa1.duration,
                              case when Schedule_code = 'AOH' then 'Yearly' 
                              when Schedule_code = 'QTR' then 'Quarterly' 
                              when Schedule_code = 'HY' then 'Half Yearly ' 
                              when Schedule_code = 'MON' then 'Monthly' 
                              when Schedule_code = 'Monthly' then 'Monthly' 
                              when Schedule_code = 'WEEK' then 'Weekly' 
                              when Schedule_code = 'POH' then  case when duration is null then 'Duration not defined' else duration::text||' Years' end  end frequency
                              from
                              (
                                             -- to get the 1st date of 12 months of a current FY from  now()
                                             SELECT generate_series(  (dd1+ interval '3 months') ,(dd1+ interval '14 months'), interval '1 month') dd2 , fy
                                             from
                                             (
                                             select extract(MONTH from now())::integer month1 , extract(year from now())::integer yy1,
                                             case when extract(MONTH from now())::integer  < 4 then (extract(year from now())::integer -1)::text||'-'||mod(extract(year from now())::integer ,100)::text
                                              else extract(year from now())::integer ||'-'|| mod((extract(year from now() )::integer+1), 100)::text  end FY,
                                             case when extract(MONTH from now())::integer  < 4 then to_date( (extract(year from now())::integer-1)::text||'04-01', 'YYYY-MM-DD') else
                                                  to_date (( (extract(year from now())::integer)::text), 'YYYY-MM-DD') end dd1
                                             )a
                                             --End to get the 1st date of 12 months of a current FY from  now()
                              ) b , asset_schedule_assoc asa1, product_category_member pcm1 
                              where asa1.asset_type = pcm1.Product_id
                              and pcm1.product_category_id = 'OHE_FIXED_ASSET'
                              and asa1.Schedule_code not in ('UNSCHD' , 'COMM' ) 
			      and upper(asa1.is_dpr) ='Y'
                              order by asset_type, Schedule_code, y1, m1
               ) as12m 
               -- end to get all asset types and scehdule types (other than UNSCHED and COMM for month wise 

               left Outer join 

               -- get all total count or population of each aset type in a depot and to link AT_ST for corresponding month 
               (
               select count(*) Total_Population , Asset_type
               from Asset_master_data
               -- facility 
               where Facility_id =depotid
               Group by Asset_type
               ) AP on AP.asset_type = as12m.Asset_type  
               -- End to get all total count or population of each aset type in a depot and to link AT_ST for corresponding month  

               left Outer join 

               -- get all monthly target and cumulative target upto end of month from start of FY of each aset type & Schedule of a depot
               (
               Select asset_type, schedule_type, fy,
                  CASE
                  -- WHEN ash.month1 = 1::integer case when vmct.target_jan is null then 0 else vmct.target_jan end as
                   WHEN ash.month1 = 1::integer THEN vmct.target_jan
                   WHEN ash.month1 = 2::integer THEN vmct.target_feb
                   WHEN ash.month1 = 3::integer THEN vmct.target_mar
                   WHEN ash.month1 = 4::integer THEN vmct.target_apr
                   WHEN ash.month1 = 5::integer THEN vmct.target_may
                   WHEN ash.month1 = 6::integer THEN vmct.target_jun
                   WHEN ash.month1 = 7::integer THEN vmct.target_jul
                   WHEN ash.month1 = 8::integer THEN vmct.target_aug
                   WHEN ash.month1 = 9::integer THEN vmct.target_sep
                   WHEN ash.month1 = 10::integer THEN vmct.target_oct
                   WHEN ash.month1 = 11::integer THEN vmct.target_nov
                   WHEN ash.month1 = 12::integer THEN vmct.target_dec
                   ELSE NULL::integer
               END AS month_target, 
               CASE
                   WHEN ash.month1 = 1::integer THEN vmct.cum_target_jan
                   WHEN ash.month1 = 2::integer THEN vmct.cum_target_feb
                   WHEN ash.month1 = 3::integer THEN vmct.cum_target_mar
                   WHEN ash.month1 = 4::integer THEN vmct.cum_target_apr
                   WHEN ash.month1 = 5::integer THEN vmct.cum_target_may
                   WHEN ash.month1 = 6::integer THEN vmct.cum_target_jun
                   WHEN ash.month1 = 7::integer THEN vmct.cum_target_jul
                   WHEN ash.month1 = 8::integer THEN vmct.cum_target_aug
                   WHEN ash.month1 = 9::integer THEN vmct.cum_target_sep
                   WHEN ash.month1 = 10::integer THEN vmct.cum_target_oct
                   WHEN ash.month1 = 11::integer THEN vmct.cum_target_nov
                   WHEN ash.month1 = 12::integer THEN vmct.cum_target_dec
                   ELSE NULL::integer
               END AS cum_month_target_till_month
           
               from v_monthly_cum_targets vmct, 
                              (select extract(MONTH from now()) month1 , extract(year from now()) yy1 )ash
               -- facility 
               where facility_id=depotid
               ) tar on as12m.Asset_type = tar.Asset_type  and  as12m.Schedule_code = tar.schedule_type and as12m.fy= tar.fy
               -- End get all monthly target and cumulative target upto end of month from start of FY of each aset type & Schedule of a depot
               left outer join
               -- get Schedules done count till this month from start of FY of each aset type & Schedule of a depot
               (
               Select count(*) Mon_cnt , Asset_type  , Schedule_code , extract(MONTH from schedule_date) prog_of_month, extract(year from schedule_date) prog_of_year, fy
               from v_assets_schedule_history
               -- facility 
               where facility_id=depotid
               --and FY =  as12m.fy
               group by Asset_type  , Schedule_code , extract(MONTH from schedule_date) , extract(year from schedule_date) , fy
               order by prog_of_year, prog_of_month
               ) mp -- mp monthly progress of at and st for each month and year
               on as12m.Asset_type = mp.Asset_type  and  as12m.Schedule_code = mp.schedule_code and as12m.fy= mp.fy
               -- End of get Schedules done count till this month from start of FY of each aset type & Schedule of a depot
               
               order by 1 , 2
               ) mgrp

group by AT_ST_description, Total_Population, Asset_type , Schedule_code, month_target , frequency

order by AT_ST_description,   Asset_type , Schedule_code
) final
) a
---
left outer join 

( 
select month1 , yy1, a1.fy, dd1, total_year_target,asset_type, schedule_type, facility_id
from
(
select extract(MONTH from now())::integer month1 , extract(year from now())::integer yy1,
case when extract(MONTH from now())::integer  < 4 then (extract(year from now())::integer -1)::text||'-'||mod(extract(year from now())::integer ,100)::text
else extract(year from now())::integer ||'-'|| mod((extract(year from now() )::integer+1), 100)::text  end FY,
case when extract(MONTH from now())::integer  < 4 then to_date( (extract(year from now())::integer-1)::text||'04-01', 'YYYY-MM-DD') else
to_date (( (extract(year from now())::integer)::text), 'YYYY-MM-DD') end dd1
)a1
left outer join ( select total_year_target, fy , asset_type, schedule_type, facility_id from v_monthly_cum_targets where facility_id = depotid ) b on (a1.fy =b.fy)
) ty_tgt on (ty_tgt.facility_id = a.facility_id and ty_tgt.Asset_type=a.asset_type and ty_tgt.Schedule_type =a.schedule_code)

left outer join 
-- out standing 

( 
 -- out staind count by facility and at-st
--- outstanding sch at-st count as on date
select count(*) os_count, asset_type||'-'||schedule_code at_st, facility_id, depot_type
from
(
--- all outstanding schedules even there is no scedule done etc
--- outstanding Schedules
select ast.asset_type||'-'||ast.schedule_code at_st,
ast.facility_id , ast.facility_name, depot_type, ast.asset_id, ast.asset_type ,ast.schedule_code, date_of_commision , recent_schedule_date::date, duration , frequency, uom_of_duration, month_duration ,
case when recent_schedule_date is null  then (date_of_commision + INTERVAL '1 month'  * ast.month_duration )::date
                              else (recent_schedule_date + INTERVAL '1 month' * month_duration)::date --"MONTHS"
end as Schedule_due_date,
(now()::date - case when recent_schedule_date is null  then (date_of_commision + INTERVAL '1 month'  * ast.month_duration )::date
                              else (recent_schedule_date + INTERVAL '1 month' * month_duration)::date --"MONTHS"
               end) outstanding_due_days,
case when (recent_schedule_date is null and date_of_commision is null) then 'Sch not yet done or data missing' end as remark
From
(
select amd.facility_id, f.facility_name, depot_type,
asset_id, amd.asset_type ,schedule_code,  AT_ST_description, oem_serial ,rly_assigned_serial , date_of_commision , amd.make ,amd.model ,section ,kilometer , duration , frequency, uom_of_duration,
month_duration
from facility f, asset_master_data amd --where facility_id=depotId, 
left outer join 
 (
   -- to get all asset types and scehdule types (other than UNSCHED and COMM for month wisedepotId
   select -- extract(year from dd2) y1, extract(month from dd2) m1 , fy, 
   asa1.Asset_type||' - '|| asa1.Schedule_code AT_ST_description ,
   asa1.Asset_type , asa1.schedule_code, uom_of_duration,
   asa1.duration,
   case when Schedule_code = 'AOH' then 'Yearly'
   when Schedule_code = 'QTR' then 'Quarterly'
   when Schedule_code = 'HY' then 'Half Yearly '
   when Schedule_code = 'MON' then 'Monthly'
   when Schedule_code = 'Monthly' then 'Monthly'
   when Schedule_code = 'WEEK' then 'Weekly'
   when Schedule_code = 'POH' then  case when duration is null then 'Duration not defined' else duration::text||' Years' end  end frequency,
   case when uom_of_duration ='Time in Years'  then duration::integer *12 
        when uom_of_duration ='Time in Months'  then duration::integer *1  
   end as month_duration --::integer
   from
   asset_schedule_assoc asa1, product_category_member pcm1
   where asa1.asset_type = pcm1.Product_id
   and pcm1.product_category_id = 'OHE_FIXED_ASSET'
   and asa1.Schedule_code not in ('UNSCHD' , 'COMM' )
   order by asset_type, Schedule_code 
               ) as12m on (amd.asset_type = as12m.asset_type)
               where amd.facility_id = f.facility_id
)AST
Left outer join 
 (
               Select  facility_id, asset_id , Asset_type  , Schedule_code , Max(schedule_date) recent_schedule_date
               from v_assets_schedule_history
               where facility_id=depotid
               
               group by  facility_id, asset_id ,Asset_type  , Schedule_code 
)  vash on (ast.asset_id = vash.asset_id and ast.asset_type = vash.asset_type and ast.schedule_code = vash.schedule_code)
               -- end to get all asset types and scehdule types (other than UNSCHED and COMM for month wise
where ast.facility_id=depotid
order by asset_id, asset_type, schedule_code
--- outstanding Schedules
--- all outstanding schedules even there is no scedule done etc

) osi  -- oust standing assets items
where outstanding_due_days > 0
group by asset_type||'-'||schedule_code, facility_id, depot_type
) osc on (osc.at_st = a.description and osc.facility_id = a.facility_id)
where ty_tgt.facility_id=depotid 
and Total_Population > 0
order by 2; 
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.asset_schedule_graph(text)
  OWNER TO postgres;
  



  
  -- Over Due Schedule For Map

select amd_kilometer , amd_asset_id,  amd_facility_id,  amd_section ,  No_of_assets_at_loc ,   all_assets_sch_data_at_loc ,all_asset_types_at_loc

, longitude ,latitude , altitude , ohe_mast , loc.section , pwi , overdue_str

FROM

(   -- -- end of AMD and thier ASH data details GROUP by KM, AI, F, SEC, OVERDUE_STR

               select amd_kilometer, amd_asset_id, --asset_at_location , no_of_assets , amd_asset_type ,

                amd_facility_id,  amd_section , count(*) No_of_assets_at_loc , string_agg(amd_asset_type , ',') all_asset_types_at_loc,

               string_agg( asset_recent_sch_data||'('||no_of_sch||')' , ' & ') all_assets_sch_data_at_loc , overdue_str

               from

               (

                              select amd_kilometer,--no_of_assets,

                              amd_asset_id, --asset_at_location , no_of_assets ,

                              amd_asset_type ,

                              amd_facility_id,  amd_section  , amd_asset_type ||'('||no_of_sch||')'|| asset_recent_sch_data asset_recent_sch_data ,no_of_sch , overdue_str

                              from

                              (

 

                                             select count(*), asset_ID amd_asset_id  , facility_id amd_facility_id , section amd_section , kilometer amd_kilometer  , asset_type  amd_asset_type

                                              from asset_master_data where asset_type in

                                             (

                                             select product_id from product_category_member  where product_category_id  ='OHE_FIXED_ASSET'

                                             )

                                             group by asset_id ,facility_id  , section   , kilometer  , asset_type

                                             --having count(*) > 1

                              ) amd

                              left outer join

                              -- For all recent ASH details

                              ( -- start of concatenate rec & next sch dates for each sch of an asset ,  overdue flag, no of assets at a location

                              select asset_id , asset_type , facility_id,  count(*) no_of_sch, string_agg( schedule_code ||'/'|| recent_schedule_date ||'/'||

                              case when to_char(next_Schedule_due_date, 'DD-MON-YYYY') is null then ' check Data for due date' else to_char(next_Schedule_due_date, 'DD-MON-YYYY')  end ,

                                ' # ') asset_recent_sch_data , string_agg(overdue_flag,',') overdue_str , count(*) no_of_assets_at_loc -- section ,

                              FROM

                                             ( -- start for Next Schedule date after recent schedule of assets with thier recent schedule done of each sch type

                                             select asset_id , asset_type , facility_id,  schedule_code ,  recent_schedule_date , -- section,

                                             case when recent_schedule_date is not null  then -- (date_of_commision + INTERVAL '1 month'  * month_duration )::date

                                                                                          (recent_schedule_date + INTERVAL '1 month' * month_duration)::date end next_Schedule_due_date,

                                             case when recent_schedule_date is not null and (recent_schedule_date + INTERVAL '1 month' * month_duration)::date is not null

                                             and (recent_schedule_date + INTERVAL '1 month' * month_duration)::date < now() then  'Yes' else 'NA' end as overdue_flag,                                                                                                            

                                             uom_of_duration , duration

                                                            from

                                                            (  -- start for list of assets with thier recent schedule done of each sch type -- GROUP by AID, AT, ST , F, UOM , Duration

                                                            select asset_id , ash.asset_type , facility_id, ash.schedule_code , max(schedule_date)::date recent_schedule_date , uom_of_duration , duration, --section,

                                                            case when uom_of_duration ='Time in Years'  then duration::integer*12

                                                            when uom_of_duration ='Time in Months'  then duration::integer*1  end as month_duration

                                                                                           -- schedule_code

                                                            from assets_schedule_history ash, asset_schedule_assoc asa

                                                            where ash.asset_type in

                                                                           (

                                                                           select product_id from product_category_member  where product_category_id  ='OHE_FIXED_ASSET'

                                                                           )

                                                            and ash.asset_type = asa.asset_type

                                                            and  ash.schedule_code =  asa.schedule_code

                                                            group by asset_id , ash.asset_type , facility_id,  ash.schedule_code , uom_of_duration , duration , -- section,

                                                                           case when uom_of_duration ='Time in Years'  then duration::integer*12

                                                                                          when uom_of_duration ='Time in Months'  then duration::integer*1

                                                                           end  -- schedule_code

                                                              -- End for list of assets with thier recent schedule done of each sch type GROUP by AID, AT, ST , F, UOM , Duration

                                                            ) a

                                                            -- END for Next Schedule date after recent schedule of assets with thier recent schedule done of each sch type

                                                                           --where asset_id = '241/21-25'

                                                                           --order by 1,2,3

                                             ) b

                                             group by asset_id , asset_type , facility_id --, section

                                --- END of of concatenate rec & next sch dates for each sch of an asset ,  overdue flag, no of assets at a location GROUP by AID, AT, F

                              ) LASH on ( LASH.asset_id = amd_asset_id and LASH.asset_type =  amd_asset_type and LASH.facility_id = amd_facility_id) -- and LASH.section = amd_section )

                             

               ) Al

               group by  amd_kilometer, amd_asset_id,   amd_facility_id,  amd_section ,overdue_str

               -- end of AMD and thier ASH data details GROUP by KM, AI, F, SEC, OVERDUE_STR

) asc_da

Left outer join

(

 select  longitude ,latitude , altitude , ohe_mast ,section , pwi  from ohe_location

) loc  on (ohe_mast = amd_asset_id)


--- user_func_location(userid text)
create FUNCTION user_func_location(userid text)
  RETURNS text AS
$BODY$

DECLARE

               FIRST_LEVEL_RM_OF_UNIT TEXT;

               --NEXT_LEVEL_RM_OF_UNIT TEXT;

        UNIT_NAME TEXT;

               NO_OF_REPORTEES BIGINT;

               user_id text;

               N BIGINT;

               UNITNAMES TEXT;

               CUR_UNIT_NAMES CURSOR FOR SELECT UNIT_CODE FROM FUNCTIONAL_LOCATION_HIERARCHY flh_l where flh_l.head_login_id not in (select distinct flh_r.rm_login_id from FUNCTIONAL_LOCATION_HIERARCHY flh_r) order BY seq_id;

               UNIT_LIST  VARCHAR[] ;

BEGIN

 

               --UNIT_LIST='';

               RAISE NOTICE 'intitial -------UNIT_LIST-%', UNIT_LIST;

               SELECT COUNT(*) INTO NO_OF_REPORTEES FROM FUNCTIONAL_LOCATION_HIERARCHY WHERE RM_LOGIN_ID = USERID;

               RAISE NOTICE 'intitial -------UNIT_LIST-%', UNIT_LIST;

               IF  NO_OF_REPORTEES = 0 THEN  --USER HAS NO REPORTEES THEN HIS DEFAULT DEPOT/WH IS ONLY ONE UNIT

                              RAISE NOTICE 'IN LOOP UNIT NAME------- if no RM % -UNIT_NAME-%', N,UNITNAMES;

                              UNIT_LIST := array(

                       SELECT FACILITY_NAME 

                              FROM USER_DEFUALT_FAC_CONS_IND_ETC

                              WHERE FACILITY_TYPE_ID in ('WAREHOUSE', 'PLANT','SP', 'SSP', 'TSS')

                              AND USER_LOGIN_ID = USERID);

                              --UNIT_LIST= UNIT_LIST||UNIT_NAME||',';

                              RETURN UNIT_LIST;

               ELSE  -- GET THE LIST OF DEPOTS/WH COVERED TO USER DIRECTLY OR THROUGH THE REPORTEES

 

               RAISE NOTICE ' UNIT NAME------- For RM   % -',USERID ;

                              SELECT seq_id INTO user_id FROM FUNCTIONAL_LOCATION_HIERARCHY WHERE head_LOGIN_ID = USERID;

                              RAISE NOTICE 'user name id is ------- -% --', user_id;

                              UNIT_LIST := array(

                              WITH RECURSIVE nodes(seq_id,unit_code, unit_type, head_login_id, rm_seq_id ) AS

                              (

                              SELECT f1.seq_id, f1.unit_code,  f1.unit_type, f1.head_login_id, f1.rm_seq_id

                              FROM functional_location_hierarchy f1 WHERE f1.rm_seq_id::integer =  user_id::integer

                              UNION

                              SELECT f2.seq_id, f2.unit_code,  f2.unit_type, f2.head_login_id, f2.rm_seq_id

                              FROM functional_location_hierarchy f2, nodes f1 WHERE f2.rm_seq_id::integer = f1.seq_id::integer

                              )

                              SELECT unit_code FROM nodes  

                              order by rm_seq_id desc

                              );

                              RAISE NOTICE 'in loop-------UNIT_LIST-%', UNIT_LIST;

                             

                              RAISE NOTICE 'final --------NO OF LOCATIONS %  -- LIST OF LOCATIONS %', N, UNIT_LIST;

                              RETURN UNIT_LIST;

               END IF; 

               END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.user_func_location(text)
  OWNER TO postgres;

        
