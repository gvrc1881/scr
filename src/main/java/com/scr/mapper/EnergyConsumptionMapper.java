package com.scr.mapper;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.scr.message.response.EnergyConsumptionResponse;
import com.scr.model.EnergyConsumption;
import com.scr.util.Helper;

import sun.util.logging.resources.logging;

@Component
public class EnergyConsumptionMapper {

	public EnergyConsumption prepareEnergyConsumptionModel(EnergyConsumptionResponse request,
			EnergyConsumption energyConsumption) {

		if (request != null) {
			energyConsumption.setKvah(request.getCur_kvah() != null ? String.valueOf(request.getCur_kvah()) : "0");
			energyConsumption.setKwh(request.getCur_kwh() != null ? String.valueOf(request.getCur_kwh()) : "0");
			energyConsumption.setRkvahLag(request.getCur_rkvah_lag() != null ? String.valueOf(request.getCur_rkvah_lag()) : "0");
			energyConsumption.setRkvahLead(request.getCur_rkvah_lead() != null ? String.valueOf(request.getCur_rkvah_lead()) : "0");

			energyConsumption.setRmd(request.getCur_rmd() != null ? String.valueOf(request.getRMD()) : "0");
			energyConsumption.setVolMin(request.getCur_vol_min() != null ? String.valueOf(request.getCur_vol_min()) : "0");
			energyConsumption.setVolMax(request.getCur_vol_max() != null ? String.valueOf(request.getCur_vol_max()) : "0");
			energyConsumption.setMaxLoad(request.getCur_max_load() != null ? String.valueOf(request.getCur_max_load()) : "0");
			energyConsumption.setMaxLoadTime(Helper.convertStringToTimestampec(request.getMax_load_time_hhmm()));
			energyConsumption.setRemarks(request.getRemarks());

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
			
			energyConsumption.setKvah(request.getCur_kvah() != null ? String.valueOf(request.getCur_kvah()) : "0");
			energyConsumption.setKwh(request.getCur_kwh() != null ? String.valueOf(request.getCur_kwh()) : "0");
			energyConsumption.setRkvahLag(request.getCur_rkvah_lag() != null ? String.valueOf(request.getCur_rkvah_lag()) : "0");
			energyConsumption.setRkvahLead(request.getCur_rkvah_lead() != null ? String.valueOf(request.getCur_rkvah_lead()) : "0");

			energyConsumption.setRmd(request.getCur_rmd() != null ? String.valueOf(request.getRMD()) : "0");
			energyConsumption.setVolMin(request.getCur_vol_min() != null ? String.valueOf(request.getCur_vol_min()) : "0");
			energyConsumption.setVolMax(request.getCur_vol_max() != null ? String.valueOf(request.getCur_vol_max()) : "0");
			energyConsumption.setMaxLoad(request.getCur_max_load() != null ? String.valueOf(request.getCur_max_load()) : "0");
			energyConsumption.setMaxLoadTime(Helper.convertStringToTimestampec(request.getMax_load_time_hhmm()));
			energyConsumption.setRemarks(request.getRemarks());
			
			energyConsumption.setCreatedBy(request.getUpdatedBy());
			energyConsumption.setCreatedTxStamp(new Timestamp(Calendar.getInstance().getTime().getTime()));
			energyConsumption.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return energyConsumption;
	}

}
