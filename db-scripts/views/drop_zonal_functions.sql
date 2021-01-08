---1
DROP FUNCTION public.asset_schedule_graph(text);
---2
DROP FUNCTION public.day_div_tss_energy_consumption_v(date, character varying);
--3
DROP FUNCTION public.period_tss_energy_consumption_v(character varying, date, date);
---4
DROP FUNCTION public.user_func_location(text);
--5
DROP FUNCTION public.user_func_location(date);
---6
drop function tcp_measure_v_func( requested_station integer, 	Schedule_date date	);