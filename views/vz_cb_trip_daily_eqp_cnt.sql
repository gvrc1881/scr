
--27
-- View: v_cb_trip_daily_eqp_cnt

	CREATE OR REPLACE VIEW public.v_cb_trip_daily_eqp_cnt AS 
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
