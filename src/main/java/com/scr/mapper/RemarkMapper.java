/**
 * 
 */
package com.scr.mapper;

import org.springframework.stereotype.Component;

import com.scr.message.request.RemarkRequest;
import com.scr.model.Remark;

/**
 * @author vt1056
 *
 */
@Component
public class RemarkMapper {

	public Remark prepareAddDetails(RemarkRequest remarkRequest) {
		Remark remark = new Remark();
		remark.setJobId(remarkRequest.getJobId());
		remark.setRemark(remarkRequest.getRemark());
		remark.setRunTypeId(remarkRequest.getRunTypeId());
		remark.setRunBy(remarkRequest.getRunBy());
		remark.setRunDate(remarkRequest.getRunDate());
		return remark;
	}

}
