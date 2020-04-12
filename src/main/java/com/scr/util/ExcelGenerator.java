package com.scr.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.model.DivisionsHistory;
import com.scr.model.JobsHistory;
import com.scr.model.SchedulerOperationTypesTracking;
import com.scr.services.SchedulerTrackingService;

@Component
public class ExcelGenerator {

	@Autowired
	private SchedulerTrackingService schedulerTrackingService;
	
	public ByteArrayInputStream customersToExcel(List<SchedulerOperationTypesTracking> jobInfo)
			throws IOException {		
		try (
				Workbook workbook = new XSSFWorkbook(); 
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				) {			
			createTrackingInfoSheet(workbook, jobInfo);
			createJobsInfoSheet(workbook, jobInfo);
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	@SuppressWarnings("deprecation")
	private void createJobsInfoSheet(Workbook workbook, List<SchedulerOperationTypesTracking> jobInfo) {
		String[] CSV_JOBS_HEADER = { "Job Id", "Operation Name","Processed Date", "Start Time", "End Time", "Total Tables", "Success Tables", "Failed Tables", "Job Status" };
		Sheet sheet = workbook.createSheet("Jobs Info");
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.BLUE.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Row for Header
		Row headerRow = sheet.createRow(0);

		// Header
		for (int col = 0; col < CSV_JOBS_HEADER.length; col++) {
			Cell cell = headerRow.createCell(col);
			cell.setCellValue(CSV_JOBS_HEADER[col]);
			cell.setCellStyle(headerCellStyle);
		}	
		LinkedHashMap<String, List<DivisionsHistory>> divisionsInfo = new LinkedHashMap<>();	
		 int rowIdx = 1;
	      for (SchedulerOperationTypesTracking jobs : jobInfo) {
	    	  List<JobsHistory> jobsHistories = schedulerTrackingService.findByOperationIdOrderByProcessedDateDesc(jobs.getOperationId());
	    	 
					for (JobsHistory jobHis : jobsHistories) {
						Row row = sheet.createRow(rowIdx++);
						row.createCell(0).setCellValue(jobHis.getOperationId());
						row.createCell(1).setCellValue(jobHis.getOperationType());
						row.createCell(2).setCellValue(jobHis.getProcessedDate().toGMTString());
						row.createCell(3).setCellValue(jobHis.getStartTime());
						row.createCell(4).setCellValue(jobHis.getEndTime());
						row.createCell(5).setCellValue(jobHis.getTotalTablesCount());
						row.createCell(6).setCellValue(jobHis.getSuccessTablesCount());
						row.createCell(6).setCellValue(jobHis.getFailedTablesCount());
						row.createCell(6).setCellValue(jobHis.getJobStatus());
						List<DivisionsHistory> divisionInfo = schedulerTrackingService.findDivisionsHistoryByJobsHistoryId(jobHis.getId());					
						divisionsInfo.put(jobs.getJobType()+"-"+jobHis.getOperationType(), divisionInfo);
					}
	      }
	      
	      createDivisionsInfoSheet(workbook, divisionsInfo);
	}

	private void createDivisionsInfoSheet(Workbook workbook, LinkedHashMap<String, List<DivisionsHistory>> divisionsInfo) {
		for (Entry<String, List<DivisionsHistory>> entry : divisionsInfo.entrySet()) {
			createDivisionInfoSheet(workbook, entry.getValue(), entry.getKey());		
		}
	}

	@SuppressWarnings("deprecation")
	private void createDivisionInfoSheet(Workbook workbook, List<DivisionsHistory> divHis, String sheetName) {
		String[] CSV_DIVISIONS_HEADER = { "Job Id", "Operation Name","Processed Date", "Table Name", "Updated Records", "Status" };

		Sheet sheet = workbook.createSheet("Division Info-"+sheetName);
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.BLUE.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Row for Header
		Row headerRow = sheet.createRow(0);

		// Header
		for (int col = 0; col < CSV_DIVISIONS_HEADER.length; col++) {
			Cell cell = headerRow.createCell(col);
			cell.setCellValue(CSV_DIVISIONS_HEADER[col]);
			cell.setCellStyle(headerCellStyle);
		}
		int rowIdx = 1;
		for (DivisionsHistory divHi : divHis) {
			String status = divHi.getStatus();
			Row row = sheet.createRow(rowIdx++);
			row.createCell(0).setCellValue(divHi.getJobsHistoryId());
			row.createCell(1).setCellValue(divHi.getOperationType());
			row.createCell(2).setCellValue(divHi.getUpdatedDate().toGMTString());
			row.createCell(3).setCellValue(divHi.getTableName());
			row.createCell(4).setCellValue(divHi.getUpdatedRecords() != null ? divHi.getUpdatedRecords().toString() : "0");
			row.createCell(5).setCellValue(status);			
		}		
	}

	@SuppressWarnings("deprecation")
	private void createTrackingInfoSheet(Workbook workbook, List<SchedulerOperationTypesTracking> jobInfo) {
		String[] CSV_PID_HEADER = { "PId", "Job Id", "Job Name", "Processed Date", "Start Time", "End Time",
				"Job Status" };
		
		Sheet sheet = workbook.createSheet("Tracking Info");
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.BLUE.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Row for Header
		Row headerRow = sheet.createRow(0);

		// Header
		for (int col = 0; col < CSV_PID_HEADER.length; col++) {
			Cell cell = headerRow.createCell(col);
			cell.setCellValue(CSV_PID_HEADER[col]);
			cell.setCellStyle(headerCellStyle);
		}		

		int rowIdx = 1;
		for (SchedulerOperationTypesTracking jobs : jobInfo) {
			Row row = sheet.createRow(rowIdx++);
			row.createCell(0).setCellValue(jobs.getTrackingId());
			row.createCell(1).setCellValue(jobs.getOperationId());
			row.createCell(2).setCellValue(jobs.getJobType());
			row.createCell(3).setCellValue(jobs.getProcessedDate().toGMTString());
			row.createCell(4).setCellValue(jobs.getStartTime());
			row.createCell(5).setCellValue(jobs.getEndTime());
			row.createCell(6).setCellValue(jobs.getJobStatus());
		}
	}
}