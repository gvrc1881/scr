
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


CREATE OR REPLACE VIEW public.v_asset_master_data AS
 SELECT amd.seq_id,
    amd.asset_type,
    amd.section,
    amd.part1,
    amd.part2,
    amd.part3,
    amd.kilometer,
    amd.position_id,
    amd.asset_id,
    amd.facility_id,
    fac.facility_name,
    fac.facility_type_id,
    fac.depot_type,
    amd.elementary_section,
    amd.oem_serial,
    amd.rly_assigned_serial,
    amd.parent_asset_type,
    amd.parent_asset_type_id,
    amd.equipped_date,
    amd.strip_date,
    amd.date_of_commision,
    amd.date_of_manufacture,
    amd.date_of_received,
    amd.source,
    amd.make,
    amd.model,
    amd.warranty_amc,
    amd.warranty_amc_end_date,
    amd.vendor,
    amd.expiry_date
   FROM asset_master_data amd,
    facility fac
  WHERE amd.facility_id::text = fac.facility_id::text;

ALTER TABLE public.v_asset_master_data
    OWNER TO postgres;
----69



-- View: public.v_drives

-- DROP VIEW public.v_drives;

CREATE OR REPLACE VIEW public.v_drives AS 
 SELECT drives.id,
    drives.active,
    drives.asset_description,
    drives.asset_type,
    drives.checklist,
    drives.created_by,
    drives.created_on,
    drives.criteria,
    drives.description,
    drives.from_date,
    drives.functional_unit,
    drives.is_id_required,
    drives.frequency,
    drives.name,
    drives.status_id,
        CASE
            WHEN drives.status_id = 1 THEN 'No'::text
            ELSE 'Yes'::text
        END AS drv_deleted_status,
    drives.target_qty,
    drives.to_date,
    drives.updated_by,
    drives.updated_on,
    drives.depot_type
   FROM drives;

ALTER TABLE public.v_drives
  OWNER TO postgres;
  
  
--3
-- View: v_asset_monthly_targets

-- View: public.v_asset_monthly_targets

-- DROP VIEW public.v_asset_monthly_targets;

drop view v_asset_monthly_targets;
create view v_asset_monthly_targets as
SELECT amt.seq_id,
    amt.facility_id,
    fac.facility_name,
    fac.facility_type_id,
    fac.depot_type,
    amt.schedule_type,
    amt.asset_type,
    amt.total_population,
    amt.target_jan,
    amt.target_feb,
    amt.target_mar,
    amt.target_apr,
    amt.target_may,
    amt.target_june AS target_jun,
    amt.target_july AS target_jul,
    amt.target_aug,
    amt.target_sep,
    amt.target_oct,
    amt.target_nov,
    amt.target_dec,
	amt.target_apr + amt.target_may as cum_target_may ,
    	amt.target_apr + amt.target_may + amt.target_june as cum_target_Jun ,
	amt.target_apr + amt.target_may + amt.target_june as target_qtr1 ,
	amt.target_apr + amt.target_may + amt.target_june + amt.target_july  as cum_target_Jul ,
	amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug  as cum_target_aug ,
	amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep as cum_target_sep ,
	amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep as target_qtr2 ,
	amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct as cum_target_oct ,
	amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov as cum_target_nov ,
	amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec as cum_target_dec ,
	amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec as target_qtr3 ,
	amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec + amt.target_jan as cum_target_jan ,
	amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec + amt.target_jan + amt.target_feb as cum_target_feb ,
	amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec + amt.target_jan + amt.target_feb + amt.target_mar  as cum_target_mar ,
	amt.target_jan + amt.target_feb + amt.target_mar + amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec AS total_target_year,
    amt.year,
    btrim((amt.year::text || '-'::text) || (((amt.year::integer + 1) % 100)::text)) AS fy
   FROM asset_monthly_targets amt,
    facility fac
  WHERE amt.facility_id::text = fac.facility_id::text;

SELECT ddp.id,
    ddp.activity_id,
    ddp.created_by,
    ddp.created_on,
    ddp.depot,
    ddp.division,
    ddp.performed_count,
    ddp.performed_date,
    ddp.section,
    ddp.status_id,
        CASE
            WHEN ddp.status_id = 1 THEN 'No'::text
            ELSE 'Yes'::text
        END AS deleted_status,
    ddp.supervisor,
    ddp.updated_by,
    ddp.updated_on,
    ddp.drive_id,
    d.active AS drv_active,
    d.asset_description AS drv_asset_description,
    d.asset_type AS drv_asset_type,
    d.checklist AS drv_checklist,
    d.created_by AS drv_created_by,
    d.criteria AS drv_criteria,
    d.description AS drv_description,
    d.from_date AS drv_from_date,
    d.frequency AS drv_frequency,
    d.functional_unit AS drv_functional_unit,
    d.is_id_required AS drv_is_id_required,
    d.name AS drv_name,
    d.status_id AS drv_status_id,
    d.target_qty AS drv_target_qty,
    d.to_date AS drv_to_date,
    d.updated_by AS drv_updated_by,
    d.depot_type AS drv_depot_type,
    d.drv_deleted_status,
    d.id AS drv_id
   FROM drive_daily_progress ddp,
    v_drives d
  WHERE ddp.drive_id = d.id;

/*
v_asset_monthly_targets_div depends on view v_monthly_cum_targets 

view v_assets_schedule_history_targets depends on view v_monthly_cum_targets 
view v_asset_sch_count_fy_mon_daily depends on view v_assets_schedule_history_targets 
view v_assets_schedule_completed_targets depends on view v_assets_schedule_history_targets 
view v_assets_schedule_counts depends on view v_monthly_cum_targets 
view v_sch_all_days_done_run_count_fac_asstype_sch depends on view v_monthly_cum_targets 
view v_sch_done_daily_run_count_fac_asstype_sch depends on view v_monthly_cum_targets 

HINT: Use DROP ... CASCADE to drop the dependent objects too. SQL state: 2BP01

DETAIL:  drop cascades to view v_monthly_cum_targets
drop cascades to view v_asset_monthly_targets_div
drop cascades to view v_assets_schedule_history_targets
drop cascades to view v_asset_sch_count_fy_mon_daily
drop cascades to view v_assets_schedule_completed_targets
drop cascades to view v_assets_schedule_counts
drop cascades to view v_sch_all_days_done_run_count_fac_asstype_sch
drop cascades to view v_sch_done_daily_run_count_fac_asstype_sch
*/






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
-- View: public.v_asset_schedule_activity_record_latest

-- DROP VIEW public.v_asset_schedule_activity_record_latest;

CREATE OR REPLACE VIEW public.v_asset_schedule_activity_record_latest AS
 SELECT row_number() OVER (ORDER BY (amd.kilometer::integer), (amd.position_id::integer)) AS s_no,
    asar.asset_id,
    amd.asset_type,
    asar.schedule_code,
    asar.schedule_date,
    asar.schedule_actual_date,
    asar.status,
    asar.m1::numeric AS m1,
    amd.kilometer::numeric AS kilometer,
    amd.position_id::numeric AS position_id,
    amd.part1::numeric AS span,
    asar.m2::numeric AS m2,
    asar.m3::numeric AS m3,
    asar.m4::numeric AS m4,
    asar.m5::numeric AS m5,
    asar.m6::numeric AS m6,
    asar.m7::numeric AS m7,
    asar.m8::numeric AS m8,
    asar.m9::numeric AS m9,
    asar.m10::numeric AS m10,
    asar.m11::numeric AS m11,
    asar.m12::numeric AS m12,
    asar.m13::numeric AS m13,
    asar.m14::numeric AS m14,
    asar.m15::numeric AS m15,
    asar.m16::numeric AS m16,
    asar.m17::numeric AS m17,
    asar.m18::numeric AS m18,
    asar.m19::numeric AS m19,
    asar.m20::numeric AS m20,
    asar.m21::numeric AS m21,
    asar.m22::numeric AS m22,
    asar.a1,
    asar.a2,
    asar.a3,
    asar.a4,
    asar.a5,
    asar.a6,
    asar.a7,
    asar.a8,
    asar.a9,
    asar.a10,
    asar.a11,
    asar.a12,
    asar.a13,
    asar.a14,
    asar.a15,
    asar.a16,
    asar.a17,
    asar.a18,
    asar.a19,
    asar.a20,
    asar.a21,
    asar.a22
   FROM asset_schedule_activity_record asar,
    asset_master_data amd
  WHERE asar.asset_id::text = amd.asset_id::text AND asar.schedule_date = (( SELECT max(asar1.schedule_date) AS max
           FROM asset_schedule_activity_record asar1
          WHERE asar.asset_id::text = asar1.asset_id::text AND asar.schedule_code::text = 'AOH'::text));

ALTER TABLE public.v_asset_schedule_activity_record_latest
    OWNER TO postgres;


--06
--View: v_assets_schedule_history

-- View: public.v_assets_schedule_history

-- DROP VIEW public.v_assets_schedule_history;

CREATE OR REPLACE VIEW public.v_assets_schedule_history AS
 SELECT ash.seq_id,
    ash.asset_id,
    ash.asset_type,
    ash.schedule_code,
    ash.schedule_date,
    date_part('year'::text, ash.schedule_date) AS year1,
    date_part('month'::text, ash.schedule_date) AS month1,
    date_part('week'::text, ash.schedule_date) AS week1,
        CASE
            WHEN date_part('month'::text, ash.schedule_date) = 1::double precision OR date_part('month'::text, ash.schedule_date) = 2::double precision OR date_part('month'::text, ash.schedule_date) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (ash.schedule_date - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, ash.schedule_date)::integer, 100), '99'::text))
            WHEN date_part('month'::text, ash.schedule_date) = 4::double precision OR date_part('month'::text, ash.schedule_date) = 5::double precision OR date_part('month'::text, ash.schedule_date) = 6::double precision OR date_part('month'::text, ash.schedule_date) = 7::double precision OR date_part('month'::text, ash.schedule_date) = 8::double precision OR date_part('month'::text, ash.schedule_date) = 9::double precision OR date_part('month'::text, ash.schedule_date) = 10::double precision OR date_part('month'::text, ash.schedule_date) = 11::double precision OR date_part('month'::text, ash.schedule_date) = 12::double precision THEN (btrim(to_char(date_part('year'::text, ash.schedule_date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (ash.schedule_date + '1 year'::interval)::date)::integer, 100), '99'::text))
            ELSE NULL::text
        END AS fy,
    ash.status,
    ash.details_of_maint,
    ash.done_by,
    ash.initial_of_incharge,
    ash.remarks,
    ash.pb_operation_seq_id,
    ash.facility_id,
    fac.facility_name,
    fac.facility_type_id,
    fac.depot_type
   FROM assets_schedule_history ash,
    facility fac
  WHERE ash.facility_id::text = fac.facility_id::text;

ALTER TABLE public.v_assets_schedule_history
    OWNER TO postgres;



---07
-- View: v_asset_sch_next_due_date

-- View: public.v_asset_sch_next_due_date

-- DROP VIEW public.v_asset_sch_next_due_date;

CREATE OR REPLACE VIEW public.v_asset_sch_next_due_date AS
 SELECT amd.asset_id,
    vash.asset_type,
    vash.schedule_code,
    asa.duration,
    asa.uom_of_duration,
    amd.date_of_commision,
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

ALTER TABLE public.v_asset_sch_next_due_date
    OWNER TO postgres;


--08
-- View: v_cb_trippings_count_duration_daily
-- View: public.v_cb_trippings_count_duration_daily

-- DROP VIEW public.v_cb_trippings_count_duration_daily;

CREATE OR REPLACE VIEW public.v_cb_trippings_count_duration_daily AS
 SELECT f.type_of_failure,
    to_char(f.from_date_time, 'yyyy-mm-dd'::text) AS date1,
    fac.facility_name,
    f.asset_id,
    count(*) AS daily_count,
    sum(date_part('day'::text, f.thru_date_time - f.from_date_time) * 24::double precision * 60::double precision + date_part('hour'::text, f.thru_date_time - f.from_date_time) * 60::double precision + date_part('minute'::text, f.thru_date_time - f.from_date_time)) AS failure_duration_min,
    f.nature_of_closure,
    f.cause_of_failure,
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
     LEFT JOIN facility fac ON f.facility_id::text = fac.facility_id::text
  WHERE f.type_of_failure::text = 'POWER_FAILURE'::text
  GROUP BY f.asset_id, (date_part('year'::text, f.from_date_time)), (date_part('month'::text, f.from_date_time)), (date_part('week'::text, f.from_date_time)), (to_char(f.from_date_time, 'yyyy-mm-dd'::text)), (
        CASE
            WHEN date_part('month'::text, f.from_date_time) = 1::double precision OR date_part('month'::text, f.from_date_time) = 2::double precision OR date_part('month'::text, f.from_date_time) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (f.from_date_time - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, f.from_date_time)::integer, 100), '99'::text))
            WHEN date_part('month'::text, f.from_date_time) = 4::double precision OR date_part('month'::text, f.from_date_time) = 5::double precision OR date_part('month'::text, f.from_date_time) = 6::double precision OR date_part('month'::text, f.from_date_time) = 7::double precision OR date_part('month'::text, f.from_date_time) = 8::double precision OR date_part('month'::text, f.from_date_time) = 9::double precision OR date_part('month'::text, f.from_date_time) = 10::double precision OR date_part('month'::text, f.from_date_time) = 11::double precision OR date_part('month'::text, f.from_date_time) = 12::double precision THEN (btrim(to_char(date_part('year'::text, f.from_date_time), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (f.from_date_time + '1 year'::interval)::date)::integer, 100), '99'::text))
            ELSE NULL::text
        END), f.type_of_failure, f.facility_id, fac.facility_name, fac.facility_type_id, fac.depot_type, f.sub_station, f.nature_of_closure, f.cause_of_failure;

ALTER TABLE public.v_cb_trippings_count_duration_daily
    OWNER TO postgres;

---09
-- View: public.v_energy_meter

-- DROP VIEW public.v_energy_meter;

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



---10
-- View: v_energy_consumption

-- View: public.v_energy_consumption

-- DROP VIEW public.v_energy_consumption;
create view v_energy_consumption as
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
		to_char(ec.max_load_time, 'dd-Mon-YYYY'::text) AS max_load_date,
    ec.max_load_time AS max_load_time_date,
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
  WHERE ec.location::text = em.feeder_name::text 
  AND ec.energy_reading_date >= em.em_start_date 
  AND (ec.energy_reading_date <= em.em_end_date OR em.em_end_date IS NULL);

--11
-- View: v_energy_day_consumption_pf_cpr
-- View: public.v_energy_day_consumption_pf_cpr

-- DROP VIEW public.v_energy_day_consumption_pf_cpr;

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
	cur.max_load_date,
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
          WHERE jr1.energy_reading_date < cur.energy_reading_date 
		  AND (jr1.joint_meter::bpchar = 'y'::bpchar OR jr1.joint_meter::bpchar = 'Y'::bpchar) 
		  AND jr1.feeder_id::text = cur.feeder_id::text)) 
		  AND jr.feeder_id::text = cur.feeder_id::text;

--12
-- View: v_failures_count_duration_daily

-- View: public.v_failures_count_duration_daily

-- DROP VIEW public.v_failures_count_duration_daily;


CREATE OR REPLACE VIEW public.v_failures_count_duration_daily
 AS
 SELECT f.type_of_failure,
    count(*) AS daily_count,
    sum(date_part('day'::text, f.thru_date_time - f.from_date_time) * 24::double precision * 60::double precision + date_part('hour'::text, f.thru_date_time - f.from_date_time) * 60::double precision + date_part('minute'::text, f.thru_date_time - f.from_date_time)) AS delay_time,
    to_char(f.from_date_time, 'yyyy-mm-dd'::text) AS date1,
    date_part('year'::text, f.from_date_time) AS year1,
    date_part('month'::text, f.from_date_time) AS month1,
    date_part('week'::text, f.from_date_time) AS week1,
    f.facility_id,
    fac.facility_name,
    fac.facility_type_id,
    fac.depot_type,
    f.sub_station,
        CASE
            WHEN date_part('month'::text, f.from_date_time) = 1::double precision OR date_part('month'::text, f.from_date_time) = 2::double precision OR date_part('month'::text, f.from_date_time) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (f.from_date_time - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, f.from_date_time)::integer, 100), '99'::text))
            WHEN date_part('month'::text, f.from_date_time) = 4::double precision OR date_part('month'::text, f.from_date_time) = 5::double precision OR date_part('month'::text, f.from_date_time) = 6::double precision OR date_part('month'::text, f.from_date_time) = 7::double precision OR date_part('month'::text, f.from_date_time) = 8::double precision OR date_part('month'::text, f.from_date_time) = 9::double precision OR date_part('month'::text, f.from_date_time) = 10::double precision OR date_part('month'::text, f.from_date_time) = 11::double precision OR date_part('month'::text, f.from_date_time) = 12::double precision THEN (btrim(to_char(date_part('year'::text, f.from_date_time), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (f.from_date_time + '1 year'::interval)::date)::integer, 100), '99'::text))
            ELSE NULL::text
        END AS fy,
		fac.division,
	case 
		when f.internal_external = 'true'  then 'External'
		when f.internal_external ='false' then 'Internal'
		end internal_external
   FROM failures f
     LEFT JOIN facility fac ON f.sub_station::text = fac.facility_id::text
  GROUP BY (date_part('year'::text, f.from_date_time)), (date_part('month'::text, f.from_date_time)), (date_part('week'::text, f.from_date_time)), (to_char(f.from_date_time, 'yyyy-mm-dd'::text)), (
        CASE
            WHEN date_part('month'::text, f.from_date_time) = 1::double precision OR date_part('month'::text, f.from_date_time) = 2::double precision OR date_part('month'::text, f.from_date_time) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (f.from_date_time - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, f.from_date_time)::integer, 100), '99'::text))
            WHEN date_part('month'::text, f.from_date_time) = 4::double precision OR date_part('month'::text, f.from_date_time) = 5::double precision OR date_part('month'::text, f.from_date_time) = 6::double precision OR date_part('month'::text, f.from_date_time) = 7::double precision OR date_part('month'::text, f.from_date_time) = 8::double precision OR date_part('month'::text, f.from_date_time) = 9::double precision OR date_part('month'::text, f.from_date_time) = 10::double precision OR date_part('month'::text, f.from_date_time) = 11::double precision OR date_part('month'::text, f.from_date_time) = 12::double precision THEN (btrim(to_char(date_part('year'::text, f.from_date_time), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (f.from_date_time + '1 year'::interval)::date)::integer, 100), '99'::text))
            ELSE NULL::text
        END), f.type_of_failure, f.facility_id, fac.facility_name, fac.facility_type_id, fac.depot_type, f.sub_station
		,fac.division,f.internal_external;

ALTER TABLE public.v_failures_count_duration_daily
    OWNER TO postgres;


---13
-- View: v_failures_count_duration_fy_mon_wk_daily
-- View: public.v_failures_count_duration_fy_mon_wk_daily

-- DROP VIEW public.v_failures_count_duration_fy_mon_wk_daily;

CREATE OR REPLACE VIEW public.v_failures_count_duration_fy_mon_wk_daily AS
 SELECT d.type_of_failure,
    d.fy,
    d.month1,
    d.week1,
    d.date1,
    d.cum_delay_time_daily,
    w.cum_delay_time_weekly,
    m.cum_delay_time_monthly,
    fy.cum_delay_time_fy,
    d.daily_count,
    w.w_count,
    m.m_count,
    fy.fy_count
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

ALTER TABLE public.v_failures_count_duration_fy_mon_wk_daily
    OWNER TO postgres;


---14
-- View: v_monthly_cum_targets
-- View: public.v_monthly_cum_targets

-- DROP VIEW public.v_monthly_cum_targets;

CREATE OR REPLACE VIEW public.v_monthly_cum_targets AS
 SELECT asmt.seq_id,
    asmt.facility_id,
    asmt.facility_name,
    asmt.facility_type_id,
    asmt.depot_type,
    asmt.schedule_type,
    asmt.asset_type,
    asmt.target_jan,
    asmt.target_feb,
    asmt.target_mar,
    asmt.target_apr,
    asmt.target_may,
    asmt.target_jun,
    asmt.target_jul,
    asmt.target_aug,
    asmt.target_sep,
    asmt.target_oct,
    asmt.target_nov,
    asmt.target_dec,
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
    asmt.year,
    asmt.fy
   FROM v_asset_monthly_targets asmt;

ALTER TABLE public.v_monthly_cum_targets
    OWNER TO postgres;



--15
-- View: v_assets_schedule_history_targets
-- View: public.v_assets_schedule_history_targets

-- DROP VIEW public.v_assets_schedule_history_targets;

CREATE OR REPLACE VIEW public.v_assets_schedule_history_targets AS
 SELECT ash.asset_id,
    ash.asset_type,
    ash.schedule_code,
    ash.schedule_date,
    ash.year1,
    ash.month1,
    ash.week1,
    ash.fy,
    ash.status,
    ash.details_of_maint,
    ash.done_by,
    ash.initial_of_incharge,
    ash.remarks,
    ash.pb_operation_seq_id,
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
    vmct.total_year_target,
    ash.facility_id,
    fac.facility_name,
    fac.facility_type_id,
    fac.depot_type
   FROM v_assets_schedule_history ash,
    facility fac,
    v_monthly_cum_targets vmct
  WHERE ash.facility_id::text = fac.facility_id::text AND vmct.asset_type::text = ash.asset_type::text AND vmct.schedule_type::text = ash.schedule_code::text AND vmct.facility_id::text = ash.facility_id::text AND vmct.fy = ash.fy;

ALTER TABLE public.v_assets_schedule_history_targets
    OWNER TO postgres;


--16
-- View: v_assets_schedule_completed_targets

-- View: public.v_assets_schedule_completed_targets

-- DROP VIEW public.v_assets_schedule_completed_targets;

CREATE OR REPLACE VIEW public.v_assets_schedule_completed_targets AS
 SELECT to_char(vasht.schedule_date, 'dd-Mon-yyyy'::text) AS schedule_date,
    vasht.asset_type,
    vasht.schedule_code,
    count(*) AS sch_done_count,
    vasht.month_target,
    vasht.cum_month_target,
    vasht.total_year_target,
    vasht.week1,
    vasht.month1,
    vasht.year1,
    vasht.fy,
    vasht.facility_id,
    vasht.facility_name,
    vasht.facility_type_id,
    vasht.depot_type
   FROM v_assets_schedule_history_targets vasht
  GROUP BY vasht.fy, vasht.schedule_date, vasht.asset_type, vasht.schedule_code, vasht.facility_id, vasht.facility_name, vasht.facility_type_id, vasht.depot_type, vasht.week1, vasht.month1, vasht.year1, vasht.month_target, vasht.cum_month_target, vasht.total_year_target;

ALTER TABLE public.v_assets_schedule_completed_targets
    OWNER TO postgres;


--17
-- View: v_asset_sch_count_fy_mon_daily
-- View: public.v_asset_sch_count_fy_mon_daily

-- DROP VIEW public.v_asset_sch_count_fy_mon_daily;

CREATE OR REPLACE VIEW public.v_asset_sch_count_fy_mon_daily AS
 SELECT d.fy,
    d.month1,
    d.schedule_date,
    d.facility_id,
    d.depot_type,
    d.asset_type,
    d.schedule_code,
    d.daily_cnt,
    m.monthly_cnt,
    d.month_target,
    y.fy_cnt,
    d.cum_month_target,
    d.total_year_target
   FROM ( SELECT vash.fy,
            vash.facility_id,
            vash.depot_type,
            vash.asset_type,
            vash.schedule_code,
            count(*) AS fy_cnt,
            vash.month_target,
            vash.cum_month_target,
            vash.total_year_target
           FROM v_assets_schedule_history_targets vash
          GROUP BY vash.fy, vash.facility_id, vash.depot_type, vash.asset_type, vash.schedule_code, vash.month_target, vash.cum_month_target, vash.total_year_target) y,
    ( SELECT vash.fy,
            vash.month1,
            vash.facility_id,
            vash.depot_type,
            vash.asset_type,
            vash.schedule_code,
            count(*) AS monthly_cnt,
            vash.month_target,
            vash.cum_month_target,
            vash.total_year_target
           FROM v_assets_schedule_history_targets vash
          GROUP BY vash.fy, vash.month1, vash.facility_id, vash.depot_type, vash.asset_type, vash.schedule_code, vash.month_target, vash.cum_month_target, vash.total_year_target) m,
    ( SELECT vash.fy,
            vash.month1,
            vash.schedule_date,
            vash.facility_id,
            vash.depot_type,
            vash.asset_type,
            vash.schedule_code,
            count(*) AS daily_cnt,
            vash.month_target,
            vash.cum_month_target,
            vash.total_year_target
           FROM v_assets_schedule_history_targets vash
          GROUP BY vash.fy, vash.month1, vash.schedule_date, vash.facility_id, vash.depot_type, vash.asset_type, vash.schedule_code, vash.month_target, vash.cum_month_target, vash.total_year_target) d
  WHERE d.month1 = m.month1 AND d.facility_id::text = m.facility_id::text AND d.depot_type::text = m.depot_type::text AND d.asset_type::text = m.asset_type::text AND d.schedule_code::text = m.schedule_code::text AND d.fy = y.fy AND d.facility_id::text = y.facility_id::text AND d.depot_type::text = y.depot_type::text AND d.asset_type::text = y.asset_type::text AND d.schedule_code::text = y.schedule_code::text;

ALTER TABLE public.v_asset_sch_count_fy_mon_daily
    OWNER TO postgres;


--18
-- View: v_assets_schedule_counts
-- View: public.v_assets_schedule_counts

-- DROP VIEW public.v_assets_schedule_counts;

CREATE OR REPLACE VIEW public.v_assets_schedule_counts AS
 SELECT to_char(vash.schedule_date, 'dd-Mon-yyyy'::text)::date AS schedule_date,
    vash.asset_type,
    vash.schedule_code,
    count(*) AS sch_done_count,
    vash.fy,
    vash.facility_id,
    vash.facility_name,
    vash.facility_type_id,
    vash.depot_type,
    vash.month1,
    vash.year1,
    vash.week1
   FROM v_assets_schedule_history vash,
    v_monthly_cum_targets vmct
  WHERE vmct.asset_type::text = vash.asset_type::text AND vmct.schedule_type::text = vash.schedule_code::text AND vmct.facility_id::text = vash.facility_id::text AND vmct.fy = vash.fy
  GROUP BY vash.fy, vash.month1, vash.year1, vash.week1, vash.schedule_date, vash.asset_type, vash.schedule_code, vash.facility_id, vash.facility_name, vash.facility_type_id, vash.depot_type;

ALTER TABLE public.v_assets_schedule_counts
    OWNER TO postgres;



--19
-- View: v_asset_monthly_targets_div
-- View: public.v_asset_monthly_targets_div

-- DROP VIEW public.v_asset_monthly_targets_div;

CREATE OR REPLACE VIEW public.v_asset_monthly_targets_div AS
 SELECT vamt.asset_type,
    vamt.schedule_type,
    vamt.year AS fy,
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

ALTER TABLE public.v_asset_monthly_targets_div
    OWNER TO postgres;



--20
-- View: v_sch_all_days_done_run_count_fac_asstype_sch
-- View: public.v_sch_all_days_done_run_count_fac_asstype_sch

-- DROP VIEW public.v_sch_all_days_done_run_count_fac_asstype_sch;

CREATE OR REPLACE VIEW public.v_sch_all_days_done_run_count_fac_asstype_sch AS
 SELECT vasc1.days_fy,
    vasc1.schedule_date,
    vasc1.facility_id,
    (vasc1.asset_type::text || '_'::text) || vasc1.schedule_code::text AS at_st,
    vasc1.asset_type,
    vasc1.schedule_code,
    vasc1.is_dpr,
    vasc1.facility_name,
    vasc1.fy,
    vasc1.month1,
    vasc1.week1,
    vasc1.day_count,
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
   FROM ( SELECT ads.days_fy,
            ads.is_dpr,
            date_part('year'::text, ads.days_fy) AS year1,
            date_part('month'::text, ads.days_fy) AS month1,
            date_part('week'::text, ads.days_fy) AS week1,
                CASE
                    WHEN date_part('month'::text, ads.days_fy) = 1::double precision OR date_part('month'::text, ads.days_fy) = 2::double precision OR date_part('month'::text, ads.days_fy) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (ads.days_fy - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, ads.days_fy)::integer, 100), '99'::text))
                    WHEN date_part('month'::text, ads.days_fy) = 4::double precision OR date_part('month'::text, ads.days_fy) = 5::double precision OR date_part('month'::text, ads.days_fy) = 6::double precision OR date_part('month'::text, ads.days_fy) = 7::double precision OR date_part('month'::text, ads.days_fy) = 8::double precision OR date_part('month'::text, ads.days_fy) = 9::double precision OR date_part('month'::text, ads.days_fy) = 10::double precision OR date_part('month'::text, ads.days_fy) = 11::double precision OR date_part('month'::text, ads.days_fy) = 12::double precision THEN (btrim(to_char(date_part('year'::text, ads.days_fy), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (ads.days_fy + '1 year'::interval)::date)::integer, 100), '99'::text))
                    ELSE NULL::text
                END AS fy,
            ads.facility_id,
            ads.facility_name,
            ads.asset_type,
            ads.schedule_code,
            vasc.schedule_date,
                CASE
                    WHEN vasc.sch_done_count IS NULL THEN 0::bigint
                    ELSE vasc.sch_done_count
                END AS day_count
           FROM v_assets_schedule_counts vasc
             RIGHT JOIN ( SELECT generate_series(se.first_date::timestamp with time zone, se.last_date::timestamp with time zone, '1 day'::interval)::date AS days_fy,
                    asa.asset_type,
                    asa.schedule_code,
                    asa.duration,
                    asa.uom_of_duration,
                    asa.is_dpr,
                    asa.description,
                    asa.sequence_code,
                    ohe_fac.facility_id,
                    ohe_fac.facility_name
                   FROM ( SELECT min(assets_schedule_history.schedule_date)::date AS first_date,
                            max(assets_schedule_history.schedule_date)::date AS last_date
                           FROM assets_schedule_history) se,
                    ( SELECT asset_schedule_assoc.asa_seq_id,
                            asset_schedule_assoc.asset_type,
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
                    ( SELECT facility.facility_id,
                            facility.facility_name
                           FROM facility
                          WHERE facility.depot_type::text = 'OHE'::text) ohe_fac) ads ON ads.asset_type::text = vasc.asset_type::text AND ads.schedule_code::text = vasc.schedule_code::text AND ads.days_fy = vasc.schedule_date AND ads.facility_id::text = vasc.facility_id::text) vasc1
     LEFT JOIN v_monthly_cum_targets vmct ON vmct.asset_type::text = vasc1.asset_type::text AND vmct.schedule_type::text = vasc1.schedule_code::text AND vmct.facility_id::text = vasc1.facility_id::text AND vmct.fy = vasc1.fy
  GROUP BY vasc1.facility_id, vasc1.asset_type, vasc1.schedule_code, vasc1.facility_name, vasc1.fy, vasc1.month1, vasc1.year1, vasc1.week1, vasc1.schedule_date, vasc1.day_count, (
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
        END), (
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
        END), vasc1.days_fy, vasc1.is_dpr;

ALTER TABLE public.v_sch_all_days_done_run_count_fac_asstype_sch
    OWNER TO postgres;



---21
-- View: v_sch_done_daily_run_count_fac_asstype_sch
-- View: public.v_sch_done_daily_run_count_fac_asstype_sch

-- DROP VIEW public.v_sch_done_daily_run_count_fac_asstype_sch;

CREATE OR REPLACE VIEW public.v_sch_done_daily_run_count_fac_asstype_sch AS
 SELECT vasc.schedule_date,
    vasc.facility_id,
    vasc.asset_type,
    vasc.schedule_code,
    vasc.facility_name,
    vasc.fy,
    vasc.month1,
    vasc.week1,
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
   FROM v_assets_schedule_counts vasc,
    v_monthly_cum_targets vmct
  WHERE vmct.asset_type::text = vasc.asset_type::text AND vmct.schedule_type::text = vasc.schedule_code::text AND vmct.facility_id::text = vasc.facility_id::text AND vmct.fy = vasc.fy
  GROUP BY vasc.facility_id, vasc.asset_type, vasc.schedule_code, vasc.facility_name, vasc.fy, vasc.month1, vasc.year1, vasc.week1, vasc.schedule_date, vasc.sch_done_count, (
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
        END), (
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
        END);

ALTER TABLE public.v_sch_done_daily_run_count_fac_asstype_sch
    OWNER TO postgres;



--22
-- View: v_subdivisions

/*
CREATE OR REPLACE VIEW v_subdivisions AS 
 SELECT fsd.facility_name AS sub_division, f.facility_name AS depot_name, 
    f.facility_id, fa.facility_id_to, f.facility_type_id, f.depot_type
   FROM facility_assoc fa
   LEFT JOIN facility f ON f.facility_id::text = fa.facility_id_to::text
   LEFT JOIN facility fsd ON fsd.facility_id::text = fa.facility_id::text
  WHERE fa.facility_assoc_type_id::text = 'SUBDIVISION_UNIT'::text;
*/

--23
-- View: v_power_block_switch_details

-- View: public.v_power_block_switch_details

-- DROP VIEW public.v_power_block_switch_details;

CREATE OR REPLACE VIEW public.v_power_block_switch_details AS
 SELECT pb.pb_operation_seq_id,
    pb.created_date AS pb_date,
    pb.req_department,
    pb.facility_id,
    fac.facility_name,
    fac.facility_type_id,
    fac.depot_type,
    pb.type_of_operation,
    pb.elementary_section_code AS es_sec_subsec_code,
    pb.shadow_block,
    pb.section AS pb_over_es_sec_subsec,
    pb.line,
    pb.line2,
    pb.pb_requested_from_date_time,
    pb.pb_requested_thru_date_time,
    pb.pb_granted_from_date_time,
    pb.pb_granted_thru_date_time,
    pb.ptw_availed_from_date_time,
    pb.ptw_availed_thru_date_time,
    pb.tpc_no_ptw_issue,
    pb.field_no_ptw_issue,
    pb.tpc_no_ptw_return,
    pb.field_no_ptw_return,
    pb.req_period,
    pb.reqn_by,
    pb.post,
    pb.switching_station,
    pb.switching_equipment,
    pb.equipment_to_work,
    pb.special_remarks,
    pb.remarks,
    pb.supervisor_incharge,
    pb.current_status,
    pb.tpc_board,
    pb.schedule,
    pb.purpose,
    pb.staff_to_work,
    smh.seq_id,
    smh.pb_operation_seq_id AS smh_pb_operation_seq_id,
    smh.io_location AS smh_io_location,
    smh.io_type AS smh_io_type,
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
   FROM power_blocks pb,
    facility fac,
    switch_maintenence_history smh
  WHERE pb.facility_id::text = fac.facility_id::text AND pb.pb_operation_seq_id::text = smh.pb_operation_seq_id::text;

ALTER TABLE public.v_power_block_switch_details
    OWNER TO postgres;


--24

--view: v_power_block_depot_daily_summary;
-- View: public.v_power_block_depot_daily_summary

-- DROP VIEW public.v_power_block_depot_daily_summary;

CREATE OR REPLACE VIEW public.v_power_block_depot_daily_summary AS
 SELECT v_power_block_switch_details.req_department,
    v_power_block_switch_details.facility_name,
    v_power_block_switch_details.depot_type,
    v_power_block_switch_details.current_status,
    count(*) AS cnt,
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
  GROUP BY v_power_block_switch_details.req_department, v_power_block_switch_details.depot_type, v_power_block_switch_details.line, v_power_block_switch_details.current_status, v_power_block_switch_details.facility_name, v_power_block_switch_details.issued_not_issued, v_power_block_switch_details.pb_date, (
        CASE
            WHEN date_part('month'::text, v_power_block_switch_details.pb_date) = 1::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 2::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (v_power_block_switch_details.pb_date - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, v_power_block_switch_details.pb_date)::integer, 100), '99'::text))
            WHEN date_part('month'::text, v_power_block_switch_details.pb_date) = 4::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 5::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 6::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 7::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 8::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 9::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 10::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 11::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 12::double precision THEN (btrim(to_char(date_part('year'::text, v_power_block_switch_details.pb_date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (v_power_block_switch_details.pb_date + '1 year'::interval)::date)::integer, 100), '99'::text))
            ELSE NULL::text
        END), (date_part('year'::text, v_power_block_switch_details.pb_date)), (date_part('month'::text, v_power_block_switch_details.pb_date)), (date_part('week'::text, v_power_block_switch_details.pb_date)), (to_char(v_power_block_switch_details.pb_date, 'yyyy-mm-dd'::text));

ALTER TABLE public.v_power_block_depot_daily_summary
    OWNER TO postgres;


--25
-- View: DROP VIEW v_switch_maintenence_history_message_final
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



--26
-- View: VIEW v_power_blocks_amendment_message_final

-- View: public.v_power_blocks_amendment_message_final

-- DROP VIEW public.v_power_blocks_amendment_message_final;

CREATE OR REPLACE VIEW public.v_power_blocks_amendment_message_final AS
 SELECT pb.pb_operation_seq_id,
    pb.created_date,
    fsw.field_operated_switch_list,
    pb.facility_id,
    fac.facility_name,
    fac.facility_type_id,
    fac.depot_type,
    pb.type_of_operation,
    pb.shadow_block,
    pb.elementary_section_code,
    pb.grant_period,
    pb.section,
    pb.line,
    pb.line2,
        CASE
            WHEN pb.line IS NULL OR pb.line2 IS NULL THEN 'UP/DN/UD/UP&DN'::text
            ELSE (pb.line::text || '/'::text) || pb.line2::text
        END AS pb_section_lines,
    pb.req_department,
    pb.req_period,
    pb.reqn_by,
        CASE
            WHEN pba.ptw_availed_from_date_time IS NULL THEN pb.ptw_availed_from_date_time
            ELSE pba.ptw_availed_from_date_time
        END AS ptw_availed_from_date_time,
        CASE
            WHEN pba.ptw_availed_from_date_time IS NULL THEN
            CASE
                WHEN pb.ptw_availed_from_date_time IS NULL THEN pb.ptw_availed_from_date_time
                ELSE pb.ptw_availed_from_date_time + ((pb.grant_period::integer || ' Minutes'::text)::interval)
            END
            ELSE
            CASE
                WHEN pba.ptw_availed_from_date_time IS NOT NULL THEN pba.ptw_availed_from_date_time + ((pb.grant_period::integer || ' Minutes'::text)::interval)
                ELSE pba.ptw_availed_from_date_time
            END
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
    pb.purpose,
    pb.pb_requested_from_date_time,
    pb.pb_requested_thru_date_time,
    pb.pb_granted_from_date_time,
    pb.pb_granted_thru_date_time,
    pb.staff_to_work,
    pb.post,
    pb.switching_station,
    pb.switching_equipment,
    pb.equipment_to_work,
    pb.special_remarks,
    pb.remarks,
    pb.tpc_board,
    pb.schedule,
    pb.supervisor_incharge,
    pb.current_status,
    pb.created_on,
    pb.created_by,
    pb.last_updated_stamp,
    pb.last_updated_tx_stamp,
    pb.created_stamp,
    pb.created_tx_stamp,
    pba.pb_operation_seq_id AS pba_operation_seq_id,
    pba.pb_amendment_seq_id,
    pba.created_by AS pba_created_by,
    pba.updated_by AS pba_updated_by,
    pba.delete AS pba_delete,
    pba.last_updated_stamp AS pba_last_updated_stamp,
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
     LEFT JOIN power_blocks_amendment pba ON pba.pb_operation_seq_id::text = pb.pb_operation_seq_id::text AND pba.delete::text = 'false'::text
     LEFT JOIN ( SELECT pb_1.pb_operation_seq_id,
            pb_1.ptw_availed_thru_date_time,
            pb_1.ptw_availed_from_date_time,
            sw.field_operated_switch_list
           FROM power_blocks pb_1
             LEFT JOIN ( SELECT switch_maintenence_history.pb_operation_seq_id,
                    switch_maintenence_history.is_field_operated,
                    string_agg(switch_maintenence_history.io_location::text, ', '::text) AS field_operated_switch_list
                   FROM switch_maintenence_history
                  WHERE switch_maintenence_history.is_field_operated::text = 'true'::text OR switch_maintenence_history.is_field_operated::text = 'TRUE'::text
                  GROUP BY switch_maintenence_history.pb_operation_seq_id, switch_maintenence_history.is_field_operated) sw ON pb_1.pb_operation_seq_id::text = sw.pb_operation_seq_id::text) fsw ON fsw.pb_operation_seq_id::text = pb.pb_operation_seq_id::text,
    facility fac
  WHERE pb.facility_id::text = fac.facility_id::text;

ALTER TABLE public.v_power_blocks_amendment_message_final
    OWNER TO postgres;


  
--27
-- View: v_product_qty_uom_params  
-- View: public.v_product_qty_uom_params

-- DROP VIEW public.v_product_qty_uom_params;

CREATE OR REPLACE VIEW public.v_product_qty_uom_params AS
 SELECT product.product_id,
    product.quantity_uom_id,
        CASE product.quantity_uom_id
            WHEN 'WT_kg'::text THEN 3
            WHEN 'OTH_no'::text THEN 0
            WHEN 'LEN_m'::text THEN 3
            WHEN 'LEN_km'::text THEN 3
            WHEN 'LEN_ft'::text THEN 2
            WHEN 'LEN_yd'::text THEN 2
            WHEN 'VLIQ_L'::text THEN 3
            ELSE NULL::integer
        END AS decimals_no,
        CASE product.quantity_uom_id
            WHEN 'WT_kg'::text THEN 'Kilograms '::text
            WHEN 'OTH_no'::text THEN 'NOs '::text
            WHEN 'LEN_m'::text THEN ' Meters '::text
            WHEN 'LEN_km'::text THEN 'KM '::text
            WHEN 'LEN_ft'::text THEN ' Feet '::text
            WHEN 'LEN_yd'::text THEN ' Yards '::text
            WHEN 'VLIQ_L'::text THEN ' Liters '::text
            ELSE NULL::text
        END AS unit1,
        CASE product.quantity_uom_id
            WHEN 'WT_kg'::text THEN 'Grams '::text
            WHEN 'OTH_no'::text THEN ' '::text
            WHEN 'LEN_m'::text THEN 'centimeters '::text
            WHEN 'LEN_km'::text THEN ' Meters '::text
            WHEN 'LEN_ft'::text THEN ' Inches '::text
            WHEN 'LEN_yd'::text THEN ' Feet '::text
            WHEN 'VLIQ_L'::text THEN ' Milli liters '::text
            ELSE NULL::text
        END AS subunit1
   FROM product;

ALTER TABLE public.v_product_qty_uom_params
    OWNER TO postgres;


----28

	-- View: public.v_cb_trip_daily_eqp_cnt

-- DROP VIEW public.v_cb_trip_daily_eqp_cnt;

CREATE OR REPLACE VIEW public.v_cb_trip_daily_eqp_cnt AS
 SELECT f.type_of_failure,
    to_char(f.from_date_time, 'yyyy-mm-dd'::text) AS date1,
    fac.facility_name,
    f.equipment,
    f.asset_id,
    count(*) AS daily_count,
    ( SELECT count(*) AS tripped_identified_fault
           FROM failures f1
          WHERE to_char(f1.from_date_time, 'yyyy-mm-dd'::text) >= '1990-01-01'::text AND f1.type_of_failure::text = 'POWER_FAILURE'::text AND (f1.tripped_identified_fault::text = 'false'::text OR f1.tripped_identified_fault IS NULL) AND f1.sub_station::text = f.sub_station::text AND f1.equipment::text = f.equipment::text) AS transient_trip_count,
    ( SELECT count(*) AS tripped_identified_fault
           FROM failures f1
          WHERE to_char(f1.from_date_time, 'yyyy-mm-dd'::text) >= '1990-01-01'::text AND f1.type_of_failure::text = 'POWER_FAILURE'::text AND f1.tripped_identified_fault::text = 'true'::text AND f1.sub_station::text = f.sub_station::text AND f1.equipment::text = f.equipment::text) AS identified_trip_count,
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
  WHERE to_char(f.from_date_time, 'yyyy-mm-dd'::text) >= '1990-01-01'::text AND f.type_of_failure::text = 'POWER_FAILURE'::text
  GROUP BY f.asset_id, (date_part('year'::text, f.from_date_time)), (date_part('month'::text, f.from_date_time)), (date_part('week'::text, f.from_date_time)), (to_char(f.from_date_time, 'yyyy-mm-dd'::text)), (
        CASE
            WHEN date_part('month'::text, f.from_date_time) = 1::double precision OR date_part('month'::text, f.from_date_time) = 2::double precision OR date_part('month'::text, f.from_date_time) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (f.from_date_time - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, f.from_date_time)::integer, 100), '99'::text))
            WHEN date_part('month'::text, f.from_date_time) = 4::double precision OR date_part('month'::text, f.from_date_time) = 5::double precision OR date_part('month'::text, f.from_date_time) = 6::double precision OR date_part('month'::text, f.from_date_time) = 7::double precision OR date_part('month'::text, f.from_date_time) = 8::double precision OR date_part('month'::text, f.from_date_time) = 9::double precision OR date_part('month'::text, f.from_date_time) = 10::double precision OR date_part('month'::text, f.from_date_time) = 11::double precision OR date_part('month'::text, f.from_date_time) = 12::double precision THEN (btrim(to_char(date_part('year'::text, f.from_date_time), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (f.from_date_time + '1 year'::interval)::date)::integer, 100), '99'::text))
            ELSE NULL::text
        END), f.type_of_failure, f.facility_id, fac.facility_name, fac.facility_type_id, fac.depot_type, f.sub_station, f.equipment, (to_char(f.created_date, 'YYYY'::text)), (to_char(f.created_date, 'MM'::text));

ALTER TABLE public.v_cb_trip_daily_eqp_cnt
    OWNER TO postgres;


   
   --29 View: v_asset_schedule_activity_record
   
	-- View: public.v_asset_schedule_activity_record

-- DROP VIEW public.v_asset_schedule_activity_record;

CREATE OR REPLACE VIEW public.v_asset_schedule_activity_record AS
 SELECT row_number() OVER () AS s_no,
    asar.asset_id,
    amd.asset_type,
    asar.schedule_code,
    asar.schedule_date,
    asar.schedule_actual_date,
    asar.status,
    ash.status AS ash_status,
    asar.details_of_maint,
    ash.details_of_maint AS ash_details_of_maint,
    asar.done_by,
    ash.done_by AS ash_done_by,
    asar.remarks,
    ash.remarks AS ash_remarks,
    amd.kilometer,
    amd.position_id,
    amd.location_position,
    amd.capacity_rating AS capacity,
    amd.part1 AS span,
    asar.status AS measure_entry_status,
    f.facility_name,
    f.depot_type,
    amd.make,
    amd.model,
    amd.oem_serial,
    amd.section,
    amd.date_of_manufacture,
    amd.date_of_commision,
    amd.stagger,
    amd.stagger1,
    amd.stagger2,
    amd.stagger3,
    amd.stay1_insulator_make,
    amd.stay2_insulator_make,
    amd.stay3_insulator_make,
    amd.bracket1_insulator_make,
    amd.bracket2_insulator_make,
    amd.bracket3_insulator_make,
    amd.stag1_ton9_insulator_make,
    amd.stag2_ton9_insulator_make,
    amd.stag3_ton9_insulator_make,
    amd.structure AS type_of_mast,
    amd.implantation,
    amd.line,
    asar.m1,
    asar.m2,
    asar.m3,
    asar.m4,
    asar.m5,
    asar.m6,
    asar.m7,
    asar.m8,
    asar.m9,
    asar.m10,
    asar.m11,
    asar.m12,
    asar.m13,
    asar.m14,
    asar.m15,
    asar.m16,
    asar.m17,
    asar.m18,
    asar.m19,
    asar.m20,
    asar.m21,
    asar.m22,
    asar.a1,
    asar.a2,
    asar.a3,
    asar.a4,
    asar.a5,
    asar.a6,
    asar.a7,
    asar.a8,
    asar.a9,
    asar.a10,
    asar.a11,
    asar.a12,
    asar.a13,
    asar.a14,
    asar.a15,
    asar.a16,
    asar.a17,
    asar.a18,
    asar.a19,
    asar.a20,
    asar.a21,
    asar.a22,
    asar.m23,
    asar.m24,
    asar.m25,
    asar.m26,
    asar.m27,
    asar.m28,
    asar.m29,
    asar.m30,
    asar.m31,
    asar.m32,
    asar.m33,
    asar.m34,
    asar.m35,
    asar.m36,
    asar.m37,
    asar.m38,
    asar.m39,
    asar.m40,
    asar.m41,
    asar.m42,
    asar.m43,
    asar.m44,
    asar.m45,
    asar.m46,
    asar.m47,
    asar.m48,
    asar.m49,
    asar.m50,
    asar.a23,
    asar.a24,
    asar.a25,
    asar.a26,
    asar.a27,
    asar.a28,
    asar.a29,
    asar.a30,
    asar.a31,
    asar.a32,
    asar.a33,
    asar.a34,
    asar.a35,
    asar.a36,
    asar.a37,
    asar.a38,
    asar.a39,
    asar.a40,
    asar.a41,
    asar.a42,
    asar.a43,
    asar.a44,
    asar.a45,
    asar.a46,
    asar.a47,
    asar.a48,
    asar.a49,
    asar.a50,
    asar.mma1,
    asar.mma2,
    asar.mma3,
    asar.mma4,
    asar.mma5,
    asar.mma6,
    asar.mma7,
    asar.mma8,
    asar.mma9,
    asar.mma10,
    asar.m51,
    asar.m52,
    asar.m53,
    asar.m54,
    asar.m55,
    asar.m56,
    asar.m57,
    asar.m58,
    asar.m59,
    asar.m60,
    asar.m61,
    asar.m62,
    asar.m63,
    asar.m64,
    asar.m65,
    asar.m66,
    asar.m67,
    asar.m68,
    asar.m69,
    asar.m70,
    asar.a51,
    asar.a52,
    asar.a53,
    asar.a54,
    asar.a55,
    asar.a56,
    asar.a57,
    asar.a58,
    asar.a59,
    asar.a60,
    asar.a61,
    asar.a62,
    asar.a63,
    asar.a64,
    asar.a65,
    asar.a66,
    asar.a67,
    asar.a68,
    asar.a69,
    asar.a70,
    asar.a71,
    asar.a72,
    asar.a73,
    asar.a74,
    asar.a75,
    asar.a76,
    asar.a77,
    asar.a78,
    asar.a79,
    asar.a80,
    asar.a81,
    asar.a82,
    asar.a83,
    asar.a84,
    asar.a85,
    asar.a86,
    asar.a87,
    asar.a88,
    asar.a89,
    asar.a90,
    asar.a91,
    asar.a92,
    asar.a93,
    asar.a94,
    asar.a95,
    asar.a96,
    asar.a97,
    asar.a98,
    asar.a99,
    asar.a100,
    asar.a101,
    asar.a102,
    asar.a103,
    asar.a104,
    asar.a105,
    asar.a106,
    asar.a107,
    asar.a108,
    asar.a109,
    asar.a110,
    asar.a111,
    asar.a112,
    asar.a113,
    asar.a114,
    asar.a115,
    asar.a116,
    asar.a117,
    asar.a118,
    asar.a119,
    asar.a120,
    asar.a121,
    asar.a122,
    asar.a123,
    asar.a124,
    asar.a125,
    asar.a126,
    asar.a127,
    asar.a128,
    asar.a129,
    asar.a130,
    f.facility_id
   FROM asset_schedule_activity_record asar,
    asset_master_data amd,
    facility f,
    assets_schedule_history ash
  WHERE asar.asset_id::text = amd.asset_id::text AND asar.asset_type::text = amd.asset_type::text AND asar.facility_id::text = f.facility_id::text AND asar.facility_id::text = amd.facility_id::text AND ash.seq_id::text = asar.asset_schedule_history_id::text;

ALTER TABLE public.v_asset_schedule_activity_record
    OWNER TO postgres;

----30
-- View: public.v_job_status

-- DROP VIEW public.v_job_status;

CREATE OR REPLACE VIEW public.v_job_status AS
 SELECT js.job_status_id,
    js.created_date,
    js.is_active,
    js.job_status_name,
    js.modified_date,
    js.created_by,
    u1.username AS created_user,
    js.modified_by,
    u2.username AS modified_user
   FROM job_status js
     LEFT JOIN users u1 ON js.created_by = u1.id
     LEFT JOIN users u2 ON js.modified_by = u2.id;

ALTER TABLE public.v_job_status
    OWNER TO postgres;

--31
        
-- View: public.v_jobs_history

-- DROP VIEW public.v_jobs_history;

CREATE OR REPLACE VIEW public.v_jobs_history AS
 SELECT jh.id,
    jh.end_time,
    jh.failed_tables_count,
    jh.job_status,
    jh.operation_id,
    jh.opration_type,
    jh.processed_date,
    jh.start_time,
    jh.status,
    jh.success_tables_count,
    jh.total_tables_count
   FROM jobs_history jh;

ALTER TABLE public.v_jobs_history
    OWNER TO postgres;

--32

-- View: public.v_master_roles

-- DROP VIEW public.v_master_roles;

CREATE OR REPLACE VIEW public.v_master_roles AS
 SELECT mr.id AS master_roles_id,
    mr.created_by,
    u1.username AS created_user,
    mr.created_date,
    mr.modified_by,
    u2.username AS modified_user,
    mr.modified_date,
    mr.role_name,
    mr.status_id
   FROM master_roles mr
     LEFT JOIN users u1 ON mr.created_by = u1.id
     LEFT JOIN users u2 ON mr.modified_by = u2.id;

ALTER TABLE public.v_master_roles
    OWNER TO postgres;

--33

-- View: public.v_menu

-- DROP VIEW public.v_menu;

CREATE OR REPLACE VIEW public.v_menu AS
 SELECT m.id AS menu_id,
    m.created_by,
    u1.username AS created_user,
    m.created_date,
    m.menu,
    m.modified_by,
    u2.username AS modified_user,
    m.modified_date,
    m.status_id AS menu_status_id,
    m.sub_menu
   FROM menu m
     LEFT JOIN users u1 ON m.created_by = u1.id
     LEFT JOIN users u2 ON m.modified_by = u2.id;

ALTER TABLE public.v_menu
    OWNER TO postgres;

--34

-- View: public.v_page_role_permission

-- DROP VIEW public.v_page_role_permission;

CREATE OR REPLACE VIEW public.v_page_role_permission AS
 SELECT prp.id AS prp_id,
    prp.page,
    prp.status AS prp_status,
    prp.master_role_id,
    mr.role_name AS mr_role_name,
    mr.status_id AS mr_status_id,
    prp.menu_id,
    m.menu AS m_menu,
    m.sub_menu AS m_sub_menu,
    m.status_id AS m_status_id,
    prp.permission_id,
    p.permission AS p_permission,
    p.status_id AS p_status_id,
    prp.created_by,
    prp.modified_by
   FROM page_role_permission prp
     LEFT JOIN master_roles mr ON prp.master_role_id = mr.id
     LEFT JOIN menu m ON prp.menu_id = m.id
     LEFT JOIN permissions p ON prp.permission_id = p.id;

ALTER TABLE public.v_page_role_permission
    OWNER TO postgres;



---35

-- View: public.v_submenu

-- DROP VIEW public.v_submenu;

CREATE OR REPLACE VIEW public.v_submenu AS
 SELECT sm.id AS submenu_id,
    sm.created_by,
    u1.username AS created_user,
    sm.created_date,
    sm.menu_id,
    sm.modified_by,
    u2.username AS modified_user,
    sm.modified_date,
    sm.orders,
    sm.status_id,
    sm.sub_menu,
    sm.sub_menu_icon,
    sm.sub_menu_url
   FROM submenu sm
     LEFT JOIN users u1 ON sm.created_by = u1.id
     LEFT JOIN users u2 ON sm.modified_by = u2.id;

ALTER TABLE public.v_submenu
    OWNER TO postgres;

---36

-- View: public.v_user_roles

-- DROP VIEW public.v_user_roles;

CREATE OR REPLACE VIEW public.v_user_roles AS
 SELECT ur.user_id,
    u.username,
    mr.role_name,
    ur.master_role_id
   FROM user_roles ur
     LEFT JOIN master_roles mr ON mr.id = ur.master_role_id
     LEFT JOIN users u ON u.id = ur.user_id;

ALTER TABLE public.v_user_roles
    OWNER TO postgres;

----37

-- View: public.v_depot_all_category_item_qoh

-- DROP VIEW public.v_depot_all_category_item_qoh;

CREATE OR REPLACE VIEW public.v_depot_all_category_item_qoh AS
 SELECT d_c_p.depot_name,
    q.facility_name AS qty_depot_name,
    d_c_p.product_category_id,
    d_c_p.product_id,
    d_c_p.p_description,
    d_c_p.abbreviation,
    d_c_p.u_description,
    d_c_p.quantity_uom_id,
    d_c_p.facility_id,
    d_c_p.subdiv,
    d_c_p.div,
    d_c_p.zone,
        CASE
            WHEN q.qoh IS NULL THEN 0::numeric
            ELSE q.qoh
        END AS qoh,
    'QoH By Depot'::text AS header,
    ((d_c_p.product_id::text || ' ( '::text) ||
        CASE
            WHEN d_c_p.p_description IS NULL THEN '--'::character varying
            ELSE d_c_p.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM ( SELECT f.depot_name,
            pcm.product_category_id,
            pcm.product_id,
            pcm.p_description,
            pcm.abbreviation,
            pcm.u_description,
            pcm.quantity_uom_id,
            f.facility_id,
            f.subdiv,
            f.div,
            f.zone
           FROM ( SELECT DISTINCT f_1.zone,
                    f_1.division AS div,
                    f_1.sub_division AS subdiv,
                    f_1.facility_name AS depot_name,
                    f_1.facility_id
                   FROM facility f_1
                  WHERE f_1.facility_type_id::text = 'WAREHOUSE'::text AND (f_1.facility_name::text ~~ '%OHE'::text OR f_1.facility_name::text ~~ '%PSI'::text OR f_1.facility_name::text ~~ '%TRD'::text OR f_1.facility_name::text ~~ '%RCC%'::text)) f,
            ( SELECT pcm_1.product_category_id,
                    pcm_1.product_id,
                    p_u.p_description,
                    p_u.abbreviation,
                    p_u.u_description,
                    p_u.quantity_uom_id
                   FROM product_category_member pcm_1,
                    ( SELECT p.product_id,
                                CASE
                                    WHEN p.quantity_uom_id IS NULL THEN ' '::character varying
                                    ELSE p.quantity_uom_id
                                END AS quantity_uom_id,
                                CASE
                                    WHEN u.abbreviation IS NULL THEN ' '::character varying
                                    ELSE u.abbreviation
                                END AS abbreviation,
                                CASE
                                    WHEN p.description IS NULL THEN ' '::character varying
                                    ELSE p.description
                                END AS p_description,
                                CASE
                                    WHEN u.description IS NULL THEN ' '::character varying
                                    ELSE u.description
                                END AS u_description
                           FROM product p
                             LEFT JOIN uom u ON p.quantity_uom_id::text = u.uom_id::text) p_u
                  WHERE p_u.product_id::text = pcm_1.product_id::text) pcm) d_c_p
     LEFT JOIN ( SELECT ii.product_id,
            ii.facility_id,
            round(sum(ii.quantity_on_hand_total), 3) AS qoh,
            f.facility_name,
            f.sub_division,
            f.division,
            f.zone,
            f.data_div
           FROM inventory_item ii,
            product p,
            facility f
          WHERE p.product_id::text = ii.product_id::text AND f.facility_id::text = ii.facility_id::text
          GROUP BY ii.product_id, ii.facility_id, f.facility_name, f.division, f.data_div, f.zone, f.sub_division) q ON d_c_p.product_id::text = q.product_id::text AND q.facility_name::text = d_c_p.depot_name::text AND q.facility_id::text = d_c_p.facility_id::text;

ALTER TABLE public.v_depot_all_category_item_qoh
    OWNER TO postgres;

--38
-- View: public.v_bpp_subdiv_depot_wise_qoh

-- DROP VIEW public.v_bpp_subdiv_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_bpp_subdiv_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'BZA'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'BPP'::text;

ALTER TABLE public.v_bpp_subdiv_depot_wise_qoh
    OWNER TO postgres;

--39
-- View: public.v_bza_depot_wise_qoh

-- DROP VIEW public.v_bza_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_bza_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'BZA'::text;

ALTER TABLE public.v_bza_depot_wise_qoh
    OWNER TO postgres;

--40

-- View: public.v_bza_div_subdivision_wise_qoh

-- DROP VIEW public.v_bza_div_subdivision_wise_qoh;

CREATE OR REPLACE VIEW public.v_bza_div_subdivision_wise_qoh AS
 SELECT sd.product_category_id,
    sd.product_id,
    sd.p_description,
    sd.abbreviation,
    sd.u_description,
    sd.quantity_uom_id,
    sd.div,
    sd.zone,
    sum(
        CASE
            WHEN sd.qoh IS NULL THEN 0::numeric
            ELSE sd.qoh
        END) AS div_qoh,
    sd.material_id_desc,
    sd.subdiv
   FROM ( SELECT v_depot_all_category_item_qoh.qoh,
            v_depot_all_category_item_qoh.depot_name,
            v_depot_all_category_item_qoh.qty_depot_name,
            v_depot_all_category_item_qoh.product_category_id,
            v_depot_all_category_item_qoh.product_id,
            v_depot_all_category_item_qoh.p_description,
            v_depot_all_category_item_qoh.abbreviation,
            v_depot_all_category_item_qoh.u_description,
            v_depot_all_category_item_qoh.quantity_uom_id,
            v_depot_all_category_item_qoh.facility_id,
            v_depot_all_category_item_qoh.subdiv,
            v_depot_all_category_item_qoh.div,
            v_depot_all_category_item_qoh.zone,
            'QoH By Depot'::text AS header,
            ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
                CASE
                    WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
                    ELSE v_depot_all_category_item_qoh.p_description
                END::text) || ' ) '::text AS material_id_desc
           FROM v_depot_all_category_item_qoh
          WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.zone::text) = 'SCR'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'BZA'::text) sd
  GROUP BY sd.product_category_id, sd.product_id, sd.p_description, sd.abbreviation, sd.u_description, sd.quantity_uom_id, sd.subdiv, sd.div, sd.zone, sd.material_id_desc;

ALTER TABLE public.v_bza_div_subdivision_wise_qoh
    OWNER TO postgres;

--41-- View: public.v_bza_subdiv_depot_wise_qoh

-- DROP VIEW public.v_bza_subdiv_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_bza_subdiv_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'BZA'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'BZA'::text;

ALTER TABLE public.v_bza_subdiv_depot_wise_qoh
    OWNER TO postgres;

--42
-- View: public.v_dkj_subdiv_depot_wise_qoh

-- DROP VIEW public.v_dkj_subdiv_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_dkj_subdiv_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'SC'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'DKJ'::text;

ALTER TABLE public.v_dkj_subdiv_depot_wise_qoh
    OWNER TO postgres;

--43

-- View: public.v_gnt_depot_wise_qoh

-- DROP VIEW public.v_gnt_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_gnt_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'GNT'::text;

ALTER TABLE public.v_gnt_depot_wise_qoh
    OWNER TO postgres;



--44
-- View: public.v_gnt_div_subdivision_wise_qoh

-- DROP VIEW public.v_gnt_div_subdivision_wise_qoh;

CREATE OR REPLACE VIEW public.v_gnt_div_subdivision_wise_qoh AS
 SELECT sd.product_category_id,
    sd.product_id,
    sd.p_description,
    sd.abbreviation,
    sd.u_description,
    sd.quantity_uom_id,
    sd.div,
    sd.zone,
    sum(
        CASE
            WHEN sd.qoh IS NULL THEN 0::numeric
            ELSE sd.qoh
        END) AS div_qoh,
    sd.material_id_desc,
    sd.subdiv
   FROM ( SELECT v_depot_all_category_item_qoh.qoh,
            v_depot_all_category_item_qoh.depot_name,
            v_depot_all_category_item_qoh.qty_depot_name,
            v_depot_all_category_item_qoh.product_category_id,
            v_depot_all_category_item_qoh.product_id,
            v_depot_all_category_item_qoh.p_description,
            v_depot_all_category_item_qoh.abbreviation,
            v_depot_all_category_item_qoh.u_description,
            v_depot_all_category_item_qoh.quantity_uom_id,
            v_depot_all_category_item_qoh.facility_id,
            v_depot_all_category_item_qoh.subdiv,
            v_depot_all_category_item_qoh.div,
            v_depot_all_category_item_qoh.zone,
            'QoH By Depot'::text AS header,
            ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
                CASE
                    WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
                    ELSE v_depot_all_category_item_qoh.p_description
                END::text) || ' ) '::text AS material_id_desc
           FROM v_depot_all_category_item_qoh
          WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.zone::text) = 'SCR'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'GNT'::text) sd
  GROUP BY sd.product_category_id, sd.product_id, sd.p_description, sd.abbreviation, sd.u_description, sd.quantity_uom_id, sd.subdiv, sd.div, sd.zone, sd.material_id_desc;

ALTER TABLE public.v_gnt_div_subdivision_wise_qoh
    OWNER TO postgres;



--45

-- View: public.v_gnt_subdiv_depot_wise_qoh

-- DROP VIEW public.v_gnt_subdiv_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_gnt_subdiv_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'GNT'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'GNT'::text;

ALTER TABLE public.v_gnt_subdiv_depot_wise_qoh
    OWNER TO postgres;




---46

-- View: public.v_gtl_depot_wise_qoh

-- DROP VIEW public.v_gtl_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_gtl_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'GTL'::text;

ALTER TABLE public.v_gtl_depot_wise_qoh
    OWNER TO postgres;


--47
-- View: public.v_gtl_div_subdivision_wise_qoh

-- DROP VIEW public.v_gtl_div_subdivision_wise_qoh;

CREATE OR REPLACE VIEW public.v_gtl_div_subdivision_wise_qoh AS
 SELECT sd.product_category_id,
    sd.product_id,
    sd.p_description,
    sd.abbreviation,
    sd.u_description,
    sd.quantity_uom_id,
    sd.div,
    sd.zone,
    sum(
        CASE
            WHEN sd.qoh IS NULL THEN 0::numeric
            ELSE sd.qoh
        END) AS div_qoh,
    sd.material_id_desc,
    sd.subdiv
   FROM ( SELECT v_depot_all_category_item_qoh.qoh,
            v_depot_all_category_item_qoh.depot_name,
            v_depot_all_category_item_qoh.qty_depot_name,
            v_depot_all_category_item_qoh.product_category_id,
            v_depot_all_category_item_qoh.product_id,
            v_depot_all_category_item_qoh.p_description,
            v_depot_all_category_item_qoh.abbreviation,
            v_depot_all_category_item_qoh.u_description,
            v_depot_all_category_item_qoh.quantity_uom_id,
            v_depot_all_category_item_qoh.facility_id,
            v_depot_all_category_item_qoh.subdiv,
            v_depot_all_category_item_qoh.div,
            v_depot_all_category_item_qoh.zone,
            'QoH By Depot'::text AS header,
            ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
                CASE
                    WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
                    ELSE v_depot_all_category_item_qoh.p_description
                END::text) || ' ) '::text AS material_id_desc
           FROM v_depot_all_category_item_qoh
          WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.zone::text) = 'SCR'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'GTL'::text) sd
  GROUP BY sd.product_category_id, sd.product_id, sd.p_description, sd.abbreviation, sd.u_description, sd.quantity_uom_id, sd.subdiv, sd.div, sd.zone, sd.material_id_desc;

ALTER TABLE public.v_gtl_div_subdivision_wise_qoh
    OWNER TO postgres;


--48

-- View: public.v_gtl_subdiv_depot_wise_qoh

-- DROP VIEW public.v_gtl_subdiv_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_gtl_subdiv_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'GTL'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'GTL'::text;

ALTER TABLE public.v_gtl_subdiv_depot_wise_qoh
    OWNER TO postgres;

--49



-- View: public.v_hx_subdiv_depot_wise_qoh

-- DROP VIEW public.v_hx_subdiv_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_hx_subdiv_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'GTL'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'HX'::text;

ALTER TABLE public.v_hx_subdiv_depot_wise_qoh
    OWNER TO postgres;


--50

-- View: public.v_hyb_depot_wise_qoh

-- DROP VIEW public.v_hyb_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_hyb_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'HYB'::text;

ALTER TABLE public.v_hyb_depot_wise_qoh
    OWNER TO postgres;


--51

-- View: public.v_hyb_div_subdivision_wise_qoh

-- DROP VIEW public.v_hyb_div_subdivision_wise_qoh;

CREATE OR REPLACE VIEW public.v_hyb_div_subdivision_wise_qoh AS
 SELECT sd.product_category_id,
    sd.product_id,
    sd.p_description,
    sd.abbreviation,
    sd.u_description,
    sd.quantity_uom_id,
    sd.div,
    sd.zone,
    sum(
        CASE
            WHEN sd.qoh IS NULL THEN 0::numeric
            ELSE sd.qoh
        END) AS div_qoh,
    sd.material_id_desc,
    sd.subdiv
   FROM ( SELECT v_depot_all_category_item_qoh.qoh,
            v_depot_all_category_item_qoh.depot_name,
            v_depot_all_category_item_qoh.qty_depot_name,
            v_depot_all_category_item_qoh.product_category_id,
            v_depot_all_category_item_qoh.product_id,
            v_depot_all_category_item_qoh.p_description,
            v_depot_all_category_item_qoh.abbreviation,
            v_depot_all_category_item_qoh.u_description,
            v_depot_all_category_item_qoh.quantity_uom_id,
            v_depot_all_category_item_qoh.facility_id,
            v_depot_all_category_item_qoh.subdiv,
            v_depot_all_category_item_qoh.div,
            v_depot_all_category_item_qoh.zone,
            'QoH By Depot'::text AS header,
            ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
                CASE
                    WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
                    ELSE v_depot_all_category_item_qoh.p_description
                END::text) || ' ) '::text AS material_id_desc
           FROM v_depot_all_category_item_qoh
          WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.zone::text) = 'SCR'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'HYB'::text) sd
  GROUP BY sd.product_category_id, sd.product_id, sd.p_description, sd.abbreviation, sd.u_description, sd.quantity_uom_id, sd.subdiv, sd.div, sd.zone, sd.material_id_desc;

ALTER TABLE public.v_hyb_div_subdivision_wise_qoh
    OWNER TO postgres;

--52

-- View: public.v_hyb_subdiv_depot_wise_qoh

-- DROP VIEW public.v_hyb_subdiv_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_hyb_subdiv_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'HYB'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'HYB'::text;

ALTER TABLE public.v_hyb_subdiv_depot_wise_qoh
    OWNER TO postgres;

--53

-- View: public.v_kzj_subdiv_depot_wise_qoh

-- DROP VIEW public.v_kzj_subdiv_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_kzj_subdiv_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'SC'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'KZJ'::text;

ALTER TABLE public.v_kzj_subdiv_depot_wise_qoh
    OWNER TO postgres;

--54-- View: public.v_mrga_subdivision_wise_qoh

-- DROP VIEW public.v_mrga_subdivision_wise_qoh;

CREATE OR REPLACE VIEW public.v_mrga_subdivision_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'GNT'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'MRGA'::text;

ALTER TABLE public.v_mrga_subdivision_wise_qoh
    OWNER TO postgres;



--55

-- View: public.v_mrk_subdivision_wise_qoh

-- DROP VIEW public.v_mrk_subdivision_wise_qoh;

CREATE OR REPLACE VIEW public.v_mrk_subdivision_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'GNT'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'MRK'::text;

ALTER TABLE public.v_mrk_subdivision_wise_qoh
    OWNER TO postgres;

--56-- View: public.v_nlr_bpp_depot_wise_qoh

-- DROP VIEW public.v_nlr_bpp_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_nlr_bpp_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'BZA'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'NLR'::text;

ALTER TABLE public.v_nlr_bpp_depot_wise_qoh
    OWNER TO postgres;


--57




-- View: public.v_rc_subdiv_depot_wise_qoh

-- DROP VIEW public.v_rc_subdiv_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_rc_subdiv_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'GTL'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'RC'::text;

ALTER TABLE public.v_rc_subdiv_depot_wise_qoh
    OWNER TO postgres;

--58
-- View: public.v_rjy_bpp_depot_wise_qoh

-- DROP VIEW public.v_rjy_bpp_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_rjy_bpp_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'BZA'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'RJY'::text;

ALTER TABLE public.v_rjy_bpp_depot_wise_qoh
    OWNER TO postgres;

--59



-- View: public.v_ru_subdiv_depot_wise_qoh

-- DROP VIEW public.v_ru_subdiv_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_ru_subdiv_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'GTL'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'RU'::text;

ALTER TABLE public.v_ru_subdiv_depot_wise_qoh
    OWNER TO postgres;

---60


-- View: public.v_sc_depot_wise_qoh

-- DROP VIEW public.v_sc_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_sc_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'SC'::text;

ALTER TABLE public.v_sc_depot_wise_qoh
    OWNER TO postgres;

---61

-- View: public.v_sc_div_subdivision_wise_qoh

-- DROP VIEW public.v_sc_div_subdivision_wise_qoh;

CREATE OR REPLACE VIEW public.v_sc_div_subdivision_wise_qoh AS
 SELECT sd.product_category_id,
    sd.product_id,
    sd.p_description,
    sd.abbreviation,
    sd.u_description,
    sd.quantity_uom_id,
    sd.div,
    sd.zone,
    sum(
        CASE
            WHEN sd.qoh IS NULL THEN 0::numeric
            ELSE sd.qoh
        END) AS div_qoh,
    sd.material_id_desc,
    sd.subdiv
   FROM ( SELECT v_depot_all_category_item_qoh.qoh,
            v_depot_all_category_item_qoh.depot_name,
            v_depot_all_category_item_qoh.qty_depot_name,
            v_depot_all_category_item_qoh.product_category_id,
            v_depot_all_category_item_qoh.product_id,
            v_depot_all_category_item_qoh.p_description,
            v_depot_all_category_item_qoh.abbreviation,
            v_depot_all_category_item_qoh.u_description,
            v_depot_all_category_item_qoh.quantity_uom_id,
            v_depot_all_category_item_qoh.facility_id,
            v_depot_all_category_item_qoh.subdiv,
            v_depot_all_category_item_qoh.div,
            v_depot_all_category_item_qoh.zone,
            'QoH By Depot'::text AS header,
            ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
                CASE
                    WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
                    ELSE v_depot_all_category_item_qoh.p_description
                END::text) || ' ) '::text AS material_id_desc
           FROM v_depot_all_category_item_qoh
          WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.zone::text) = 'SCR'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'SC'::text) sd
  GROUP BY sd.product_category_id, sd.product_id, sd.p_description, sd.abbreviation, sd.u_description, sd.quantity_uom_id, sd.subdiv, sd.div, sd.zone, sd.material_id_desc;

ALTER TABLE public.v_sc_div_subdivision_wise_qoh
    OWNER TO postgres;



--62
-- View: public.v_sc_subdiv_depot_wise_qoh

-- DROP VIEW public.v_sc_subdiv_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_sc_subdiv_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'SC'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'SC'::text;

ALTER TABLE public.v_sc_subdiv_depot_wise_qoh
    OWNER TO postgres;

--63

-- View: public.v_scr_division_wise_qoh

-- DROP VIEW public.v_scr_division_wise_qoh;

CREATE OR REPLACE VIEW public.v_scr_division_wise_qoh AS
 SELECT sd.product_category_id,
    sd.product_id,
    sd.p_description,
    sd.abbreviation,
    sd.u_description,
    sd.quantity_uom_id,
    sd.div,
    sd.zone,
    sum(
        CASE
            WHEN sd.qoh IS NULL THEN 0::numeric
            ELSE sd.qoh
        END) AS div_qoh,
    sd.material_id_desc
   FROM ( SELECT v_depot_all_category_item_qoh.qoh,
            v_depot_all_category_item_qoh.depot_name,
            v_depot_all_category_item_qoh.qty_depot_name,
            v_depot_all_category_item_qoh.product_category_id,
            v_depot_all_category_item_qoh.product_id,
            v_depot_all_category_item_qoh.p_description,
            v_depot_all_category_item_qoh.abbreviation,
            v_depot_all_category_item_qoh.u_description,
            v_depot_all_category_item_qoh.quantity_uom_id,
            v_depot_all_category_item_qoh.facility_id,
            v_depot_all_category_item_qoh.subdiv,
            v_depot_all_category_item_qoh.div,
            v_depot_all_category_item_qoh.zone,
            'QoH By Depot'::text AS header,
            ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
                CASE
                    WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
                    ELSE v_depot_all_category_item_qoh.p_description
                END::text) || ' ) '::text AS material_id_desc
           FROM v_depot_all_category_item_qoh
          WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.zone::text) = 'SCR'::text) sd
  GROUP BY sd.product_category_id, sd.product_id, sd.p_description, sd.abbreviation, sd.u_description, sd.quantity_uom_id, sd.div, sd.zone, sd.material_id_desc;

ALTER TABLE public.v_scr_division_wise_qoh
    OWNER TO postgres;

--64

-- View: public.v_skzr_subdiv_depot_wise_qoh

-- DROP VIEW public.v_skzr_subdiv_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_skzr_subdiv_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'SC'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'SKZR'::text;

ALTER TABLE public.v_skzr_subdiv_depot_wise_qoh
    OWNER TO postgres;

---65
-- View: public.v_tdu_subdiv_depot_wise_qoh

-- DROP VIEW public.v_tdu_subdiv_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_tdu_subdiv_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'SC'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'TDU'::text;

ALTER TABLE public.v_tdu_subdiv_depot_wise_qoh
    OWNER TO postgres;

--66

-- View: public.v_tuni_bpp_depot_wise_qoh

-- DROP VIEW public.v_tuni_bpp_depot_wise_qoh;

CREATE OR REPLACE VIEW public.v_tuni_bpp_depot_wise_qoh AS
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'BZA'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'TUNI'::text;

ALTER TABLE public.v_tuni_bpp_depot_wise_qoh
    OWNER TO postgres;

--67---


-- View: public.v_role_permission

-- DROP VIEW public.v_role_permission;

CREATE OR REPLACE VIEW public.v_role_permission AS
 SELECT rp.id AS role_permission_id,
    rp.created_by,
    u1.username AS created_user,
    rp.created_date,
    rp.modified_by,
    u2.username AS modified_user,
    rp.modified_date,
    rp.permission_id,
    p.permission AS permission_code,
    p.status_id AS permission_status,
    rp.role_type_id,
    rt.role_name,
    rt.role_type,
    rt.status_id AS role_type_status_id,
    rp.status_id
   FROM role_permission rp
     LEFT JOIN users u1 ON rp.created_by = u1.id
     LEFT JOIN users u2 ON rp.modified_by = u2.id
     LEFT JOIN permissions p ON rp.permission_id = p.id
     LEFT JOIN roletype rt ON rp.role_type_id = rt.id;

ALTER TABLE public.v_role_permission
    OWNER TO postgres;



--68



-- View: public.v_schedulerjobs

-- DROP VIEW public.v_schedulerjobs;

CREATE OR REPLACE VIEW public.v_schedulerjobs AS
 SELECT sj.job_id,
    sj.created_date,
    sj.is_active AS schedulerjob_is_active,
    sj.last_run_timestamp,
    sj.modified_date,
    sj.created_by,
    u1.username AS created_user,
    sj.job_status_id,
    js.job_status_name,
    sj.job_type_id,
    jt.job_type_name,
    jt.is_active,
    sj.modified_by,
    u2.username AS modified_user,
    sj.repository_id,
    rep.repository_code,
    rep.repository_name,
    rep.is_active AS repository_is_active,
    sj.time_interval_id,
    ti.time_interval,
    ti.is_active AS timeinterval_is_active
   FROM schedulerjobs sj
     LEFT JOIN users u1 ON sj.created_by = u1.id
     LEFT JOIN users u2 ON sj.modified_by = u2.id
     LEFT JOIN jobtypes jt ON sj.job_type_id = jt.job_type_id
     LEFT JOIN timeintervals ti ON sj.time_interval_id = ti.time_interval_id
     LEFT JOIN repository rep ON sj.repository_id = rep.repository_id
     LEFT JOIN job_status js ON sj.job_status_id = js.job_status_id;

ALTER TABLE public.v_schedulerjobs
    OWNER TO postgres;

----69


-- View: public.v_drives

-- DROP VIEW public.v_drives;

CREATE OR REPLACE VIEW public.v_drives AS 
 SELECT drives.id,
    drives.active,
    drives.asset_description,
    drives.asset_type,
    drives.checklist,
    drives.created_by,
    drives.created_on,
    drives.criteria,
    drives.description,
    drives.from_date,
    drives.functional_unit,
    drives.is_id_required,
    drives.frequency,
    drives.name,
    drives.status_id,
        CASE
            WHEN drives.status_id = 1 THEN 'No'::text
            ELSE 'Yes'::text
        END AS drv_deleted_status,
    drives.target_qty,
    drives.to_date,
    drives.updated_by,
    drives.updated_on,
    drives.depot_type
   FROM drives;

ALTER TABLE public.v_drives
  OWNER TO postgres;

  --70

  
-- View: public.v_drive_target

-- DROP VIEW public.v_drive_target;

CREATE OR REPLACE VIEW public.v_drive_target AS 
 SELECT dt.id,
    dt.created_by,
    dt.created_on,
    dt.poulation,
    dt.status_id,
        CASE
            WHEN dt.status_id = 1 THEN 'No'::text
            ELSE 'Yes'::text
        END AS deleted_status,
    dt.target,
    dt.unit_name,
    dt.unit_type,
    dt.updated_by,
    dt.updated_on,
    dt.drive_id,
    d.active AS drv_active,
    d.asset_description AS drv_asset_description,
    d.asset_type AS drv_asset_type,
    d.checklist AS drv_checklist,
    d.created_by AS drv_created_by,
    d.criteria AS drv_criteria,
    d.description AS drv_description,
    d.from_date AS drv_from_date,
    d.frequency AS drv_frequency,
    d.functional_unit AS drv_functional_unit,
    d.is_id_required AS drv_is_id_required,
    d.name AS drv_name,
    d.status_id AS drv_status_id,
    d.target_qty AS drv_target_qty,
    d.to_date AS drv_to_date,
    d.updated_by AS drv_updated_by,
    d.depot_type AS drv_depot_type,
    d.drv_deleted_status,
    d.id AS drv_id
   FROM drive_target dt,
    v_drives d
  WHERE dt.drive_id = d.id;

ALTER TABLE public.v_drive_target
  OWNER TO postgres;


--71
-- View: public.v_drive_daily_progress

-- DROP VIEW public.v_drive_daily_progress;

CREATE OR REPLACE VIEW public.v_drive_daily_progress AS 
 SELECT ddp.id,
    ddp.activity_id,
    ddp.created_by,
    ddp.created_on,
    ddp.depot,
    ddp.division,
    ddp.performed_count,
    ddp.performed_date,
    ddp.section,
    ddp.status_id,
        CASE
            WHEN ddp.status_id = 1 THEN 'No'::text
            ELSE 'Yes'::text
        END AS deleted_status,
    ddp.supervisor,
    ddp.updated_by,
    ddp.updated_on,
    ddp.drive_id,
    d.active AS drv_active,
    d.asset_description AS drv_asset_description,
    d.asset_type AS drv_asset_type,
    d.checklist AS drv_checklist,
    d.created_by AS drv_created_by,
    d.criteria AS drv_criteria,
    d.description AS drv_description,
    d.from_date AS drv_from_date,
    d.frequency AS drv_frequency,
    d.functional_unit AS drv_functional_unit,
    d.is_id_required AS drv_is_id_required,
    d.name AS drv_name,
    d.status_id AS drv_status_id,
    d.target_qty AS drv_target_qty,
    d.to_date AS drv_to_date,
    d.updated_by AS drv_updated_by,
    d.depot_type AS drv_depot_type,
    d.drv_deleted_status,
    d.id AS drv_id
   FROM drive_daily_progress ddp,
    v_drives d
  WHERE ddp.drive_id = d.id;

ALTER TABLE public.v_drive_daily_progress
  OWNER TO postgres;

  ---72
  
-- View: public.v_drive_category_asso

-- DROP VIEW public.v_drive_category_asso;

CREATE OR REPLACE VIEW public.v_drive_category_asso AS 
 SELECT a.dca_id,
    a.dca_active,
    a.created_by,
    a.created_on,
    a.dca_status_id,
    a.dca_deleted_status,
    a.updated_by,
    a.updated_on,
    a.drive_category_id,
    a.drive_category_name,
    a.report_sub_heading,
    drsh.sub_heading,
    a.report_order,
    a.report_display_id,
    a.drive_id,
    a.dc_status_id,
    a.drv_active,
    a.drv_asset_description,
    a.drv_asset_type,
    a.drv_checklist,
    a.drv_created_by,
    a.drv_criteria,
    a.drv_description,
    a.drv_from_date,
    a.drv_frequency,
    a.drv_functional_unit,
    a.drv_is_id_required,
    a.drv_name,
    a.drv_status_id,
    a.drv_target_qty,
    a.drv_to_date,
    a.drv_updated_by,
    a.drv_depot_type,
    a.drv_deleted_status,
    a.drv_id,
    a.dc_id
   FROM ( SELECT dca.id AS dca_id,
            dca.active AS dca_active,
            dca.created_by,
            dca.created_on,
            dca.status_id AS dca_status_id,
                CASE
                    WHEN dca.status_id = 1 THEN 'No'::text
                    ELSE 'Yes'::text
                END AS dca_deleted_status,
            dca.updated_by,
            dca.updated_on,
            dca.drive_category_id,
            dc.drive_category_name,
            dca.report_sub_heading,
            dca.report_order,
            dca.report_display_id,
            dca.drive_id,
            dc.status_id AS dc_status_id,
            d.active AS drv_active,
            d.asset_description AS drv_asset_description,
            d.asset_type AS drv_asset_type,
            d.checklist AS drv_checklist,
            d.created_by AS drv_created_by,
            d.criteria AS drv_criteria,
            d.description AS drv_description,
            d.from_date AS drv_from_date,
            d.frequency AS drv_frequency,
            d.functional_unit AS drv_functional_unit,
            d.is_id_required AS drv_is_id_required,
            d.name AS drv_name,
            d.status_id AS drv_status_id,
            d.target_qty AS drv_target_qty,
            d.to_date AS drv_to_date,
            d.updated_by AS drv_updated_by,
            d.depot_type AS drv_depot_type,
            d.drv_deleted_status,
            d.id AS drv_id,
            dc.id AS dc_id
           FROM drive_category_asso dca,
            drive_category dc,
            v_drives d
          WHERE dca.drive_category_id = dc.id AND dca.drive_id = d.id) a
     LEFT JOIN drive_report_sub_headings drsh ON drsh.id = a.report_sub_heading
  ORDER BY a.drive_category_id, a.report_order;

ALTER TABLE public.v_drive_category_asso
  OWNER TO postgres;
  
  
  
  
  ------73
  
  -- View: public.v_asset_monthly_targets

-- DROP VIEW public.v_asset_monthly_targets;

CREATE OR REPLACE VIEW public.v_asset_monthly_targets AS 
 SELECT amt.seq_id,
    amt.facility_id,
    fac.facility_name,
    fac.facility_type_id,
    fac.depot_type,
    amt.schedule_type,
    amt.asset_type,
    amt.total_population,
    amt.target_jan,
    amt.target_feb,
    amt.target_mar,
    amt.target_apr,
    amt.target_may,
    amt.target_june AS target_jun,
    amt.target_july AS target_jul,
    amt.target_aug,
    amt.target_sep,
    amt.target_oct,
    amt.target_nov,
    amt.target_dec,
    amt.target_apr + amt.target_may AS cum_target_may,
    amt.target_apr + amt.target_may + amt.target_june AS cum_target_jun,
    amt.target_apr + amt.target_may + amt.target_june AS target_qtr1,
    amt.target_apr + amt.target_may + amt.target_june + amt.target_july AS cum_target_jul,
    amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug AS cum_target_aug,
    amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep AS cum_target_sep,
    amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep AS target_qtr2,
    amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct AS cum_target_oct,
    amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov AS cum_target_nov,
    amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec AS cum_target_dec,
    amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec AS target_qtr3,
    amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec + amt.target_jan AS cum_target_jan,
    amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec + amt.target_jan + amt.target_feb AS cum_target_feb,
    amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec + amt.target_jan + amt.target_feb + amt.target_mar AS cum_target_mar,
    amt.target_jan + amt.target_feb + amt.target_mar + amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec AS total_target_year,
    amt.year,
    btrim((amt.year::text || '-'::text) || (((amt.year::integer + 1) % 100)::text)) AS fy
   FROM asset_monthly_targets amt,
    facility fac
  WHERE amt.facility_id::text = fac.facility_id::text;

ALTER TABLE public.v_asset_monthly_targets
  OWNER TO postgres;

------------------74

--drop view v_thermovision_check_points cascade;
--alter table thermovision_check_points add column display_of_temp_diff varchar;

--drop view v_thermovision_check_points cascade;

create view v_thermovision_check_points as
SELECT tcp.id AS tcp_id,
    tcp.facility_id AS tcp_facility_id,
    f.facility_name AS tcp_facility_name,
    f.depot_type AS tcp_station_type,
    tcp.check_point_part AS tcp_check_point_part,
    tcp.check_point_description AS tcp_check_point_description,
    tcp.type_of_check_point AS tcp_type_of_check_point,
    tcp.display_group AS tcp_display_group,
    tcp.display_order AS tcp_display_order,
    tcp.active AS tcp_active,
    tcp.commparison_points AS tcp_commparison_points,
	tcp.display_of_temp_diff as tcp_display_of_temp_diff
   FROM thermovision_check_points tcp
     LEFT JOIN facility f ON tcp.facility_id::text = f.facility_id::text;


------------------75

--drop view v_tcp_schedule cascade;
create view v_tcp_schedule as 
select 
tcps.id as  tcps_id,
tcps.facility_id as  tcps_facility_id,
f.facility_name as tcps_facility_name,
f.depot_type as tcps_station_type ,
location as  tcps_location,
asset_type as  tcps_asset_type,
date_time as  tcps_date,
tcps.date_time AS tcps_date_time,
to_char(tcps.date_time, 'HH:MM'::text) AS tcps_time,
by as  tcps_by,
general_remark as  tcps_general_remark
from tcp_schedule tcps ,  facility f
where tcps.facility_id::text = f.facility_id ;

------------------76

--drop view  v_thermovision_measures ; 
	 
	 
--select * from v_thermovision_measures
--	drop view v_thermovision_measures ;
	create view  v_thermovision_measures as
	 SELECT vtcp.tcp_check_point_part,
    vtcp.tcp_check_point_description,
    vtcp.tcp_type_of_check_point,
    vtcp.tcp_display_group,
    vtcp.tcp_display_order,
    vtcp.tcp_active,
    vtcp.tcp_commparison_points,
	vtcp.tcp_display_of_temp_diff,
	vtcp.tcp_id as tcp_id ,
    tcpm.id AS tcpm_id,
    tcpm.tcp_schedule_id AS tcpm_tcp_schedule_id,
    tcpm.tcp_id AS tcpm_tcp_id,
    tcpm.fixed_measure AS tcpm_fixed_measure,
    tcpm.c_clamp_measure AS tcpm_c_clamp_measure,
    tcpm.ambient_temp AS tcpm_ambient_temp,
    tcpm.image_id AS tcpm_image_id,
    tcpm.remark AS tcpm_remark,
    tcpm.criticality AS tcpm_criticality,
    tcpm.variance_with_other_point AS tcpm_variance_with_other_point,
    vtcps.tcps_facility_id,
    vtcps.tcps_facility_name,
    vtcps.tcps_station_type,
    vtcps.tcps_location,
    vtcps.tcps_asset_type,
    vtcps.tcps_date,
    vtcps.tcps_date_time,
    vtcps.tcps_time
   FROM thermovision_measures tcpm,
    v_tcp_schedule vtcps,
    v_thermovision_check_points vtcp
  WHERE tcpm.tcp_schedule_id = vtcps.tcps_id AND tcpm.tcp_id = vtcp.tcp_id;