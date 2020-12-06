package com.scr.mapper;

import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.scr.message.request.AssistanceRequest;
import com.scr.model.Assistance;

@Component
public class AssistanceMapper {
	
static Logger logger = LogManager.getLogger(AssistanceMapper.class);
	
	public Assistance prepareAssistanceModel(@Valid AssistanceRequest request, List<MultipartFile> file, Long commonFileId) {
		Assistance assistance = null;
		if(request != null) {
						
			assistance = new Assistance();
			assistance.setWorkId(request.getWorkId());
			assistance.setWorkGroupId(request.getWorkGroupId());
			assistance.setTypeOfAssistance(request.getTypeOfAssistance());
			assistance.setAssistance(request.getAssistance());
			assistance.setRequestedBy(request.getRequestedBy());
			assistance.setRequestedDate(request.getRequestedDate());
			assistance.setRequestTo(request.getRequestTo());
			assistance.setResponseBy(request.getRequestedBy());
			assistance.setResponseDate(request.getResponseDate());
			assistance.setResponse(request.getResponse());
			assistance.setRemark(request.getRemark());
			assistance.setStatus(request.getStatus());
			assistance.setAttachment(String.valueOf(commonFileId));
		}
		return assistance;
	}
	public Assistance prepareAssistanceUpdataData(Assistance assistance,
			@Valid AssistanceRequest request, List<MultipartFile> file, Long commonFileId) {
		if(request != null) {
			assistance.setWorkId(request.getWorkId());
			assistance.setWorkGroupId(request.getWorkGroupId());
			assistance.setTypeOfAssistance(request.getTypeOfAssistance());
			assistance.setAssistance(request.getAssistance());
			assistance.setRequestedBy(request.getRequestedBy());
			assistance.setRequestedDate(request.getRequestedDate());
			assistance.setRequestTo(request.getRequestTo());
			assistance.setResponseBy(request.getRequestedBy());
			assistance.setResponseDate(request.getResponseDate());
			assistance.setResponse(request.getResponse());
			assistance.setRemark(request.getRemark());
			assistance.setStatus(request.getStatus());
			assistance.setAttachment(String.valueOf(commonFileId));
		}
		return assistance;
	}

}
