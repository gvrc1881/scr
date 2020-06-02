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