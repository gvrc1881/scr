package com.scr.jobs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.message.response.DashboardDetailsResponse;
import com.scr.message.response.DashboardResponse;
import com.scr.util.CloseJDBCObjects;

@Component
public class DashBoardResource {
	
	static Logger log = LogManager.getLogger(ReportResource.class);

	@Autowired
	private DataSource dataSource;

	private CloseJDBCObjects closeJDBCObjects = new CloseJDBCObjects();
	
	@SuppressWarnings("resource")
	public  DashboardResponse getAssetTypes() {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		DashboardResponse dashboardResponse = new DashboardResponse();
		List<DashboardDetailsResponse> assetTypeList = new ArrayList<>();
		List<DashboardDetailsResponse> schDoneList = new ArrayList<>();
		
		/*String query = "select row_to_json(t) from "
						+ "("
						+ "select asset_type as lebel, count(*) as value "
						+ "from zonal.asset_master_data amd, "
						+ "("
						+ "select product_id from zonal.product_category_member "
						+ "where product_category_id ='OHE_FIXED_ASSET') b "
						+ "where product_id = asset_type "
						+ "group by asset_type "
						+ "order by 2 desc"
						+ ") t";*/
		String query = "select * "
						+"from "
						+"("
						+" select   asset_type as Label, Sum(total)*.5 as value "
						+"from AMD_Pop_div amd "
						+"group by asset_type "
						+"order by 2 desc "
						+") t";
		String schDoneQuery = "select * " + 
				"from " + 
				"(" + 
				" select   asset_type as Label, Sum(total)*.5 as value " + 
				"from amd_sch_done_div amd " + 
				"group by asset_type " + 
				"order by 1 asc " + 
				") t ;";
		
		try {
			con = dataSource.getConnection();
			pStatement = con.prepareStatement(query);
			resultSet = pStatement.executeQuery();
			// log.info("result set:::"+resultSet);
			String assetType = null;;
			Integer value = null;
			while (resultSet.next()) {
				 assetType = (String) resultSet.getString("label");
				 value = (Integer) resultSet.getInt("value");
			/*	assetType = "ATD";
				value = 30;*/
				assetTypeList.add(getDetails(assetType,value));
			}
			pStatement = con.prepareStatement(schDoneQuery);
			resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				 assetType = (String) resultSet.getString("label");
				 value = (Integer) resultSet.getInt("value");
				 schDoneList.add(getDetails(assetType,value));
			}
			//log.info("size is:::::"+assetTypeList.size());
			dashboardResponse.setAssetTypeList(assetTypeList);
			dashboardResponse.setSchDoneList(schDoneList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeJDBCObjects.releaseResouces(con,pStatement, resultSet);
		}
		
		return dashboardResponse;
	}
	
	private DashboardDetailsResponse getDetails(String assetType, Integer totalCount) {
		DashboardDetailsResponse response = new DashboardDetailsResponse();
		response.setAssetType(assetType);
		response.setTotalAssetTypes(totalCount);
		log.info(assetType);
		log.info(totalCount);
		return response;
	}

	public DashboardResponse prepareSubDivisionWiseProductDashboard(String productId) {
		// TODO Auto-generated method stub
		
		Connection con = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		DashboardResponse dashboardResponse = new DashboardResponse();
		List<DashboardDetailsResponse> subdivWiseProductList = new ArrayList<>();
		
		String query =  
				"select div.zone , div, subdiv , product_id as material_desc , " + 
				" case when sum(QOH) is null then 0 else sum(QOH) end QOH ," + 
				"  abbreviation uom  " + 
				"from " + 
				" ( select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
				" left outer join " + 
				"( " + 
				" SELECT " + 
				"        II.PRODUCT_ID AS PRODUCT_ID, II.FACILITY_ID AS FACILITY_ID,  " + 
				"        uom.abbreviation, " + 
				"	ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)),3) as QOH, " + 
				"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div " + 
				"               FROM " + 
				"        INVENTORY_ITEM II , " + 
				"       PRODUCT P, " + 
				"        FACILITY F, uom " + 
				"        WHERE    " + 
				"         P.PRODUCT_ID = II.PRODUCT_ID " + 
				"        AND F.FACILITY_ID = II.FACILITY_ID " + 
				"	and p.quantity_uom_id = uom.uom_id " + 
				"	and p.product_id = '"+productId+"' " + 
				"        GROUP BY II.PRODUCT_ID,II.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division " + 
				") q " + 
				" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID ) "  + 
				"group by div.zone , div, subdiv , product_id ,  abbreviation   " + 
				"        ORDER BY div.zone , div, subdiv , product_id";
		log.info("**** query ***"+query);
		try {
			con = dataSource.getConnection();
			pStatement = con.prepareStatement(query);
			resultSet = pStatement.executeQuery();
			// log.info("result set:::"+resultSet);
			String assetType = null;;
			Integer value = null;
			while (resultSet.next()) {
				 //assetType = (String) resultSet.getString("label");
				 //value = (Integer) resultSet.getInt("value");
			/*	assetType = "ATD";
				value = 30;*/
				 subdivWiseProductList.add(getDetails(resultSet.getString("zone"),resultSet.getString("div"),resultSet.getString("subdiv"),resultSet.getString("material_desc"),resultSet.getInt("qoh"),resultSet.getString("uom")));
			}
			dashboardResponse.setSubdivWiseProductList(subdivWiseProductList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeJDBCObjects.releaseResouces(con,pStatement, resultSet);
		}
		
		return dashboardResponse;
	}

	private DashboardDetailsResponse getDetails(String string, String string2, String string3, String string4, int int1,
			String string5) {
		// TODO Auto-generated method stub
		DashboardDetailsResponse response = new DashboardDetailsResponse();
		response.setZone(string);
		response.setDiv(string2);
		response.setSubdiv(string3);
		response.setMaterialDesc(string4);
		response.setQoh((Integer)int1);
		response.setUom(string5);
		return response;
	}
	
	

}
