/** 
 * 
 */
package com.scr.jobs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.scr.message.request.ReportRequest;
import com.scr.model.ReportRepository;
import com.scr.repository.ReportRepositoryRepository;
import com.scr.util.CloseJDBCObjects;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

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
	private CloseJDBCObjects closeJDBCObjects ;

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	private ReportRepositoryRepository reportRepository;

	public ReportRequest generateReport(ReportRequest report) {
		log.info("in report resource:6::" + report.getReportId());
		String jrxmlFileName = null;
		Connection con = null;
	//	String query = ("select * from report_repository");
		Optional<ReportRepository> reportRepryObject = reportRepository.findByReportId(report.getReportId());
		if (reportRepryObject.isPresent()) {
			ReportRepository reportRepry = reportRepryObject.get();
			jrxmlFileName = reportRepry.getJrxmlName();
		}
		/*
		 * try { con = dataSource.getConnection(); ps = con.prepareStatement(query);
		 * resultSet = ps.executeQuery(); while (resultSet.next()) { String rname =
		 * resultSet.getString("report_id"); String jname =
		 * resultSet.getString("jrxml_name"); if
		 * (report.getReportId().equalsIgnoreCase(rname)) { jrxmlFileName = jname;
		 * 
		 * }
		 * 
		 * }
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } finally {
		 * closeJDBCObjects.releaseResouces(con, ps, resultSet); }
		 */

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
		parameters.put("zone", report.getZone());
		parameters.put("division", report.getDivision());
		parameters.put("subDivision", report.getSubDivision());
		parameters.put("Material_Item", report.getMaterialItem());
		parameters.put("start_date_of_period", report.getFromDate());
		parameters.put("end_date_of_period", report.getToDate());

		if (report.getProductId() != null) {
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
			log.info("getPbExtentCode" + report.getPbExtentCode().getPbExtentCode());
		}
		if (report.getElementarySectionCode() != null) {
			parameters.put("elementarySection", report.getElementarySectionCode().getElementarySectionCode());
		}
		parameters.put("fromKm", report.getFromkm());
		parameters.put("toKm", report.getTokm());
		if (report.getProductId() != null) {
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

		try {
			con = dataSource.getConnection();
			Resource resource = resourceLoader.getResource("classpath:jrxml/" + jrxmlFileName);
			String tempFilePath = null;
			try {
				InputStream inputStream = resource.getInputStream();
				File somethingFile = File.createTempFile(resource.getFilename().replace(".jrxml","").trim(), ".jrxml");
				try {
					FileUtils.copyInputStreamToFile(inputStream, somethingFile);
				} finally {
					IOUtils.closeQuietly(inputStream);
				}
				log.info("File Path is " + somethingFile.getAbsolutePath());
				tempFilePath = somethingFile.getAbsolutePath();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JasperReport jasperReport = JasperCompileManager.compileReport(tempFilePath);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
			byte[] reportResult = JasperExportManager.exportReportToPdf(jasperPrint);
			report.setOutputData(Base64.getEncoder().encodeToString(reportResult));
			log.info("Done!...");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			closeJDBCObjects.closeConnection(con);
		}
		return report;

	}

}