--21  
-- View: v_subdivisions

CREATE OR REPLACE VIEW v_subdivisions AS 
 SELECT fsd.facility_name AS sub_division, f.facility_name AS depot_name, 
    f.facility_id, fa.facility_id_to, f.facility_type_id, f.depot_type
   FROM facility_assoc fa
   LEFT JOIN facility f ON f.facility_id::text = fa.facility_id_to::text
   LEFT JOIN facility fsd ON fsd.facility_id::text = fa.facility_id::text
  WHERE fa.facility_assoc_type_id::text = 'SUBDIVISION_UNIT'::text;
