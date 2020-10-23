package com.scr.mapper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.scr.message.response.EnergyConsumptionResponse;
import com.scr.model.EnergyConsumption;
import com.scr.util.Helper;

@Component
public class EnergyConsumptionMapper {

	public EnergyConsumption prepareEnergyConsumptionModel(EnergyConsumptionResponse request,
			EnergyConsumption energyConsumption) {

		if (request != null) {
			energyConsumption.setKvah(request.getCurKvah() != null ? String.valueOf(request.getCurKvah()) : "0");
			energyConsumption.setKwh(request.getCurKwh() != null ? String.valueOf(request.getCurKwh()) : "0");
			energyConsumption.setRkvahLag(request.getCurRkvahLag() != null ? String.valueOf(request.getCurRkvahLag()) : "0");
			energyConsumption.setRkvahLead(request.getCurRkvahLead() != null ? String.valueOf(request.getCurRkvahLead()) : "0");

			energyConsumption.setRmd(request.getRmd() != null ? String.valueOf(request.getRmd()) : "0");
			energyConsumption.setVolMin(request.getCurVolMin() != null ? String.valueOf(request.getCurVolMin()) : "0");
			energyConsumption.setVolMax(request.getCurVolMax() != null ? String.valueOf(request.getCurVolMax()) : "0");
			energyConsumption.setMaxLoad(request.getCurMaxLoad() != null ? String.valueOf(request.getCurMaxLoad()) : "0");
			energyConsumption.setMaxLoadTime(Helper.convertStringToTimestampec(request.getMaxLoadTimeHhmm()));
			energyConsumption.setRemarks(request.getRemarks());
			energyConsumption.setJointMeter(request.getJointMeter());
			energyConsumption.setPf(String.valueOf(request.getPf()));
			energyConsumption.setCpf(String.valueOf(request.getCpf()));
			
			// energyConsumption.set
			energyConsumption.setLastUpdatedStamp(new Timestamp(Calendar.getInstance().getTime().getTime()));
			energyConsumption.setLastUpdatedTxStamp(new Timestamp(Calendar.getInstance().getTime().getTime()));
			// energyConsumption.setCmd(String.valueOf(request.getCur_cmd()));
			// energyConsumption.setCpf(String.valueOf(request.getCPF()));

			// energyConsumption.setDataDiv(request.getdata);
			// energyConsumption.setEnergyReadingDate(request.getRequested_reading_date());
			// energyConsumption.setEnergyReadingDate(request.getRequested_reading_date());
			// energyConsumption.setKvah(request.get);
		}
		return energyConsumption;
	}

	public EnergyConsumption prepareEnergyConsumptionModelForSave(EnergyConsumptionResponse request, EnergyConsumption list) {
		EnergyConsumption energyConsumption = null;
		try {
		if(request != null) {
			energyConsumption = new EnergyConsumption();
			System.out.println("id = "+list.getId());
			energyConsumption.setId(list.getId()+1);
			
			energyConsumption.setKvah(request.getCurKvah() != null ? String.valueOf(request.getCurKvah()) : "0");
			energyConsumption.setKwh(request.getCurKwh() != null ? String.valueOf(request.getCurKwh()) : "0");
			energyConsumption.setRkvahLag(request.getCurRkvahLag() != null ? String.valueOf(request.getCurRkvahLag()) : "0");
			energyConsumption.setRkvahLead(request.getCurRkvahLead() != null ? String.valueOf(request.getCurRkvahLead()) : "0");
			energyConsumption.setLocation(request.getFeederName());
			energyConsumption.setRmd(request.getRmd() != null ? String.valueOf(request.getRmd()) : "0");
			energyConsumption.setVolMin(request.getCurVolMin() != null ? String.valueOf(request.getCurVolMin()) : "0");
			energyConsumption.setVolMax(request.getCurVolMax() != null ? String.valueOf(request.getCurVolMax()) : "0");
			energyConsumption.setMaxLoad(request.getCurMaxLoad() != null ? String.valueOf(request.getCurMaxLoad()) : "0");
			energyConsumption.setMaxLoadTime(Helper.convertStringToTimestampec(request.getMaxLoadTimeHhmm()));
			energyConsumption.setRemarks(request.getRemarks());
			energyConsumption.setDataDiv(request.getDataDiv().toLowerCase());
			energyConsumption.setJointMeter(request.getJointMeter());
			energyConsumption.setPf(String.valueOf(request.getPf()));
			energyConsumption.setCpf(String.valueOf(request.getCpf()));
				/*
				 * SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy"); Date date =
				 * formatter.parse(request.getRequested_reading_date());
				 * 
				 * SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd"); String
				 * dateString = formatter1.format(date);
				 * 
				 * System.out.println("dateString = "+dateString);
				 */
			System.out.println("energy reading date = "+request.getEnergyReadingDate());
			 SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
			energyConsumption.setEnergyReadingDate(formatter1.parse(request.getEnergyReadingDate()));
			
			energyConsumption.setCreatedBy(request.getUpdatedBy());
			energyConsumption.setCreatedTxStamp(new Timestamp(Calendar.getInstance().getTime().getTime()));
			energyConsumption.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(energyConsumption);
		return energyConsumption;
	}

}
