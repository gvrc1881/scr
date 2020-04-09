package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.EnergyBillPayment;
import com.scr.repository.EnergyBillPaymentRepository;

@Service
public class EnergyBillPaymentServices {
	
	
	@Autowired
	private EnergyBillPaymentRepository energyBillPaymentRepository;

	public List<EnergyBillPayment> findAll() {
		// TODO Auto-generated method stub
		return energyBillPaymentRepository.findAll();
	}

	public EnergyBillPayment saveEneBillPayment(EnergyBillPayment energyBillPayment) {
		// TODO Auto-generated method stub
		return energyBillPaymentRepository.save(energyBillPayment);
	}

	public Optional<EnergyBillPayment> findById(Integer id) {
		// TODO Auto-generated method stub
		return energyBillPaymentRepository.findById(id);
	}

	public void deleteEneBillPaymentById(Integer id) {
		// TODO Auto-generated method stub
		energyBillPaymentRepository.deleteById(id);
	}
	
	

}
