/** 
 * 
 */
package com.scr.jobs;

import java.io.ByteArrayOutputStream;
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
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;

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
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (report.getFacility() != null) {
			parameters.put("facilityId", report.getFacility().getFacilityId());
			parameters.put("facilityName", report.getFacility().getFacilityName());
			parameters.put("subDivision", report.getFacility().getSubDivision());
			parameters.put("depot", report.getFacility().getFacilityName());
			parameters.put("depot", report.getFacility().getFacilityId());
			parameters.put("depotId", report.getFacility().getId());
			parameters.put("checkPointsDepot",report.getFacility().getId());
			parameters.put("subStation",report.getFacility().getFacilityName());
			
			log.info("checkPointsDepot==="+parameters.put("checkPointsDepot",report.getFacility().getFacilityId()));
			
		}
		parameters.put("fromDate",report.getFromDate());
		log.info("from date"+parameters.put("fromDate", report.getFromDate()));
		parameters.put("DayReadingsAndConsumptionDate", report.getFromDate());
		parameters.put("toDate", report.getToDate());
		parameters.put("failureFromDate", report.getFailureFromDate());
		parameters.put("failureToDate", report.getFailureToDate());
		parameters.put("date", report.getFromDate());
		parameters.put("Date", report.getDate());
		parameters.put("depot", report.getFeederName());
		parameters.put("gfFromDate", report.getFromDate());
		parameters.put("gfTodate", report.getToDate());
		parameters.put("zone", report.getZone());
		parameters.put("division", report.getDivision());
		parameters.put("subDivision", report.getSubDivision());
		parameters.put("Material_Item", report.getMaterialItem());
		parameters.put("start_date_of_period", report.getFromDate());
		parameters.put("end_date_of_period", report.getToDate());
		parameters.put("tpc_Board", report.getTpcBoard());
		parameters.put("state_Elec_Board", report.getStateEleBoard());
		if (report.getWorkName() != null) {
			parameters.put("workName", report.getWorkName().getWorkName());
		}
        parameters.put("group",report.getGroup());
        parameters.put("section", report.getSection());
        parameters.put("agency",report.getAgency());
        parameters.put("wpaName",report.getWpaName());
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
			log.info("assetType"+parameters.put("assetType", report.getProductId()));
			log.info("assetType1"+ report.getProductId());
		}
		parameters.put("assetId", report.getAssetId());
		if (report.getScheduleCode() != null) {
			parameters.put("scheduleType", report.getScheduleCode().getScheduleCode());
		}
		if (report.getScheduleDate() != null) {
			parameters.put("scheduleDate", report.getScheduleDate());
		}

		parameters.put("ActivityType", report.getActivityType());
		parameters.put("driveName", report.getDriveName());
		parameters.put("checkPointsDepot", report.getCheckPointsDepot());
		parameters.put("depot", report.getCheckPointsDepot());
		parameters.put("equipmentno", report.getEquipmentno());
		parameters.put("location",report.getLocation());
		
		parameters.put("tempDiff", report.getTempDiff());
		

		log.info(" ****** PARAMETERS BODY ***** " + parameters);
		
		Optional<ReportRepository> reportRepryObject = reportRepository.findByReportId(report.getReportId());
		if (reportRepryObject.isPresent()) {
			ReportRepository reportRepry = reportRepryObject.get();
			jrxmlFileName = reportRepry.getJrxmlName();
			if (reportRepry.getSubReportDetails() != null && reportRepry.getSubReportDetails().length() > 0) {
				String[] subReportNameAndJRXMLName = reportRepry.getSubReportDetails().split(";");
				for (String nameAndJRXMLName : subReportNameAndJRXMLName) {
					String[] subReportDetailsArray = nameAndJRXMLName.split(",");
					String subReportName = subReportDetailsArray[0];
					String subReportJRXMLName = subReportDetailsArray[1];
					Resource resource = resourceLoader.getResource("classpath:jrxml/" + subReportJRXMLName);
					String tempFilePath = null;
					try {
						InputStream inputStream = resource.getInputStream();
						File somethingFile = File.createTempFile(resource.getFilename().replace(".jrxml","").trim(), ".jrxml");
						try {
							FileUtils.copyInputStreamToFile(inputStream, somethingFile);
						} finally {
							IOUtils.closeQuietly(inputStream);
						}
						log.info("Sub Report File Path is " + somethingFile.getAbsolutePath());
						tempFilePath = somethingFile.getAbsolutePath();
						JasperReport subReport = JasperCompileManager.compileReport(tempFilePath);
						parameters.put(subReportName, subReport);
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		/*
		 * try { con = dataSource.getConnection(); ps = con.prepareStatement(query);
		 * resultSet = ps.executeQuery(); while (resultSet.next()) { String rname =
		 * resultSet.getString("report_id"); String jnam =
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
		log.info("before try block ***");
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
			byte[] reportResult = null; 
			
			if ("Microsoft Excel".equals(report.getFormatType())) {
				try {
					File reportOutPutFile = File.createTempFile(resource.getFilename().replace(".jrxml","").trim(), ".xlsx");
					JRXlsxExporter jrXlsxExporter = new JRXlsxExporter();
					jrXlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
					jrXlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(reportOutPutFile));
					SimpleXlsxExporterConfiguration simpleXlsxExporterConfiguration = new SimpleXlsxExporterConfiguration();
					jrXlsxExporter.setConfiguration(simpleXlsxExporterConfiguration);
					jrXlsxExporter.exportReport();
					reportResult = FileUtils.readFileToByteArray(reportOutPutFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}else if ("Plain Text".equals(report.getFormatType())) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				JRTextExporter exporter = new JRTextExporter();
		        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		        exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,new Float(6.55));
		        exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,new Float(11.9));
		        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,baos);
		        exporter.exportReport();
			    reportResult = baos.toByteArray(); 
			}else if ("Comma Separated Value Text".equals(report.getFormatType())) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				JRExporter exporter = new JRCsvExporter();
			    exporter.setParameter(JRCsvExporterParameter.JASPER_PRINT, jasperPrint);
			    exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, ",");
			    exporter.setParameter(JRCsvExporterParameter.RECORD_DELIMITER,System.getProperty("line.separator"));
			    exporter.setParameter(JRCsvExporterParameter.OUTPUT_STREAM, baos);
			    exporter.exportReport();
			    reportResult = baos.toByteArray();
			}else {
				reportResult = JasperExportManager.exportReportToPdf(jasperPrint);
			}
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