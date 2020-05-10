package com.scr.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.util.Constants;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class FileDownloadController {
	static Logger logger = LogManager.getLogger(FileDownloadController.class);
	
	@Value("${stipulation.path}")
	private String stipulationPath;

	@Value("${inspection.path}")
	private String inspectionPath;

	@RequestMapping(value="/driveFiles/{type}/{fileName:.+}", method = RequestMethod.GET)
	public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("type") String type, @PathVariable("fileName") String fileName) throws IOException {
		File file = null;
		try {
			logger.info("Type = "+type+" FileName = "+fileName);
			if(type.equalsIgnoreCase(Constants.INSPECTION))
				file = new File(inspectionPath + fileName);
			else if(type.equalsIgnoreCase(Constants.STIPULATION))
				file = new File(stipulationPath + fileName);
		//	file = new File("D://SCR//inspection//drive inspe fai_analy etc screen details_2020-05-07_00-08-50.xlsx");	
			if (file.exists()) {

				// get the mimetype
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}

				response.setContentType(mimeType);
				response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
				response.setContentLength((int) file.length());

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				FileCopyUtils.copy(inputStream, response.getOutputStream());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
