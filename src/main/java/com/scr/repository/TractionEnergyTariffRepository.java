package com.scr.repository;

import java.sql.Timestamp;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.TractionEnergyTariff;

@Repository
public interface TractionEnergyTariffRepository extends JpaRepository<TractionEnergyTariff, Integer>{

	@Query(value = "SELECT case when count(tet)> 0 then true else false  end  FROM TractionEnergyTariff tet WHERE tet.supplier = :supplier and CAST(tet.fromDate AS date ) = :fromDate")
	Boolean existsBySupplierAndFromDate(@Param("supplier") String supplier,@Param("fromDate") Timestamp fromDate);

}
