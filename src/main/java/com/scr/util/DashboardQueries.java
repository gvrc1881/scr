package com.scr.util;

import org.springframework.stereotype.Component;

@Component
public class DashboardQueries {
	
	public static final String MATERIAL_QTY_RECEIVED_AND_CONSUMED_IN_GIVEN_PERIOD_BY_DEPOT = "select div.zone , div, subdiv ,  product_id as material_desc ," + 
			"case when sum(received_qty) is null then 0 else sum(received_qty) end received_qty ," + 
			"case when sum(consumed_qty) is null then 0 else sum(consumed_qty) end consumed_qty ," + 
			"case when sum(qty_net_period_net_qty) is null then 0 else sum(qty_net_period_net_qty) end qty_net_period_net_qty ," + 
			"  abbreviation uom" + 
			" from " + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" left outer join" + 
			" ( " + 
			" SELECT " + 
			"        d.PRODUCT_ID AS PRODUCT_ID, d.FACILITY_ID AS FACILITY_ID," + 
			"        uom.abbreviation," + 
			" sum(case when ROUND(d.QUANTITY,3) < 0 then 0 else d.QUANTITY end ) as received_qty ," + 
			" sum(case when ROUND(d.QUANTITY,3) > 0 then 0 else d.QUANTITY end ) as consumed_qty ," + 
			" ROUND(SUM((d.QUANTITY)),3) qty_net_period_net_qty ," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"               FROM" + 
			"        dmtr d ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE   F.FACILITY_NAME = ? AND" + 
			"         P.PRODUCT_ID = d.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = d.FACILITY_ID" + 
			" and p.quantity_uom_id = uom.uom_id " + 
			" and f.division = ? " + 
			" and p.product_id = ? " + 
			" and f.sub_division = ? " + 
			" and zone = ? " + 
			" and transaction_date ::date >= ? ::date "+
			" and transaction_date :: date <= ? ::date  "+
			"        GROUP BY d.PRODUCT_ID,d.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			") q " + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			"Group by  PRODUCT_ID, abbreviation , division , subdiv, div, data_div , div.zone, sub_division" + 
			"        ORDER BY div.zone , div, subdiv , PRODUCT_ID";
	
	public static final String MATERIAL_QTY_ONH_AND_BY_DEPOT = "select div.zone , div, subdiv , div.FACILITY_ID , depot_name , product_id as material_desc ," + 
			"case when QOH is null then 0 else qoh end QOH ," + 
			"  abbreviation uom" + 
			"from" + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			"left outer join" + 
			"( " + 
			" SELECT " + 
			"        II.PRODUCT_ID AS PRODUCT_ID, II.FACILITY_ID AS FACILITY_ID, " + 
			"        uom.abbreviation," + 
			" ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)),3) as QOH," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"               FROM" + 
			"        INVENTORY_ITEM II ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE    F.FACILITY_NAME= ? " + 
			"	AND" + 
			"         P.PRODUCT_ID = II.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = II.FACILITY_ID" + 
			" and p.quantity_uom_id = uom.uom_id" + 
			" and f.division =  ? " + 
			" and p.product_id = ?" + 
			" and f.sub_division = ?" + 
			"  and zone =? " + 
			"        GROUP BY II.PRODUCT_ID,II.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			") q" + 
			"on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			"" + 
			"        ORDER BY div.zone , div, subdiv , depot_name  , PRODUCT_ID";
	
	public static final String DIVISION_QUERY = "select div.zone , div, product_id as material_desc , " + 
			" case when sum(QOH) is null then 0 else sum(QOH) end QOH ," + 
			"  abbreviation uom  " + 
			" from " + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" left outer join " + 
			" ( " +
			" SELECT " + 
			"        II.PRODUCT_ID AS PRODUCT_ID, II.FACILITY_ID AS FACILITY_ID, " + 
			"        uom.abbreviation," + 
			"	ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)),3) as QOH," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"               FROM" + 
			"        INVENTORY_ITEM II ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE " + 
			"         P.PRODUCT_ID = II.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = II.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" + 
			"	and p.product_id = '0076-1'" +
			"        GROUP BY II.PRODUCT_ID,II.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			" ) q " + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			" group by div.zone , div, product_id  ,  abbreviation " + 
			"        ORDER BY div.zone , div,  PRODUCT_ID ";

	
	
	String SUB_DIVISION_QUERY = "select div.zone , div, subdiv , product_id as material_desc , " + 
			"case when sum(QOH) is null then 0 else sum(QOH) end QOH ," + 
			"  abbreviation uom  " + 
			"from" + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			"left outer join " + 
			"(" + 
			"SELECT " + 
			"        II.PRODUCT_ID AS PRODUCT_ID, II.FACILITY_ID AS FACILITY_ID, --round(minimum_stock), " + 
			"        uom.abbreviation," + 
			"	ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)),3) as QOH," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"               FROM" + 
			"        INVENTORY_ITEM II ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE" + 
			"         P.PRODUCT_ID = II.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = II.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" + 
			"	and p.product_id = '0076-1' " + 
			"        GROUP BY II.PRODUCT_ID,II.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			") q" + 
			"on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			"" + 
			"group by div.zone , div, subdiv , product_id ,  abbreviation   " + 
			"        ORDER BY div.zone , div, subdiv , product_id " ;
	
	
	String DEPOT_QUERY = "select div.zone , div, subdiv , div.FACILITY_ID , depot_name , product_id as material_desc , " + 
			" case when QOH is null then 0 else qoh end QOH ," + 
			"  abbreviation uom  " + 
			" from" + 
			" ( select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" left outer join " + 
			" ( " + 
			" SELECT " + 
			"        II.PRODUCT_ID AS PRODUCT_ID, II.FACILITY_ID AS FACILITY_ID, " + 
			"        uom.abbreviation," + 
			"	ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)),3) as QOH," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"               FROM" + 
			"        INVENTORY_ITEM II ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE  " + 
			"         P.PRODUCT_ID = II.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = II.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" +
			"	and p.product_id = '0076-1' " + 
			"        GROUP BY II.PRODUCT_ID,II.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			" ) q " + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			"        ORDER BY div.zone , div, subdiv , depot_name  , PRODUCT_ID";
	
	String DTMR_QUERY = " select div.zone , div,  product_id as material_desc , " + 
			" case when sum(received_qty) is null then 0 else sum(received_qty) end received_qty ," + 
			" case when sum(consumed_qty) is null then 0 else sum(consumed_qty) end consumed_qty ," + 
			" case when sum(qty_net_period_net_qty) is null then 0 else sum(qty_net_period_net_qty) end qty_net_period_net_qty ," + 
			"  abbreviation uom  " + 
			" from " + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" left outer join " + 
			" ( " + 
			" SELECT " + 
			"        d.PRODUCT_ID AS PRODUCT_ID, d.FACILITY_ID AS FACILITY_ID,  " + 
			"        uom.abbreviation," + 
			"	sum(case when ROUND(d.QUANTITY,3) < 0 then 0 else d.QUANTITY end ) as received_qty ," + 
			"	sum(case when ROUND(d.QUANTITY,3) > 0 then 0 else d.QUANTITY end ) as consumed_qty ," + 
			"	ROUND(SUM((d.QUANTITY)),3) qty_net_period_net_qty ," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"               FROM" + 
			"        dmtr d ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE " + 
			"         P.PRODUCT_ID = d.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = d.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" +
			"	and p.product_id = '0076-1' "+
			"        GROUP BY d.PRODUCT_ID,d.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			" ) q" + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			" Group by  PRODUCT_ID, abbreviation , division , div, data_div , div.zone, sub_division" + 
			"        ORDER BY div.zone , div,  PRODUCT_ID ";
	
	String SUB_DIVISION_WISE_PERIOD_QUERY = "select div.zone , div, subdiv ,  product_id as material_desc , " + 
			"case when sum(received_qty) is null then 0 else sum(received_qty) end received_qty ," + 
			"case when sum(consumed_qty) is null then 0 else sum(consumed_qty) end consumed_qty ," + 
			"case when sum(qty_net_period_net_qty) is null then 0 else sum(qty_net_period_net_qty) end qty_net_period_net_qty ," + 
			"  abbreviation uom  " + 
			" from " + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" left outer join " + 
			" ( " + 
			" SELECT " + 
			"        d.PRODUCT_ID AS PRODUCT_ID, d.FACILITY_ID AS FACILITY_ID,  " + 
			"        uom.abbreviation," + 
			"	sum(case when ROUND(d.QUANTITY,3) < 0 then 0 else d.QUANTITY end ) as received_qty ," + 
			"	sum(case when ROUND(d.QUANTITY,3) > 0 then 0 else d.QUANTITY end ) as consumed_qty ," + 
			"	ROUND(SUM((d.QUANTITY)),3) qty_net_period_net_qty ," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"    FROM" + 
			"        dmtr d ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE  " + 
			"         P.PRODUCT_ID = d.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = d.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" + 
			"	and p.product_id = '0076-1' " +  
			"        GROUP BY d.PRODUCT_ID,d.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			" ) q" + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			" Group by  PRODUCT_ID, abbreviation , division , subdiv, div, data_div , div.zone, sub_division" + 
			"        ORDER BY div.zone , div, subdiv , PRODUCT_ID ";
	
	String DEPOT_WISE_PERIOD_QUERY = " select div.zone , div, subdiv , div.FACILITY_ID , depot_name , product_id as material_desc , " + 
			"case when received_qty is null then 0 else received_qty end received_qty ," + 
			"case when consumed_qty is null then 0 else consumed_qty end consumed_qty ," + 
			"case when qty_net_period_net_qty is null then 0 else qty_net_period_net_qty end qty_net_period_net_qty ," + 
			"  abbreviation uom  " + 
			" from " + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" left outer join " + 
			" ( " + 
			" SELECT " + 
			"        d.PRODUCT_ID AS PRODUCT_ID, d.FACILITY_ID AS FACILITY_ID,  " + 
			"        uom.abbreviation," + 
			"	sum(case when ROUND(d.QUANTITY,3) < 0 then 0 else d.QUANTITY end ) as received_qty ," + 
			"	sum(case when ROUND(d.QUANTITY,3) > 0 then 0 else d.QUANTITY end ) as consumed_qty ," + 
			"	ROUND(SUM((d.QUANTITY)),3) qty_net_period_net_qty ," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"               FROM" + 
			"        dmtr d ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE   " + 
			"         P.PRODUCT_ID = d.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = d.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" + 
			"	and p.product_id = '0076-1' " + 
			"        GROUP BY d.PRODUCT_ID,d.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			") q" + 
			"on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			" " + 
			"        ORDER BY div.zone , div, subdiv , depot_name  , PRODUCT_ID ";
	
	
	public static String DIVISION_WISE_BY_PRODUCT = "select" + 
			"   div.zone," + 
			"   div," + 
			"   product_id as material_desc," + 
			"   case" + 
			"      when" + 
			"         sum(QOH) is null " + 
			"      then" + 
			"         0 " + 
			"      else" + 
			"         sum(QOH) " + 
			"   end" + 
			"   QOH , abbreviation1 uom " + 
			" ,  'QoH By Division of - '||product_id ||'('||description1||') - units '||abbreviation1 as header "+
			"from" + 
			"   (" + 
			"      select distinct" + 
			"         zone," + 
			"         data_div div," + 
			"         sub_division subdiv," + 
			"         facility_name depot_name," + 
			"         FACILITY_ID " + 
			"      from" + 
			"         facility " + 
			"   )" + 
			"   div " + 
			"   inner join" + 
			"      (" + 
			"         SELECT" + 
			"            II.PRODUCT_ID AS PRODUCT_ID," + 
			"            II.FACILITY_ID AS FACILITY_ID," + 
			"            case when uom.abbreviation is null or uom.abbreviation ='' then ' ' else uom.abbreviation end abbreviation1 ," + 
			"            ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)), 3) as QOH," + 
			"            F.FACILITY_NAME AS FACILITY_NAME," + 
			"            f.sub_division," + 
			"            f.division," + 
			"            f.zone," + 
			"            f.data_div "
			+ " , case when p.description is null or p.description ='' then ' ' else p.description end description1 " + 
			"         FROM" + 
			"            INVENTORY_ITEM II," + 
			"            PRODUCT P," + 
			"            FACILITY F," + 
			"            uom " + 
			"         WHERE" + 
			"            P.PRODUCT_ID = II.PRODUCT_ID " + 
			"            AND F.FACILITY_ID = II.FACILITY_ID " + 
			"            and p.quantity_uom_id = uom.uom_id " + 
			"            and p.product_id = ? " + 
			"            and " + 
			"            (" + 
			"               Upper(f.Zone)" + 
			"            )" + 
			"            = Upper(?) " + 
			"         GROUP BY" + 
			"            II.PRODUCT_ID," + 
			"            II.FACILITY_ID," + 
			"            F.FACILITY_NAME," + 
			"            case when uom.abbreviation is null or uom.abbreviation ='' then ' ' else uom.abbreviation end ," + 
			"            f.division," + 
			"            f.data_div," + 
			"            f.zone," + 
			"            f.sub_division "
			+ ", case when p.description is null or p.description ='' then ' ' else p.description end  " + 
			"      )" + 
			"      q " + 
			"      on (div = q.data_div " + 
			"      and div.zone = q.zone " + 
			"      and subdiv = q.sub_division " + 
			"      and q.FACILITY_NAME = depot_name " + 
			"      and q.facility_id = div.FACILITY_ID ) " + 
			"group by" + 
			"   div.zone," + 
			"   div," + 
			"   product_id," + 
			"   abbreviation1, description1 " + 
			" ORDER BY" + 
			"   div.zone," + 
			"   div," + 
			"   product_id";
	
	public static String SUB_DIVISION_WISE_BY_PRODUCT = "select" + 
			"    div.zone," + 
			"    div," + 
			"    subdiv," + 
			"    product_id as material_desc," + 
			"    case" + 
			"        when" + 
			"            sum(QOH) is null " + 
			"        then" + 
			"            0 " + 
			"        else" + 
			"            sum(QOH) " + 
			"    end" + 
			"    QOH , abbreviation1 uom " + 
			" ,  'QoH By Sub Division of - '||product_id ||'('||description1||') - units '||abbreviation1 as header "+
			"from" + 
			"    (" + 
			"        select distinct" + 
			"            zone," + 
			"            data_div div," + 
			"            sub_division subdiv," + 
			"            facility_name depot_name," + 
			"            FACILITY_ID " + 
			"        from" + 
			"            facility " + 
			"    )" + 
			"    div " + 
			"    inner join" + 
			"        (" + 
			"            SELECT" + 
			"                II.PRODUCT_ID AS PRODUCT_ID," + 
			"                II.FACILITY_ID AS FACILITY_ID," + 
			"            case when uom.abbreviation is null or uom.abbreviation ='' then ' ' else uom.abbreviation end abbreviation1 ," + 
			"                ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)), 3) as QOH," + 
			"                F.FACILITY_NAME AS FACILITY_NAME," + 
			"                f.sub_division," + 
			"                f.division," + 
			"                f.zone," + 
			"                f.data_div"
			+ " , case when p.description is null or p.description ='' then ' ' else p.description end description1 " +  
			"            FROM" + 
			"                INVENTORY_ITEM II," + 
			"                PRODUCT P," + 
			"                FACILITY F," + 
			"                uom " + 
			"            WHERE" + 
			"                P.PRODUCT_ID = II.PRODUCT_ID " + 
			"                AND F.FACILITY_ID = II.FACILITY_ID " + 
			"                and p.quantity_uom_id = uom.uom_id " + 
			"                and " + 
			"                (" + 
			"                    Upper(f.division)" + 
			"                )" + 
			"                = ? " + 
			"                and " + 
			"                (" + 
			"                    Upper(f.Zone)" + 
			"                )" + 
			"                = ? " + 
			"                and p.product_id = ? " + 
			"            GROUP BY" + 
			"                II.PRODUCT_ID," + 
			"                II.FACILITY_ID," + 
			"                F.FACILITY_NAME," + 
			"            case when uom.abbreviation is null or uom.abbreviation ='' then ' ' else uom.abbreviation end ," + 
			"                f.division," + 
			"                f.data_div," + 
			"                f.zone," + 
			"                f.sub_division "
			+ ", case when p.description is null or p.description ='' then ' ' else p.description end  " +  
			"        )" + 
			"        q " + 
			"        on (div = q.data_div " + 
			"        and div.zone = q.zone " + 
			"        and subdiv = q.sub_division " + 
			"        and q.FACILITY_NAME = depot_name " + 
			"        and q.facility_id = div.FACILITY_ID ) " + 
			"group by" + 
			"    div.zone," + 
			"    div," + 
			"    subdiv," + 
			"    product_id," + 
			"    abbreviation1, description1 " + 
			"ORDER BY" + 
			"    div.zone," + 
			"    div," + 
			"    subdiv," + 
			"    product_id";
	
	public static String DEPOT_WISE = "SELECT div.zone," + 
			"       div," + 
			"       subdiv," + 
			"       div.FACILITY_ID," + 
			"       depot_name," + 
			"       product_id AS material_desc," + 
			"       CASE" + 
			"           WHEN QOH IS NULL THEN 0" + 
			"           ELSE qoh" + 
			"       END QOH," + 
			"       abbreviation1 uom" + 
			" ,  'QoH By Depot of - '||product_id ||'('||description1||') - units '||abbreviation1 as header "+
			" FROM " + 
			"  (SELECT DISTINCT ZONE," + 
			"                   data_div div," + 
			"                   sub_division subdiv," + 
			"                   facility_name depot_name," + 
			"                   FACILITY_ID" + 
			"   FROM facility) div " + 
			" INNER JOIN" + 
			"  (SELECT II.PRODUCT_ID AS PRODUCT_ID," + 
			"          II.FACILITY_ID AS FACILITY_ID," + 
			"            case when uom.abbreviation is null or uom.abbreviation ='' then ' ' else uom.abbreviation end abbreviation1 ," + 
			" ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)), 3) AS QOH," + 
			" F.FACILITY_NAME AS FACILITY_NAME," + 
			" f.sub_division," + 
			" f.division," + 
			" f.zone," +  
			" f.data_div"
			+ " , case when p.description is null or p.description ='' then ' ' else p.description end description1 " + 
			"   FROM INVENTORY_ITEM II," + 
			"        PRODUCT P," + 
			"        FACILITY F," + 
			"        uom" + 
			"   WHERE P.PRODUCT_ID = II.PRODUCT_ID" + 
			"     AND F.FACILITY_ID = II.FACILITY_ID" + 
			"     AND p.quantity_uom_id = uom.uom_id" + 
			"     AND QUANTITY_ON_HAND_TOTAL IS NOT NULL" + 
			"     AND (Upper(f.Zone)) = ?" + 
			"     AND (Upper(f.division) =?)" + 
			"     AND CASE" + 
			"             WHEN upper(f.sub_division)='' THEN f.sub_division=upper(?)" + 
			"             ELSE f.sub_division IS NOT NULL" + 
			"         END" + 
			"     AND p.product_id = ?" + 
			"   GROUP BY II.PRODUCT_ID," + 
			"            II.FACILITY_ID," + 
			"            F.FACILITY_NAME," + 
			"            case when uom.abbreviation is null or uom.abbreviation ='' then ' ' else uom.abbreviation end ," + 
			"            f.division," + 
			"            f.data_div," + 
			"            f.zone," + 
			"            f.sub_division "+
			", case when p.description is null or p.description ='' then ' ' else p.description end  " + 
			" ) q ON (div = q.data_div" + 
			"                                  AND div.zone = q.zone" + 
			"                                  AND subdiv = q.sub_division" + 
			"                                  AND q.FACILITY_NAME = depot_name" + 
			"                                  AND q.facility_id = div.FACILITY_ID)" + 
			" ORDER BY div.zone," + 
			"         div," + 
			"         subdiv," + 
			"         depot_name ," + 
			"         PRODUCT_ID";
	
	public static String DIVISION_WISE_WITH_PERIOD = "select div.zone , div,  product_id as material_desc , " + 
			"case when sum(received_qty) is null then 0 else sum(received_qty) end received_qty ," + 
			"case when sum(consumed_qty) is null then 0 else sum(consumed_qty) end consumed_qty ," + 
			"case when sum(qty_net_period_net_qty) is null then 0 else sum(qty_net_period_net_qty) end qty_net_period_net_qty ," + 
			"  abbreviation1 uom  " + 
			" ,  'Materail received and consumed By Division of - '||product_id ||'('||description1||') - units '||abbreviation1 || ' from ' || ? " + 
			" ||' to '|| ? as header "+
			" from " + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" inner join " + 
			"( " + 
			" SELECT " + 
			"   d.PRODUCT_ID AS PRODUCT_ID, d.FACILITY_ID AS FACILITY_ID,  " + 
			"   case when uom.abbreviation is null or uom.abbreviation ='' then ' ' else uom.abbreviation end abbreviation1 ," + 
			"	sum(case when ROUND(d.QUANTITY,3) < 0 then 0 else d.QUANTITY end ) as received_qty ," + 
			"	sum(case when ROUND(d.QUANTITY,3) > 0 then 0 else d.QUANTITY end ) as consumed_qty ," + 
			"	ROUND(SUM((d.QUANTITY)),3) qty_net_period_net_qty, " + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div"
			+ " , case when p.description is null or p.description ='' then ' ' else p.description end description1 " +  
			"               FROM " + 
			"        dmtr d ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom " + 
			"        WHERE    " + 
			"         P.PRODUCT_ID = d.PRODUCT_ID " + 
			"        AND F.FACILITY_ID = d.FACILITY_ID " + 
			"	and p.quantity_uom_id = uom.uom_id" + 
			"	and (Upper(f.Zone)) = ? " + 
			"	and p.product_id = ? " + 
			"	and QUANTITY is not null " + 
			" and transaction_date ::date >= ? ::date "+
			" and transaction_date :: date <= ? ::date  "+
			"        GROUP BY d.PRODUCT_ID,d.FACILITY_ID, F.FACILITY_NAME " +
			"      ,case when uom.abbreviation is null or uom.abbreviation ='' then ' ' else uom.abbreviation end ," + 
			"  f.division ,  f.data_div , f.zone, f.sub_division "+
			", case when p.description is null or p.description ='' then ' ' else p.description end  " + 
			" ) q " + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			" Group by  PRODUCT_ID, abbreviation1 , division , div, data_div , div.zone, sub_division, description1 " + 
			"        ORDER BY div.zone , div,  PRODUCT_ID ";
	
	public static String SUB_DIVISION_WITH_PERIOD = "select div.zone , div,sub_division,  product_id as material_desc , " + 
			"case when sum(received_qty) is null then 0 else sum(received_qty) end received_qty ," + 
			"abs(case when sum(consumed_qty) is null then 0 else sum(consumed_qty) end ) consumed_qty ," + 
			"case when sum(qty_net_period_net_qty) is null then 0 else sum(qty_net_period_net_qty) end qty_net_period_net_qty ," + 
			"  abbreviation1 uom  " + 
			" ,  'Materail received and consumed By Sub Division of - '||product_id ||'('||description1||') - units '||abbreviation1 || ' from ' || ? " + 
			" ||' to '|| ? as header "+			
			" from" + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div " + 
			" inner join" + 
			"( " + 
			" SELECT " + 
			"        d.PRODUCT_ID AS PRODUCT_ID, d.FACILITY_ID AS FACILITY_ID,  " + 
			"            case when uom.abbreviation is null or uom.abbreviation ='' then ' ' else uom.abbreviation end abbreviation1 ," + 
			"	sum(case when ROUND(d.QUANTITY,3) < 0 then 0 else d.QUANTITY end ) as received_qty ," + 
			"	sum(case when ROUND(d.QUANTITY,3) > 0 then 0 else d.QUANTITY end ) as consumed_qty ," + 
			"	ROUND(SUM((d.QUANTITY)),3) qty_net_period_net_qty ," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div"
			+ " , case when p.description is null or p.description ='' then ' ' else p.description end description1 " +  
			"               FROM" + 
			"        dmtr d ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE    " + 
			"         P.PRODUCT_ID = d.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = d.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" + 
			"	 and upper(zone) =upper(?)" + 
			"	and upper(f.division) = upper(?)" + 
			"	and p.product_id = ? " + 
			"	and QUANTITY is not null" + 
			" and transaction_date ::date >= ? ::date "+
			" and transaction_date :: date <= ? ::date  "+
			"        GROUP BY d.PRODUCT_ID,d.FACILITY_ID, F.FACILITY_NAME, "+
			"            case when uom.abbreviation is null or uom.abbreviation ='' then ' ' else uom.abbreviation end ," + 
			" f.division ,  f.data_div , f.zone, f.sub_division "+
			", case when p.description is null or p.description ='' then ' ' else p.description end  " +  
			" ) q" + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			" Group by  PRODUCT_ID, abbreviation1 , division , div, data_div , div.zone, sub_division, description1 " + 
			"        ORDER BY div.zone , div,  PRODUCT_ID ";
	
	public static String DEPOT_WISE_WITH_PERIOD = "select div.zone , div, subdiv , div.FACILITY_ID , depot_name , product_id as material_desc , " + 
			"case when received_qty is null then 0 else received_qty end received_qty ," + 
			"case when consumed_qty is null then 0 else consumed_qty end consumed_qty ," + 
			"case when qty_net_period_net_qty is null then 0 else qty_net_period_net_qty end qty_net_period_net_qty ," + 
			"  abbreviation1 uom  " + 
			" ,  'Materail received and consumed By Depot of - '||product_id ||'('||description1||') - units '||abbreviation1 || ' from ' || ? " + 
			" ||' to '|| ? as header "+
			" from" + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" inner join " + 
			"( " + 
			" SELECT " + 
			"        d.PRODUCT_ID AS PRODUCT_ID, d.FACILITY_ID AS FACILITY_ID,  " + 
			"            case when uom.abbreviation is null or uom.abbreviation ='' then ' ' else uom.abbreviation end abbreviation1 ," + 
			"	sum(case when ROUND(d.QUANTITY,3) < 0 then 0 else d.QUANTITY end ) as received_qty ," + 
			"	sum(case when ROUND(d.QUANTITY,3) > 0 then 0 else d.QUANTITY end ) as consumed_qty ," + 
			"	ROUND(SUM((d.QUANTITY)),3) qty_net_period_net_qty ," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div"
			+ " , case when p.description is null or p.description ='' then ' ' else p.description end description1 " +  
			"               FROM" + 
			"        dmtr d ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE  " + 
			"         P.PRODUCT_ID = d.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = d.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" + 
			"	and upper(zone) = Upper(?) " + 
			"	and upper(f.division) = Upper(?)" + 
			"	and case when upper(f.sub_division)='' then f.sub_division=upper(?)" + 
			"			else f.sub_division is not null end" + 
			"	and p.product_id = ?" + 
			"	and QUANTITY is not null" + 
			" and transaction_date ::date >= ? ::date "+
			" and transaction_date :: date <= ? ::date  "+ 
			"        GROUP BY d.PRODUCT_ID,d.FACILITY_ID, F.FACILITY_NAME, "+
			"            case when uom.abbreviation is null or uom.abbreviation ='' then ' ' else uom.abbreviation end ," + 
			" f.division ,  f.data_div , f.zone, f.sub_division "+
			", case when p.description is null or p.description ='' then ' ' else p.description end  " + 
			" ) q" + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			"    ORDER BY div.zone , div, subdiv , depot_name  , PRODUCT_ID ";
	
	public static String DIVISION_WISE_ENERGY_CONSUMPTION = " select feeder_id , feeder_name ,extract(day from no_of_days_lapsed_reading) , " + 
			"cur_kwh , prev_kwh , multiplication_fac, " + 
			" (cur_kwh - prev_kwh)*multiplication_fac/extract(day from no_of_days_lapsed_reading) consumption " + 
			"from " + 
			"( " + 
			"select (cur_kwh - recent_kwh)*multiplication_fac/extract(day from no_of_days_lapsed_reading) consumption , " + 
			"feeder_id , feeder_name , multiplication_fac ,requested_reading_date ,  joint_meter ,  " + 
			"first_reading_after_meter_fix, meter_start_date ,recent_reading_date, no_of_days_lapsed_reading, " + 
			" recent_reading_date as prev_reading_date , 'Gap(' ||   no_of_days_lapsed_reading ||')'  reading_gap_days, " + 
			// "--|| \r\n" + 
			" recent_kwh as prev_kwh, cur_kwh, " + 
			//"-- recent_reading_date || ' : Gap(' ||   no_of_days_lapsed_reading ||')' || \r\n" + 
			" recent_kvah as prev_kvah, cur_kvah, " + 
			//"--recent_reading_date || ' : Gap(' ||   no_of_days_lapsed_reading ||')' || \r\n" + 
			" recent_rkvah_lag as prev_rkvah_lag, cur_rkvah_lag, " + 
			//"--recent_reading_date || ' : Gap(' ||   no_of_days_lapsed_reading ||')' || \r\n" + 
			" recent_rkvah_lead as prev_rkvah_lead, cur_rkvah_lead, " + 
			" cur_cmd, cur_rmd, cur_vol_max, cur_vol_min  , cur_max_load , " + 
			" joint_reading_date, no_of_days_lapsed_j_reading, jr_kwh, jr_kvah, jr_rkvah_lag, jr_rkvah_lead " + 
			"from  " + 
			"( " + 
			"SELECT " + 
			"	a.feeder_id , a.feeder_name , a.multiplication_fac , to_char(?::date, 'dd-mm-yy') as requested_reading_date, rec.em_start_kwh rec_em_start_kwh, " + 
			"    case when rec.energy_reading_date is null then 'Yes' else 'No' end as first_reading_after_meter_fix,  " + 
			"  a.em_start_date::date  as meter_start_date , " + 
			"    to_char(case when rec.energy_reading_date is null then a.em_start_date::date else rec.energy_reading_date::date end,'dd-mm-yy')  AS recent_reading_date, " + 
			"    ?::date - case when rec.energy_reading_date is null then a.em_start_date else rec.energy_reading_date end AS no_of_days_lapsed_reading, " + 
		//	"--- current day details\r\n" + 
			"    cur.seq_id, cur.id , " + 
			"    cur.location cur_location, cur.feeder_id cur_feeder_id, " + 
			"    cur.energy_reading_date AS cuurent_reading_date, " + 
			"    cur.joint_meter , " + 
			"    cur.kwh AS cur_kwh, " + 
			"    cur.kvah AS cur_kvah," + 
			"    cur.rkvah_lag AS cur_rkvah_lag," + 
			"    cur.rkvah_lead AS cur_rkvah_lead," + 
			"    cur.cmd cur_cmd," + 
			"    cur.rmd cur_rmd," + 
			"    cur.vol_max  cur_vol_max," + 
			"    cur.vol_min cur_vol_min," + 
			"    cur.max_load cur_max_load," + 
			"    cur.max_load_time_hhmm," + 
			"    cur.max_load_time_date, cur.remarks," + 
		//	"--    cur.multiplication_fac ," + 
			//"--- recent to required date values " + 
			"    rec.seq_id, rec.id ," + 
			"    rec.location, rec.feeder_id rec_feeder_id," + 
			"    case when rec.energy_reading_date is null then a.em_start_kwh else rec.kwh end AS recent_kwh," + 
			"    case when rec.energy_reading_date is null then a.em_start_kvah else rec.kvah end AS recent_kvah," + 
			"    case when rec.energy_reading_date is null then a.em_start_rkvah_lag else rec.rkvah_lag end AS recent_rkvah_lag," + 
			"    case when rec.energy_reading_date is null then a.em_start_rkvah_lead else rec.rkvah_lead end AS recent_rkvah_lead," + 
			"    rec.cmd recent_cmd," + 
			"    rec.rmd recent_rmd, rec.vol_max recent_vol_max, rec.vol_min recent_vol_min, rec.max_load recent_max_load," + 
			"    rec.max_load_time_hhmm recent_max_load_time_hhmm," + 
			"    rec.max_load_time_date recent_max_load_time_date ," + 
			"   rec.multiplication_fac recent_multiplication_fac ," + 
		//	"   ----- ---- added for jr started\r\n" + 
		//	"       -- joint reading ---\r\n" + 
			"    jr.energy_reading_date AS joint_reading_date," + 
			"    cur.energy_reading_date - jr.energy_reading_date AS no_of_days_lapsed_j_reading," + 
			"    jr.kwh AS jr_kwh," + 
			"    jr.kvah AS jr_kvah," + 
			"    jr.rkvah_lag AS jr_rkvah_lag," + 
			"    jr.rkvah_lead AS jr_rkvah_lead" + 
			//"     ----- ---- added for jr ended" + 
			"   FROM ( " + 
			"   select em.feeder_id , em.seq_id ,  em.em_start_date , em.em_end_date , em.multiplication_fac , em.remarks , " + 
			"	em_m_start_reading , em_m_end_reading , em_start_kwh , em_start_kvah , em_start_rkvah_lag , em_start_rkvah_lead ," + 
			"	 feeder_name from v_energy_meter em where upper(em_data_div) = upper(?)  )  a " + 
			"   left outer join v_energy_consumption rec on rec.energy_reading_date = ( SELECT max(cur1.energy_reading_date) AS max " + 
			"					FROM v_energy_consumption cur1 " + 
			"					WHERE cur1.energy_reading_date < ?::date " + 
			"					AND cur1.feeder_id = a.feeder_id " + 
			"					) " + 
			"	AND rec.feeder_id = a.feeder_id " + 
			"    LEFT JOIN v_energy_consumption cur ON  ( cur.energy_reading_date = ?::date " + 
			"						AND a.feeder_id = cur.feeder_id ) " + 
			//"									---- added for jr started " + 
			"   LEFT JOIN v_energy_consumption jr ON  ( jr.energy_reading_date = ( SELECT max(jr1.energy_reading_date) AS max " + 
			"									FROM v_energy_consumption jr1 " + 
			"									WHERE jr1.energy_reading_date < cur.energy_reading_date " + 
			"									AND (jr1.joint_meter = 'y'::bpchar OR jr1.joint_meter = 'Y'::bpchar)  " + 
			"									AND jr1.feeder_id = cur.feeder_id " + 
			"									)  " + 
			"						AND jr.feeder_id = cur.feeder_id )" + 
			//" ----- ---- added for jr ended\r\n" + 
			") final " + 
			") a" ;
	
	public static  String FEEDER_WISH_ENERGY_CONSUMPTION = "select req_date, feeder_id , feeder_name , multiplication_fac ,  requested_reading_date ," + 
			"no_of_days_lapsed_reading,  " + 
			"cur_kwh , prev_kwh , multiplication_fac, " + 
			" case when no_of_days_lapsed_reading > 1 then (cur_kwh - prev_kwh)*multiplication_fac/no_of_days_lapsed_reading " + 
			"  else (cur_kwh - prev_kwh)*multiplication_fac end as   consumption " + 
			"from " + 
			"( " + 
			"select energy_consume_date as req_date, feeder_id , feeder_name , multiplication_fac , joint_meter , requested_reading_date ,first_reading_after_meter_fix, meter_start_date ,recent_reading_date, " + 
			"no_of_days_lapsed_reading,  " + 
			"recent_reading_date_string , 'Gap(' ||   no_of_days_lapsed_reading ||')' reading_gap_days, " + 
			" recent_kwh as prev_kwh, cur_kwh,  " + 
			"recent_kvah as prev_kvah, cur_kvah,  " + 
			"recent_rkvah_lag as prev_rkvah_lag, cur_rkvah_lag,  " + 
			"recent_rkvah_lead as prev_rkvah_lead, cur_rkvah_lead,  " + 
			"cur_cmd, cur_rmd, cur_vol_max, cur_vol_min  , cur_max_load   , " + 
			" joint_reading_date, no_of_days_lapsed_j_reading, jr_kwh, jr_kvah, jr_rkvah_lag, jr_rkvah_lead " + 
			"from   " + 
			"(  " + 
			"SELECT   " + 
			"energy_consume_date ,  " + 
			"	a.feeder_id , a.feeder_name , a.multiplication_fac , to_char(energy_consume_date::date, 'dd-mm-yy')  as requested_reading_date, rec.em_start_kwh rec_em_start_kwh,  " + 
			"    case when rec.energy_reading_date is null then 'Yes' else 'No' end as first_reading_after_meter_fix,   " + 
			"    a.em_start_date::date  as meter_start_date ,  to_char(a.em_start_date::date, 'dd-mm-yy')   as meter_start_date_string , " + 
			"   rec.energy_reading_date AS recent_reading_date,  to_char(rec.energy_reading_date::date, 'dd-mm-yy')    as   recent_reading_date_string,  " + 
			"    (energy_consume_date::date - case when rec.energy_reading_date is null then a.em_start_date::Date else rec.energy_reading_date::Date end)-1  AS no_of_days_lapsed_reading ,  " + 
			"   cur.seq_id, cur.id ,  " + 
			"    cur.location cur_location, cur.feeder_id cur_feeder_id,  " + 
			"    cur.energy_reading_date AS cuurent_reading_date,  " + 
			"    cur.joint_meter , " + 
			"    cur.kwh AS cur_kwh,  " + 
			"    cur.kvah AS cur_kvah,  " + 
			"    cur.rkvah_lag AS cur_rkvah_lag,  " + 
			"    cur.rkvah_lead AS cur_rkvah_lead,  " + 
			"    cur.cmd cur_cmd,  " + 
			"    cur.rmd cur_rmd,  " + 
			"    cur.vol_max  cur_vol_max,  " + 
			"    cur.vol_min cur_vol_min,  " + 
			"    cur.max_load cur_max_load,  " + 
			"    cur.max_load_time_hhmm,  " + 
			"    cur.max_load_time_date,  " + 
			"    rec.seq_id, rec.id , rec.energy_reading_date recent_energy_reading_Date ,  " + 
			"    rec.location, rec.feeder_id rec_feeder_id,  " + 
			"    case when rec.energy_reading_date is null then a.em_start_kwh else rec.kwh end AS recent_kwh,  " + 
			"    case when rec.energy_reading_date is null then a.em_start_kvah else rec.kvah end AS recent_kvah,  " + 
			"    case when rec.energy_reading_date is null then a.em_start_rkvah_lag else rec.rkvah_lag end AS recent_rkvah_lag,  " + 
			"    case when rec.energy_reading_date is null then a.em_start_rkvah_lead else rec.rkvah_lead end AS recent_rkvah_lead,  " + 
			"    rec.cmd recent_cmd,  " + 
			"    rec.rmd recent_rmd, rec.vol_max recent_vol_max, rec.vol_min recent_vol_min, rec.max_load recent_max_load,  " + 
			"    rec.max_load_time_hhmm recent_max_load_time_hhmm,  " + 
			"    rec.max_load_time_date recent_max_load_time_date ,  " + 
			"   rec.multiplication_fac recent_multiplication_fac  ," + 
			// " ----- ---- added for jr started\r\n" + 
			// "       -- joint reading ---\r\n" + 
			"    jr.energy_reading_date AS joint_reading_date, " + 
			"    cur.energy_reading_date - jr.energy_reading_date AS no_of_days_lapsed_j_reading, " + 
			"    jr.kwh AS jr_kwh, " + 
			"    jr.kvah AS jr_kvah," + 
			"    jr.rkvah_lag AS jr_rkvah_lag," + 
			"    jr.rkvah_lead AS jr_rkvah_lead" + 
			// "     ----- ---- added for jr ended\r\n" + 
			"   FROM  " + 
			"   (   " + 
			"   select energy_consume_date, em.feeder_id , em.seq_id ,  em.em_start_date , em.em_end_date , em.multiplication_fac , em.remarks ,   " + 
			"	em_m_start_reading , em_m_end_reading , em_start_kwh , em_start_kvah , em_start_rkvah_lag , em_start_rkvah_lead ,  " + 
			"	 feeder_name from v_energy_meter em ,  " + 
			"	 (select generate_series(?::date,  ?::date, interval '1 day')::date as energy_consume_date )dt   " + 
			"	where feeder_id = ?    " + 
			"	 )  a  " + 
			"   left outer join v_energy_consumption rec on rec.energy_reading_date = ( SELECT max(cur1.energy_reading_date) AS max  " + 
			"		FROM v_energy_consumption cur1  " + 
			"		WHERE cur1.energy_reading_date < a.energy_consume_date  " + 
			"		AND cur1.feeder_id = a.feeder_id   " + 
			"		)  " + 
			"	AND rec.feeder_id = a.feeder_id   " + 
			"    LEFT JOIN v_energy_consumption cur ON  ( cur.energy_reading_date = a.energy_consume_date  " + 
			"			AND a.feeder_id = cur.feeder_id )  " + 
			// "			---- added for jr started\r\n" + 
			"   LEFT JOIN v_energy_consumption jr ON  ( jr.energy_reading_date = ( SELECT max(jr1.energy_reading_date) AS max " + 
			"									FROM v_energy_consumption jr1 " + 
			"									WHERE jr1.energy_reading_date < cur.energy_reading_date " + 
			"									AND (jr1.joint_meter = 'y'::bpchar OR jr1.joint_meter = 'Y'::bpchar) " + 
			"									AND jr1.feeder_id = cur.feeder_id " + 
			"									) " + 
			"						AND jr.feeder_id = cur.feeder_id ) " + 
			// " ----- ---- added for jr ended\r\n" + 
			"			) final  " + 
			" order by requested_reading_date " + 
			") a " ;
	
	public static String TOWER_CAR = "select " + 
			"sum(case when data_div ='GTL'  then 1 else 0 end ) as GTL_cnt, " + 
			" sum(case when data_div ='GNT'  then 1 else 0 end ) as GNT_cnt, " + 
			" sum(case when data_div ='SC'  then 1 else 0 end ) as SC_cnt, " + 
			" sum(case when data_div ='BZA'  then 1 else 0 end ) as BZA_cnt, " + 
			" sum(case when data_div ='HYB'  then 1 else 0 end ) as HYB_cnt, " + 
			" c.f_status,product_category_id " + 
			"from ( " + 
			"select case when asu.status is null then 'IN_USE' else asu.status end as f_status,asm.data_div, " + 
			//"	--asu.status," + 
			"	product_category_id from asset_master_data asm " + 
			"inner join product_category_member on product_id =asset_type  " + 
			"left join ( " + 
			"SELECT distinct b.date_of_status,status,b.asset_id  FROM " + 
			"      (SELECT asset_id,  max(date_of_status) as max_timestamp " + 
			"       FROM asset_status_update GROUP BY asset_id ORDER BY asset_id) a " + 
			"JOIN asset_status_update b ON a.max_timestamp = b.date_of_status " + 
			")asu on asm.asset_id = asu.asset_id " + 
			"where product_category_id in ('4 Wheel TW','8 Wheel TW' )) c " + 
			"group by  c.f_status, product_category_id";

	public static String ENERGY = "select cmd,rmd,(cmd-rmd), (rmd - cmd)," + 
			"case when (cmd-rmd) > 0 then Round(rmd/cmd*100,2) else " + 
			"	case when (cmd-rmd) < 0 then Round(cmd/cmd*100,2) else Round(cmd/cmd*100,2) end " + 
			"end as consumed," + 			
			"case when (cmd-rmd) > 0 then 0 else " + 
			"	case when (cmd-rmd) < 0 then Round((rmd-cmd)/cmd*100,2) else 0 end " + 
			"end as exceeded," + 
			"case when (cmd-rmd) > 0 then Round((cmd-rmd)/cmd*100,2) else " + 
			"	case when (cmd-rmd) < 0 then 0 else 0 end " + 
			"end as canbeconsume," + 			
			"case when cmd is null then 'No CMD' " + 
			"else round((rmd/cmd::decimal*100),2)::varchar end as per_rmd," + 
			"energy_reading_date,feeder_id,location" + 
			" from v_energy_consumption" + 
			" where energy_reading_date= ? ::date";
	
	public static String CB_FAILURE = " select  facility_name,count(*), " + 
			"sum(EXTRACT(EPOCH FROM (f.thru_date_time- f.from_date_time) )) " + 
			"from failures f " + 
			"LEFT OUTER JOIN facility FAC ON (f.sub_station = fac.facility_id ) " + 
			"where " + 
			"to_char(f.from_date_time, 'yyyy-mm-dd')::date = ? ::date " + 
			"and " + 
			"type_of_failure ='POWER_FAILURE' " + 
			"and current_status = 'ACTIVE' " + 
			"Group by  " + 
			"facility_name, " + 
			"FAC.division";

	

	
}
