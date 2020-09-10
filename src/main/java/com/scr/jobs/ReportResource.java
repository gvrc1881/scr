/** 
 * 
 */ 
package com.scr.jobs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.scr.message.request.ReportRequest;
import com.scr.util.CloseJDBCObjects;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import sun.util.logging.resources.logging;

/**
 * @author winfocus
 *
 */

@Component
public class ReportResource {

	static Logger log = LogManager.getLogger(ReportResource.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private Environment environment;
	
	private CloseJDBCObjects closeJDBCObjects = new CloseJDBCObjects();
	
	@Autowired
	ResourceLoader resourceLoader;
		
	public String getBasePath() {
		
		String os = System.getProperty("os.name").toLowerCase();
		String basePath = null;
		if ("linux".equals(os)) {
			basePath = environment.getProperty("reports.linux.path");
			log.info("resPath:::" + basePath);
		} else {
			basePath = environment.getProperty("reports.windows.path");
		}
		return basePath;
	}

	public String getAbsolutePath(String path, String jrxmlFileName, Map<String, Object> parameters,
			String reportsBasePath) {
		
		String absolutePath = null;
		String urlPath = null;
		File outputFileDir = new File(reportsBasePath + "/output");

		if (!outputFileDir.exists()) {
			boolean status = outputFileDir.mkdirs();

		}
		try {
			urlPath = URLDecoder.decode(path, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		if (urlPath.contains("/WEB-INF/classes/")) {
			// absolutePath = urlPath + "jrxml/" + jrxmlFileName;
			String pathArr[] = urlPath.split("/WEB-INF/classes/");
			// absolutePath = pathArr[0] + "/jrxml/" + jrxmlFileName;
		} else {
			String pathArr[] = urlPath.split("/target/classes/");
			absolutePath = pathArr[0] + "/src/main/java/com/scr/jrxml/" + jrxmlFileName;
		}
		return absolutePath;
	}

	


	

	public ReportRequest generateReport(ReportRequest report) {
		log.info("in report resource:6::" + report.getReportId());
		String jrxmlFileName = null;
		Connection con = null;
		//Connection con1 = null;
		ResultSet resultSet = null;
		// ResultSet resultSet1 = null;
		PreparedStatement ps = null;
		String query = ("select * from report_repository");
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				String rname = resultSet.getString("report_id");
				String jname = resultSet.getString("jrxml_name");
				if (report.getReportId().equalsIgnoreCase(rname)) {
					jrxmlFileName = jname;

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeJDBCObjects.releaseResouces(con,ps, resultSet);
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		if (report.getFacility() != null) {
			parameters.put("facilityId", report.getFacility().getFacilityId());
			parameters.put("facilityName", report.getFacility().getFacilityName());
			parameters.put("subDivision", report.getFacility().getSubDivision());
			parameters.put("depot", report.getFacility().getFacilityName());
		}
		parameters.put("fromDate", report.getFromDate());
		parameters.put("DayReadingsAndConsumptionDate", report.getFromDate());
		parameters.put("toDate", report.getToDate());
		parameters.put("failureFromDate", report.getFailureFromDate());
		parameters.put("failureToDate", report.getFailureToDate());
		parameters.put("date", report.getFromDate());
		parameters.put("Date", report.getDate());
		parameters.put("gfFromDate", report.getFromDate());
		parameters.put("gfTodate", report.getToDate());
		parameters.put("zone", report.getZone().getCode());
		parameters.put("division", report.getDivision().getCode());
		parameters.put("subDivision", report.getSubDivision());
		parameters.put("Material_Item", report.getMaterialItem());
		parameters.put("start_date_of_period", report.getFromDate());
		parameters.put("end_date_of_period", report.getToDate());
		
		if(report.getProductId()!=null) {
		parameters.put("productId", report.getProductId());
		}
		if (report.getDepartment() != null) {
			parameters.put("department", report.getDepartment().getDepartment());
		}
		if (report.getObservationCategory() != null) {
			parameters.put("observationCategory", report.getObservationCategory().getObservationCategory());
		}
		parameters.put("year", report.getYear());
		parameters.put("SwitchType", report.getPbSwitchType());

		if (report.getPbExtentCode() != null) {
			parameters.put("elementarySection", report.getPbExtentCode().getPbExtentCode());
			log.info("getPbExtentCode"+report.getPbExtentCode().getPbExtentCode());
		}
		if (report.getElementarySectionCode() != null) {
			parameters.put("elementarySection", report.getElementarySectionCode().getElementarySectionCode());
		}
		parameters.put("fromKm", report.getFromkm());
		parameters.put("toKm", report.getTokm());
		if(report.getProductId()!=null) {
			parameters.put("assetType", report.getProductId());
		}
		parameters.put("assetId", report.getAssetId());
		if (report.getScheduleCode() != null) {
			parameters.put("scheduleType", report.getScheduleCode().getScheduleCode());
		}
		if (report.getScheduleDate() != null) {
			parameters.put("scheduleDate", report.getScheduleDate());
		}
		
		parameters.put("ActivityType", report.getActivityType());
		
		log.info(" ****** PARAMETERS BODY ***** " + parameters);
		
		UUID uniqueId = null;
		try {
			con = dataSource.getConnection();
		//	String path = this.getClass().getClassLoader().getResource("").getPath();
			String reportsBasePath = getBasePath();
		//	String absolutePath = getAbsolutePath(path, jrxmlFileName, parameters, reportsBasePath);
			 Resource resource = resourceLoader.getResource("classpath:jrxml/"+jrxmlFileName);
			 File file = null;
			try {
				file = resource.getFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			 log.info("resource File Path = "+file.getAbsolutePath());
		//	log.info("String absolutePath" + absolutePath);
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			log.info("jasperReport6-1-2020" + jasperReport);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
			byte[] reportResult = JasperExportManager.exportReportToPdf(jasperPrint);
			log.info("*** report result length ***"+reportResult.length);
			report.setOutputData(Base64.getEncoder().encodeToString(reportResult));
			
			
			/*JRPdfExporter jrPdfExporter = new JRPdfExporter();
			uniqueId = UUID.randomUUID();
			log.info("uniqyeId28-12-19" + uniqueId);
			log.info("from generate report to pdfFile :::::" + reportsBasePath);
			jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			jrPdfExporter.setExporterOutput(
					new SimpleOutputStreamExporterOutput(reportsBasePath + "/output/" + "report_" + uniqueId + ".pdf"));
			SimplePdfExporterConfiguration simplePdfExporterConfiguration = new SimplePdfExporterConfiguration();
			jrPdfExporter.setConfiguration(simplePdfExporterConfiguration);*/
			/*try {
				jrPdfExporter.exportReport();
				File f = new File(reportsBasePath + "/output/" + "report_" + uniqueId + ".pdf");
				log.info("reportBasePath" + f);
				InputStream is = new FileInputStream(f);
				log.info("is length:::" + is.available());
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				int nRead;
				byte[] data = new byte[is.available()];
				while ((nRead = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				String outPutString = Base64.getEncoder().encodeToString(buffer.toByteArray());
				report.setOutputData(outPutString);
				is.close();
			} catch (JRException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {				
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}*/
		} catch (SQLException e) {			
			e.printStackTrace();
		} catch (JRException e) {
			
			e.printStackTrace();
		}finally {
			closeJDBCObjects.closeConnection(con);
		}
		return report;

	}

}