   --28 View: v_asset_schedule_activity_record

CREATE OR REPLACE VIEW public.v_asset_schedule_activity_record AS

  SELECT row_number() OVER () AS s_no,

     asar.asset_id,

     amd.asset_type,

     asar.schedule_code,

     asar.schedule_date,

     asar.schedule_actual_date,

     asar.status as status,
     ash.status as ash_status,

     asar.details_of_maint as details_of_maint,
     ash.details_of_maint as ash_details_of_maint,

     asar.done_by as done_by,
     
     ash.done_by as ash_done_by,

     asar.remarks as remarks,
     ash.remarks as ash_remarks,

     --100 AS kilometer,
     
     kilometer AS kilometer,

     amd.position_id,
     amd.location_position,
     amd.capacity_rating as capacity,

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

   WHERE asar.asset_id::text = amd.asset_id::text AND

asar.asset_type::text = amd.asset_type::text AND 
asar.facility_id::text= f.facility_id::text AND
 asar.facility_id::text = amd.facility_id::text

AND ash.seq_id::text = asar.asset_schedule_history_id::text;

ALTER TABLE public.v_asset_schedule_activity_record

   OWNER TO postgres;

   View: public.v_asset_schedule_activity_assoc

   select * from asset_schedule_assoc 