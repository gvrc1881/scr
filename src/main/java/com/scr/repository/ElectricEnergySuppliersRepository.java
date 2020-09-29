package com.scr.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.scr.model.ElectricEnergySuppliers;

@Repository
public interface ElectricEnergySuppliersRepository extends JpaRepository<ElectricEnergySuppliers, Integer> {
	
	@Query("FROM ElectricEnergySuppliers ORDER BY code ASC")
	List<ElectricEnergySuppliers> findAll();
	List<ElectricEnergySuppliers> findByStateId(String stateId);

	
}
