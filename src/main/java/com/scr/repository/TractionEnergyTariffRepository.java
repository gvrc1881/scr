package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.TractionEnergyTariff;

@Repository
public interface TractionEnergyTariffRepository extends JpaRepository<TractionEnergyTariff, Integer>{

}
