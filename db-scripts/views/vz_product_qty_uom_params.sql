  
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