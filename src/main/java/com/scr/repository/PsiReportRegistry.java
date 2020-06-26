package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsiReportRegistry extends JpaRepository< com.scr.model.PsiReportRegistry, Long>{

	List<com.scr.model.PsiReportRegistry> findByParentReportId(String string);


}
