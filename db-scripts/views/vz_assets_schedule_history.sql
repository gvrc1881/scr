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
