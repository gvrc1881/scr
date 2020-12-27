package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.scr.mapper.AssistanceMapper;
import com.scr.mapper.CommonMapper;
import com.scr.message.request.AssistanceRequest;
import com.scr.model.Assistance;
import com.scr.model.ContentManagement;
import com.scr.repository.AssistanceRepository;
import com.scr.repository.ContentManagementRepository;
import com.scr.util.Constants;

@Service
public class AssistanceService {
	
	static Logger log = LogManager.getLogger(AssistanceService.class);
	
	@Value("${assistance.path}")
	private String assistancePath;
	
	@Autowired
	private AssistanceRepository assistanceRepository;
	@Autowired
	private CommonMapper commonMapper;
	@Autowired
	private ContentManagementRepository contentManagementRepository;
	@Autowired
	private AssistanceMapper assistanceMapper;
	
	public List<Assistance> findAll() {
		// TODO Auto-generated method stub
		return assistanceRepository.findAll();
	}
	
	public void save(Assistance assistance) {
		assistanceRepository.save(assistance);
	}
	
	
	public @Valid boolean saveAssistance(@Valid AssistanceRequest assistanceRequest, List<MultipartFile> file) {
		List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
		
		liContentManagements = commonMapper.prepareContentManagementList(file, assistancePath, Constants.ASSISTANCE,
				"","","","Assistance","","","","","", Integer.parseInt(assistanceRequest.getCreatedBy()));
		
		if(liContentManagements != null && !liContentManagements.isEmpty()) {
			contentManagementRepository.saveAll(liContentManagements);
			log.info("Files Details saved in to Database Successfully.");
			Assistance assistance = assistanceMapper.prepareAssistanceModel(assistanceRequest, file, liContentManagements.get(0).getCommonFileId());
			assistance = assistanceRepository.save(assistance);
		}else {
			Assistance assistance = assistanceMapper.prepareAssistanceModel(assistanceRequest, file, new Long(0));
			assistance = assistanceRepository.save(assistance);
		}
		
		return true;
	}
	
	public String updateAssistanceData(@Valid AssistanceRequest assistanceRequest, List<MultipartFile> file) {
		List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();
		liContentManagements = commonMapper.prepareForUpdateContentManagementList(file, assistancePath, Constants.ASSISTANCE,
				"","","","Assistance","","","","","", Integer.parseInt(assistanceRequest.getUpdatedBy()), assistanceRequest.getAttachment());
		
		Long commonFileId = (long) 0.0;
		if(liContentManagements!=null && !liContentManagements.isEmpty()) {
			contentManagementRepository.saveAll(liContentManagements);
			log.info("Files Details saved in to Database Successfully.");
			commonFileId = liContentManagements.get(0).getCommonFileId();
		}
		else {
			commonFileId = assistanceRequest.getAttachment() != null ? Long.parseLong(assistanceRequest.getAttachment()) : (long) 0.0;
			log.info("find file = "+commonFileId);
		}
		
		log.info("find the existing observation by id : "+commonFileId);
		Optional<Assistance> assistance = assistanceRepository.findById(assistanceRequest.getId());
		if(assistance.isPresent()) {
			Assistance assistanceUpdate = assistanceMapper.prepareAssistanceUpdataData(assistance.get(), assistanceRequest, file, commonFileId);
			assistanceUpdate = assistanceRepository.save(assistanceUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Observations Id";
		}
	}
	
	
	
	public Optional<Assistance> findAssistanceById(Long id) {
		// TODO Auto-generated method stub
		return assistanceRepository.findById(id);

	}

	public void deleteAssistanceById(Long id) {
		// TODO Auto-generated method stub
		assistanceRepository.deleteById(id);
	}
	
}
