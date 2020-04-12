/**
 * 
 */
package com.scr.mapper;

import org.springframework.stereotype.Component;

import com.scr.model.SchedulerJob;

/**
 * @author vt1056
 *
 */
@Component
public class JobsMapper {

	public SchedulerJob prepareSchedulerUpdate(SchedulerJob job) {
		SchedulerJob schedulerJobToUpdate = new SchedulerJob();
		
		return schedulerJobToUpdate;
	}

}
