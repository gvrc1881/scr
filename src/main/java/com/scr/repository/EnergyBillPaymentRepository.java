package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.EnergyBillPayment;


@Repository
public interface EnergyBillPaymentRepository extends JpaRepository<EnergyBillPayment, Integer>{

	List<EnergyBillPayment> findAll();

}
