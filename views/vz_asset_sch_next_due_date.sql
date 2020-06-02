
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
