
package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.scr.model.ReportParameter;

@Repository
public interface ReportParametersRepository extends JpaRepository<ReportParameter, Long> {
	
	List<ReportParameter> findAll();
	

}
