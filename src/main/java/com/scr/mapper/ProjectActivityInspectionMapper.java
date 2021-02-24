package com.scr.mapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.scr.message.request.ProjectActivityInspectionRequest;
import com.scr.model.ProjectActivityInspection;
@Component
public class ProjectActivityInspectionMapper {

	static Logger logger = LogManager.getLogger(ProjectActivityInspectionMapper.class);

	public ProjectActivityInspection preparePAIEntryModel(
			@Valid ProjectActivityInspectionRequest projectActivityInspectionRequest) throws Exception {
		ProjectActivityInspection prjActIns = new ProjectActivityInspection();
		try {
			Field[] fields = ProjectActivityInspection.class.getDeclaredFields();
			HashMap<String, String> measuresMap = projectActivityInspectionRequest.getMeasureMap();
			prjActIns.setDoneBy(projectActivityInspectionRequest.getDoneBy());
			prjActIns.setRemark(projectActivityInspectionRequest.getRemark());
			prjActIns.setStatus(projectActivityInspectionRequest.getStatus());
			prjActIns.setDate(projectActivityInspectionRequest.getDate());
			prjActIns.setActivityId(projectActivityInspectionRequest.getActivityId());
			for (Map.Entry<String, String> entry : measuresMap.entrySet()) {
				for (Field field : fields) {
					if (entry.getKey().equals(field.getName())) {
						field.setAccessible(true);
						field.set(prjActIns, entry.getValue());
					}
				}
				
			}
			HashMap<String, String> activityMap=projectActivityInspectionRequest.getActivityMap();
			for (Map.Entry<String, String> entry : activityMap.entrySet()) {

				for (Field field : fields) {
					
					if (entry.getKey().equals(field.getName())) {
						field.setAccessible(true);
						field.set(prjActIns, entry.getValue());
					}
				}
				
			}
			HashMap<String, String> multiMeasureMap=projectActivityInspectionRequest.getMultiMeasureMap();
			for (Map.Entry<String, String> entry : multiMeasureMap.entrySet()) {
				for (Field field : fields) {
					if (entry.getKey().equals(field.getName())) {
						field.setAccessible(true);
						field.set(prjActIns, entry.getValue());
					}
				}
				
			}
			HashMap<String, String> multiMeasureActivityMap=projectActivityInspectionRequest.getMultiMeasureActivityMap();
			for (Map.Entry<String, String> entry : multiMeasureActivityMap.entrySet()) {
				for (Field field : fields) {
					if (entry.getKey().equals(field.getName())) {
						field.setAccessible(true);
						field.set(prjActIns, entry.getValue());
					}
				}
				
			}
			logger.info("Prepared PAI model object = "+prjActIns.toString());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		
		return prjActIns;
	}
}
