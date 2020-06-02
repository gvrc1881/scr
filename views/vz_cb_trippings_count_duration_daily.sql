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
