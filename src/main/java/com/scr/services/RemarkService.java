/**
 * 
 */
package com.scr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.mapper.RemarkMapper;
import com.scr.message.request.RemarkRequest;
import com.scr.model.Remark;
import com.scr.repository.RemarkRepository;

/**
 * @author vt1056
 *
 */
@Service
public class RemarkService {

	@Autowired
	private RemarkMapper mapper;
	
	@Autowired
	private RemarkRepository remarkRepository;
	
	public void saveRemark(RemarkRequest remarkRequest) {		
		Remark remark = mapper.prepareAddDetails(remarkRequest);
		remarkRepository.save(remark);
	}

}
